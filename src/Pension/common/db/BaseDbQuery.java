package Pension.common.db;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-13
 * Time: 下午2:25
 */
public class BaseDbQuery {
    private static final Logger log = Logger.getLogger(BaseDbQuery.class);
    private Connection conn=null;
    public BaseDbQuery(Connection conn){
        this.conn=conn;
    }

    //查询SEQUENCE的下一个值
    public Long querySequence(String sequencename){
        Statement stmt=null;
        Long result=0l;
        try {
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select "+sequencename+".nextval from dual");
            if(rs.next()){
                result=rs.getLong(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(null!=stmt){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    //查询,返回List<Map>
    public List<Map<String,Object>> query(String sql){
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Statement stmt=null;
        Integer result=0;
        try {
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            ResultSetMetaData metaData= rs.getMetaData();
            int columns=metaData.getColumnCount();
            while(rs.next()){
                Map<String,Object> map=new HashMap<String, Object>();
                for(int i=1;i<=columns;i++){
                    String columnName=metaData.getColumnName(i);
                    String columnValue=rs.getString(columnName);
                    map.put(columnName.toLowerCase(),columnValue);
                }
                list.add(map);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(null!=stmt){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
