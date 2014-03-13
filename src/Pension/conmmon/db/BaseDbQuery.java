package Pension.conmmon.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public Integer querySequence(String sequencename){
        Statement stmt=null;
        Integer result=0;
        try {
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select "+sequencename+".nextval from dual");
            if(rs.next()){
                result=rs.getInt(1);
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
}
