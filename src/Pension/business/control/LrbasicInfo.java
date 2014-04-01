package Pension.business.control;

import Pension.common.CommonDbUtil;
import Pension.jdbc.JdbcFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 上午11:00
 * To change this template use File | Settings | File Templates.
 */
public class LrbasicInfo {
    private Connection conn ;
    private HttpServletRequest request;
    //private PreparedStatement pstmt = null;
    public String findLrbasicInfo(String pages,String rows) {       //列表
        int total = 0;
        int pagenum = Integer.parseInt(pages);
        int rowsnum = Integer.parseInt(rows);
        Map<String,Object> mapruturn = new HashMap<String, Object>();
        Connection conn = JdbcFactory.getConn("oracle");
        PreparedStatement pstmt = null;

        //查询总条数
        String sqltotal =   "SELECT * FROM T_OLDPEOPLE";
        /*pstmt = JdbcFactory.getPstmt(conn,sqltotal);
        try {
            ResultSet rst = pstmt.executeQuery();
            rst.next();
            total =Integer.parseInt( rst.getString("NUM"));
            mapruturn.put("total",rst.getString("NUM")) ;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/
        total = total(sqltotal);
        mapruturn.put("total",total);

        String sql ="select * from  (  SELECT t.*,ROWNUM rum FROM T_OLDPEOPLE t where ROWNUM<="+pagenum*rowsnum+") where rum >"+(pagenum-1)*rowsnum+"";
        pstmt = JdbcFactory.getPstmt(conn,sql);
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Map<String,Object> obj = new HashMap<String, Object>();
                obj.put("name",rs.getString("name"));      //前面“name”与htm页面的name的命名一致，后面和数据库中的命名一致
                obj.put("gender",rs.getString("gender"));
                obj.put("birthday",rs.getString("birthd"));
                obj.put("place",rs.getString("address")) ;
                obj.put("peopleid",rs.getString("identityid"));
                list.add(obj);
            }
            //rs.close();

           // mapruturn.put("total",total);
            mapruturn.put("rows",list);
           // return JSONArray.fromObject(list);
            return JSONObject.fromObject(mapruturn).toString();
        } catch (SQLException e) {

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }/*finally {
            if(null!=pstmt){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }*/
        //return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public String findLrbasicInfo(String lrname,String pages,String rows) {       //查询
        int total = 0;
        int pagenum = Integer.parseInt(pages);
        int rowsnum = Integer.parseInt(rows);
        Map<String,Object> searchmap = new HashMap<String, Object>();
        Connection conn = JdbcFactory.getConn("oracle");
        PreparedStatement pstmt = null;
        String tsql = "select * from T_OLDPEOPLE WHERE NAME LIKE '"+lrname+"%'";
        total = total(tsql);
        String sql = "SELECT * FROM (SELECT t.*,ROWNUM rum FROM T_OLDPEOPLE t WHERE NAME LIKE '"+lrname+"%' AND ROWNUM<="+pagenum*rowsnum+") WHERE rum >"+(pagenum-1)*rowsnum+" ";
        pstmt = JdbcFactory.getPstmt(conn,sql);
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Map<String,Object> obj = new HashMap<String, Object>();
                obj.put("lrid",rs.getString("LR_ID"));
                obj.put("name",rs.getString("name"));
                obj.put("gender",rs.getString("gender"));
                obj.put("birthday",rs.getString("birthd"));
                obj.put("place",rs.getString("address")) ;
                obj.put("peopleid",rs.getString("identityid"));
                list.add(obj);
               // total++;
            }
            searchmap.put("total",total);
            searchmap.put("rows",list);
            //rs.close();    //要记得close
            //return JSONArray.fromObject(list);
            return  JSONObject.fromObject(searchmap).toString();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        } /*finally {
            if(null!=pstmt){
                try {
                    pstmt.close();   //建立的连接对象，记得close
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }*/
        //return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public String editLrbasicInfo() {
        String peopleid=request.getParameter("peopleid");
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        //StringBuffer sb=new StringBuffer();
        //String node=request.getParameter(peopleid);
        String sql="SELECT * FROM T_OLDPEOPLE WHERE identityid='"+peopleid+"'";
        List list=commonDbUtil.query(sql);
        Map map=new HashMap();
        if(list.size()>0){
            map=(Map)list.get(0);
        }
        return JSONObject.fromObject(map).toString();

    }

    public int total(String sql){
        int total = 0;
        Connection conn = JdbcFactory.getConn("oracle");
        PreparedStatement pstmt = null;
        pstmt = JdbcFactory.getPstmt(conn,sql);
        ResultSet rst = null;
        try {
            rst = pstmt.executeQuery();
            while(rst.next())  {
                total++;
            }
            return  total;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return 0;
        }


    }
}
