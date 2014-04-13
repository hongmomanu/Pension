package Pension.common;

import Pension.common.db.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-12
 * Time: 下午7:35
 */
public class CommQuery {
    /*
    分页查询,查询总数和分布数据
     */

    public static Map query(String sql,  int page, int rows) throws AppException
    {
        Map result=new HashMap();
        String sqlcount="select count(*) from ("+sql+")";
        String sqlfy=
                "SELECT * FROM (SELECT tt.*, ROWNUM ro FROM ("+sql+") tt WHERE ROWNUM <="+(page)*rows+") WHERE ro > "+(page-1)*rows;
        Connection conn= DbUtil.get();
        Statement stmt=null;
        ResultSet rs=null;
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        try {
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sqlfy);
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
            rs=stmt.executeQuery(sqlcount);
            int count=0;
            if(rs.next()){
                count=rs.getInt(1);
            }
            result.put(IParam.TOTAL,count);
            result.put(IParam.ROWS,list);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AppException("分页查询失败", e);
        }finally {
            try{
                if(null!=rs)rs.close();
                if(null!=stmt)stmt.close();
            }catch (SQLException e){
                 e.printStackTrace();
            }
        }

        return result;
    }


    public static Map query(String sql) throws AppException
    {
        Map result=new HashMap();
        Connection conn= DbUtil.get();
        Statement stmt=null;
        ResultSet rs=null;
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        try {
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
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

            result.put(IParam.ROWS,list);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AppException("分页查询失败", e);
        }finally {
            try{
                if(null!=rs)rs.close();
                if(null!=stmt)stmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return result;
    }



}