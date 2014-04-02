package Pension.common;

import Pension.jdbc.JdbcFactory;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * User: Administrator
 * Date: 14-4-2
 * Time: 下午1:54
 */
public class ReadDbColumns {
    public static void  query(String sql) throws SQLException {
        Connection conn= JdbcFactory.getConn();
        Statement stmt=conn.createStatement();
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
    public static  void main(String[] args) throws SQLException {
        ReadDbColumns.query("select * from t_oldpeople");
        //<th data-options="field:'name',width:100">姓名</th>
    }
}