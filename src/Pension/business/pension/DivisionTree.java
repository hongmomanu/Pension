package Pension.business.pension;

import Pension.common.CommonDbUtil;
import Pension.jdbc.JdbcFactory;
import net.sf.json.JSONArray;

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
 * Date: 14-4-15
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
public class DivisionTree {
    private Connection conn ;
    private HttpServletRequest request;


    public String divisiontree() {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> hangzhou = new HashMap<String,Object>();
        Map<String,Object> qu = new HashMap<String, Object>();
        hangzhou.put("id",330100);
        hangzhou.put("text","杭州市");
        hangzhou.put("children",divisionChildren("3","330100")) ;
        list.add(hangzhou);
        return JSONArray.fromObject(list).toString();  //To change body of created methods use File | Settings | File Templates.
    }

    public List<Map<String, Object>> divisionChildren(String dvrank,String dvhigh){
        List<Map<String, Object>> childlist = new ArrayList<Map<String,Object>>();
        Connection conn = JdbcFactory.getConn("oracle");
        /*int dvcode = Integer.getInteger(dvrank);
        System.out.print(dvcode);*/
        String sql = "SELECT * FROM division WHERE Dvrank = '"+dvrank+"' and dvhigh = '"+dvhigh+"'";
        PreparedStatement pstmt   = JdbcFactory.getPstmt(conn, sql);
        try {
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Map<String, Object> child = new HashMap<String, Object>();
                child.put("dvcode",rs.getString("dvcode"));
                child.put("text",rs.getString("totalname"));
               /* if(!( rs.getString("dvrank").equals("5"))) {                               // !( rs.getString("dvrank").equals("5"));
                    child.put("children",divisionChildren((Integer.parseInt(rs.getString("dvrank"))+1)+"",rs.getString("dvcode")));
                }*/
                childlist.add(child);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return childlist;
    }


}