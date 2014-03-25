package Pension.business.control;

import Pension.jdbc.JdbcFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 上午11:00
 * To change this template use File | Settings | File Templates.
 */
public class LrbasicInfo {
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
            return JSONArray.fromObject(list);
        } catch (SQLException e) {

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
        //return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
