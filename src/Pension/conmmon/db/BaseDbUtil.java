package Pension.conmmon.db;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: weipan
 * Date: 14-3-13
 * Time: 下午1:38
 * Desc: 基本的数据库操作
 */
public class BaseDbUtil {
    private static final Logger log = Logger.getLogger(BaseDbUtil.class);
    private Connection conn=null;
    public BaseDbUtil(Connection conn){
        this.conn=conn;
    }

    /*
    插入数据col_vals到tablename
     */
    public int insertTableVales(Map<String, Object> col_vals, String tablename) {
        return insertOrReplaceInto(col_vals,tablename,"INSERT INTO ");
    }

    /*
    插入数据col_vals到tablename,暂不使用
     */
    public int insertOrReplace(Map<String, Object> col_vals, String tablename) {
        return insertOrReplaceInto(col_vals,tablename,"INSERT OR REPLACE INTO ");
    }

    /*
    私有方法,插入数据的具体实现
     */
    private int insertOrReplaceInto(Map<String, Object> col_vals, String tablename,String insertorreplace) {
        List<String> list=this.getColumnData(tablename);
        int result=0;
        String col_str="";
        String val_str="";
        ArrayList<String> val_arr=new ArrayList<String>();
        Iterator iter = col_vals.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            if(null!=list&&!list.contains(key)){
                continue;
            }
            String val = col_vals.get(key).toString();
            val_arr.add(val);
            val_str+="?,";
            col_str+=key+",";

        }
        col_str.length();
        col_str=col_str.substring(0,col_str.length()-1);
        val_str=val_str.substring(0,val_str.length()-1);

        String sql = insertorreplace+" " + tablename +
                " ("+col_str+") values " +"("+val_str+")";
        log.debug(sql);
        PreparedStatement pstmt=null;
        try {
            pstmt = conn.prepareStatement(sql);
            int i=0;
            for( i=0;i<val_arr.size();i++){
                pstmt.setString(i+1, val_arr.get(i)==null?"":val_arr.get(i));
            }
            result=pstmt.executeUpdate();
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
            ex.printStackTrace();
            result= -1;
        }finally {
            try {
                if(null!=pstmt){
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  result;
    }



    public int updateTableValesSpecail(Map<String, Object> col_vals, String tablename, Map<String, String> colnames, String kandv) {
        List<String> list=this.getColumnData(tablename);
        Statement stmt=null;
        kandv=(null!=kandv&&!kandv.equals(""))?"and "+kandv:"";
        String whereStr=" where 1=1 "+kandv;
        String str="";
        int result=0;
        ArrayList<String> val_arr=new ArrayList<String>();
        Iterator iter = col_vals.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            if(null!=list&&!list.contains(key)){
                continue;
            }
            String val = col_vals.get(key).toString();
            val_arr.add(val);
            str+=key+"=?,";
            if(null!=colnames&&colnames.containsKey(key)){    //,应该改为?占位符
                whereStr+=" and "+key+"='"+colnames.get(key)+"'";
            }
        }
        str=str.substring(0,str.length()-1);
        String sql = "update " + tablename +" set "+str+whereStr;

        log.debug(sql);
        PreparedStatement pstmt=null;
        try {
            pstmt = conn.prepareStatement(sql);
            for( int i=0;i<val_arr.size();i++){
                pstmt.setString(i+1, val_arr.get(i)==null?"":val_arr.get(i));
            }
            result= pstmt.executeUpdate();
        } catch (SQLException ex) {
            log.debug(ex.getMessage());

        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.debug(e.getMessage());
            }
        }
        return result;
    }

    /*
    更新表数据
    @param col_vals 数据
    @param tablename 表名
    @param colnames 条件数据

     */
    public int updateTableVales(Map<String, Object> col_vals, String tablename, Map<String, String> colnames) {
        return this.updateTableValesSpecail(col_vals,tablename,colnames,"");
    }


    /*
    获取表的列属性信息
     */
    public List<String> getColumnData(String tablename) {
        Statement stmt=null;
        List<String> list=new ArrayList<String>();
        try {
            stmt=conn.createStatement();
            ResultSetMetaData mtdt=stmt.executeQuery("select * from "+tablename+ " where 1=0").getMetaData();
            for(int i=1;i<=mtdt.getColumnCount();i++){
                list.add(mtdt.getColumnName(i).toLowerCase());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null!=stmt){
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }



    /*
    判断查询结果是否有结果
     */
    public boolean isExist(String sql) {
        boolean flag=false;
        Statement pstmt=null;
        try {
            pstmt=conn.createStatement();
            ResultSet rs=pstmt.executeQuery(sql);
            if(rs.next()){
                flag=true;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            flag=false;
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /*执行一个sql语句*/
    public void execute(String sql) {
        log.debug(sql);
        PreparedStatement pstmt=null;
        try {
            pstmt=conn.prepareStatement(sql);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
