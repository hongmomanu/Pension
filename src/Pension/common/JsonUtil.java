package Pension.common;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: weipan
 * Date: 14-3-3
 * Time: 下午8:00
 * Desc: SQL结果集转JSON对象
 */
public class JsonUtil {

    private static String totalName="totalCount";
    private static String successName="success";
    private static String rowsName="results";
    /*
    根据查询结果生成JSONOBJECT所要的map
     */
    public static Map generateJsonMap(ResultSet rs,Integer totalCount,Boolean success,Map m) throws SQLException {

        ResultSetMetaData metaData= rs.getMetaData();
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
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
        Map<String,Object>res=new HashMap<String, Object>();

        if(null!=totalCount)res.put(totalName,totalCount);
        if(null!=totalCount)res.put(successName,true);
        if(null!=m){
            m.putAll(res);
        }

        res.put(rowsName,list);
        return res;
    }
    /*
    根据查询结果生成JSONOBJECT所要的map
     */
    public static Map generateJsonMap(ResultSet rs,int totalCount,boolean success) throws SQLException {
        return generateJsonMap(rs,totalCount,success,null);
    }
    public static Map generateJsonMap(ResultSet rs) throws SQLException {
        return generateJsonMap(rs,null,null,null);
    }

    /*
    * 根据查询结果生成JSONArray所需要的list
    * */
    public static List generateJsonList(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData= rs.getMetaData();
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
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
        return list;

    }
}
