package Pension.model.notepad;

import Pension.common.*;
import Pension.common.db.DbUtil;
import Pension.common.sys.audit.AuditManager;
import Pension.model.Model;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteEnter extends Model {

    public String save()  {//先保存clob再问题信息
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Map map= ParameterUtil.toMap(this.getRequest());
        Long cid=commonDbUtil.getSequence("SEQ_T_NOTEPADC");
        Long aid=commonDbUtil.getSequence("SEQ_T_NOTEPADA");
        int result=0;

        PreparedStatement stmt = null;// 加载SQL语句
        try {
            stmt = DbUtil.get().prepareStatement("insert into t_notepadc(notecid,noteclob) values (?,?)");
            String noteclob=map.get("noteclob").toString();
            Reader clobReader = new StringReader(noteclob);
            stmt.setLong(1,cid);
            stmt.setCharacterStream(2, clobReader, noteclob.length());
            int num = stmt.executeUpdate();    //保存到 t_notepadc
            if (num > 0) {
                map.put("noteid",aid);
                map.put("notecid",cid);
                commonDbUtil.insertTableVales(map,"t_notepada");  //保存到 t_notepada
            }else{
                result=-1;
            }
        } catch (SQLException e) {
            result=-1;
            e.printStackTrace();
        } finally {
            if(null!=stmt){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return result>-1? RtnType.SUCCESS:RtnType.FAILURE;
    }

    public String query() throws AppException {
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Integer page=Integer.parseInt(this.getRequest().getParameter("page"));
        Integer rows=Integer.parseInt(this.getRequest().getParameter("rows"));

        Map map= new HashMap();
        String sql="select noteid,text,xiangmulb,wengtilb,sender,sendee,digest,createdate,begindate,enddate,settlement,notecid from t_notepada";

        return JSONObject.fromObject(CommQuery.query(sql,page,rows)).toString();
    }

    public String clobExport() {
        CLOB clob = null;
        Long notecid=Long.parseLong(this.getRequest().getParameter("notecid"));

        String sql = "select * from t_notepadc where notecid=?";
        PreparedStatement pstmt = null;
        String content = "";
        try {
            pstmt = DbUtil.get().prepareStatement(sql);
            pstmt.setLong(1,notecid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                clob = (oracle.sql.CLOB) rs.getClob("noteclob"); // 获得CLOB字段str
                // 注释： 用 rs.getString("str")无法得到 数据 ，返回的 是 NULL;
                content = ClobToString(clob);
            }else{
                content= "no content";
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null!=pstmt){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    // 将字CLOB转成STRING类型
    private String ClobToString(CLOB clob) throws SQLException, IOException {

        String reString = "";
        Reader is = clob.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s+"\n");
            s = br.readLine();
        }
        sb.append("</body> </html >");
        reString = "<!DOCTYPE html><html> <head>" +
                "<script src=\"js/extLocation.js\"></script>" +
                "<script> document.write('<link rel=\"stylesheet\" type=\"text/css\" id=\"swicth-style\" href=\"'+extKindeditor+ 'themes/default/default.css\"><\\/>'); </script> </head> <body>"+
                sb.toString();
        return reString;
    }
}
