package Pension.common.sys.userlog;

import Pension.common.AppException;
import Pension.common.CommQuery;
import Pension.common.CommonDbUtil;
import Pension.common.IParam;
import Pension.common.db.DbUtil;
import Pension.common.sys.LogBean;
import Pension.common.sys.ReqBean;
import Pension.common.sys.util.CurrentUser;
import Pension.common.sys.util.SysUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.oracore.OracleType;
import oracle.sql.CLOB;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-17
 * Time: 下午1:05
 */
public class UserLog {

    public static void AddLog() throws SQLException {
        LogBean logBean =new LogBean();
        Map localmap=new HashMap();

        Long opseno=getOpseno();
        localmap.put("opseno",opseno);
        logBean.setLocalLog(localmap);
        System.out.println("操作日志流水号:" + logBean.getLocalLog().get("opseno"));
        ReqBean reqBean=new ReqBean();
        HttpServletRequest req=reqBean.getLocalReq();
        CurrentUser user= SysUtil.getCacheCurrentUser();
        String functionid=(String)req.getParameter("functionid");
        String digest=makeDigest(getFunctionBSDigest(functionid),req);

        PreparedStatement stmt = null;// 加载SQL语句
        try {
            stmt = DbUtil.get().prepareStatement(
                    "insert into xt_userlog(opseno,digest,functionid,dvcode,loginname,username,originalpage) values (?,?,?,?,?,?,?)");
            stmt.setLong(1,opseno);
            stmt.setString(2,digest);
            stmt.setString(3,functionid);
            stmt.setString(4,user.getRegionid());
            stmt.setString(5,user.getLoginName());
            stmt.setString(6,user.getUserName());
            String ol= req.getParameter("originalpage");
            String originalpage="";
            if(null!=ol){
                originalpage=ol;    //保存原始界面
            }

            Reader clobReader = new StringReader(originalpage);
            stmt.setCharacterStream(7, clobReader, originalpage.length());
            stmt.executeUpdate();
        } catch (SQLException e) {
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
    }

    private static JSONArray getFunctionBSDigest(String functionid) {
        Map map= null;
        try {
            map = CommQuery.query("select bsdigest from xt_function where functionid='" + functionid + "'");
        } catch (AppException e) {
            e.printStackTrace();
        }
        List list=(List)map.get(IParam.ROWS);
        if(list.size()>0){
            return JSONArray.fromObject(((Map)list.get(0)).get("bsdigest"));
        }else{
            System.out.println("没有functionid或者没有业务摘要的相关配置");
            return null;
        }
    }

    private static String makeDigest(JSONArray array,HttpServletRequest request){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<array.size();i++){
            JSONObject obj=array.getJSONObject(i);
            sb.append(obj.getString("label")+request.getParameter(obj.getString("property"))+" ");
        }
        return sb.toString();
    }


    public Map query(String functionid,int page,int rows) throws AppException {
         return CommQuery.query("select opseno,functionid,digest,bsnyue,bstime,username,loginname from xt_userlog where functionid='"
                 +functionid+"' order by opseno desc",page,rows);
    }


    public String clobExport(Long opseno) {
        CLOB clob = null;

        String sql = "select originalpage from xt_userlog where opseno=?";
        PreparedStatement pstmt = null;
        String content = "";
        try {
            pstmt = DbUtil.get().prepareStatement(sql);
            pstmt.setLong(1,opseno);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                clob = (oracle.sql.CLOB) rs.getClob("originalpage"); // 获得CLOB字段str
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
        return sb.toString();
    }

    private static Long getOpseno() {
        CallableStatement cst =null;
        Long opseno=0l;
        try{
            String sql = "{?= call glog.getopseno()}";
            cst = DbUtil.get().prepareCall(sql);
            cst.registerOutParameter(1, Types.INTEGER);
            cst.execute();
            opseno=cst.getLong(1);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
             try {
                 if(cst!=null) cst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return opseno;
    }
}
