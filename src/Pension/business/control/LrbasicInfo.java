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
    public JSONArray findLrbasicInfo() {
        Connection conn = JdbcFactory.getConn("oracle");
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM T_OLDPEOPLE";
        pstmt = JdbcFactory.getPstmt(conn,sql);
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Map<String,Object> obj = new HashMap<String, Object>();
                obj.put("name",rs.getString("name"));
                obj.put("place",rs.getString("address")) ;
                obj.put("peopleid",rs.getString("identityid"));
                list.add(obj);
            }
            //rs.close();
            return JSONArray.fromObject(list);
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

    public JSONArray findLrbasicInfo(String lrname) {
        Connection conn = JdbcFactory.getConn("oracle");
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM T_OLDPEOPLE where name like '"+lrname+"%'";
        pstmt = JdbcFactory.getPstmt(conn,sql);
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Map<String,Object> obj = new HashMap<String, Object>();
                obj.put("lrid",rs.getString("LR_ID"));
                obj.put("name",rs.getString("name"));
                obj.put("place",rs.getString("address")) ;
                obj.put("peopleid",rs.getString("identityid"));
                list.add(obj);
            }
            //rs.close();    //要记得close
            return JSONArray.fromObject(list);
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
}
