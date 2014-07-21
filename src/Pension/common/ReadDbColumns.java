package Pension.common;

import Pension.jdbc.JdbcFactory;

import java.sql.*;
import java.util.*;

/**
 * User: Administrator
 * Date: 14-4-2
 * Time: 下午1:54
 */
public class ReadDbColumns {
    public static void  query(String sql) throws SQLException {
        Connection conn= JdbcFactory.getConn();
        Statement stmt=conn.createStatement();
        //SELECT * FROM USER_COL_COMMENTS WHERE TABLE_NAME='T_BLOODSUGARFLUP';
        ResultSetMetaData metaData=stmt.executeQuery(sql).getMetaData();
        int count=metaData.getColumnCount();
        String buf="";
        for(int i=1;i<=count;i++){
            //buf+=metaData.getColumnName(i).toLowerCase()+",";
            buf+="<th data-options=\"field:'"+metaData.getColumnName(i).toLowerCase()+"'\">"
                    +metaData.getColumnName(i).toUpperCase()+"</th>\n";
        }
        System.out.println(buf);
        stmt.close();
        conn.close();
    }
    public static void  query2(String tableName) throws SQLException {
        Connection conn= JdbcFactory.getConn();
        Statement stmt=conn.createStatement();
        String sql="sELECT * FROM USER_COL_COMMENTS WHERE TABLE_NAME='"+tableName+"'";
        ResultSet rs=stmt.executeQuery(sql);
        List list=new ArrayList();
        while(rs.next()){
            Map map=new HashMap();
            map.put("column_name",rs.getString("COLUMN_NAME").toLowerCase());
            map.put("comments", rs.getString("comments"));
            list.add(map);
        }
        String buf="";
        stmt.close();
        conn.close();

        Iterator it1=list.iterator();
        while(it1.hasNext()){
            Map m=(Map)it1.next();
            buf+="<th data-options=\"field:'"+m.get("column_name")+"',width:70,align:'center'\">"
                    +m.get("comments")+"</th>\n";
        }
        System.out.println(buf);
        System.out.println("########################################");

        String buf2="<tr>";
        int count=0;
        Iterator it2=list.iterator();
        while(it2.hasNext()){
            Map m=(Map)it2.next();
            String tr="";
            if(count%4==0){
                tr="</tr>\n<tr>" ;
            }
            count++;
            buf2+=tr+"<td class=\"formtdtext\"><label>"+m.get("comments")+"</label></td>\n" +
                    "<td><input  name=\""+m.get("column_name")+"\"></td>";
        }
        System.out.println(buf2);
    }
    public static  void main(String[] args) throws SQLException {
        //ReadDbColumns.query("select * from T_BLOODSUGARFLUP");
        ReadDbColumns.query2("T_HEALTHEXAMINATIONINFO");
        //<th data-options="field:'name',width:100">姓名</th>
    }
}