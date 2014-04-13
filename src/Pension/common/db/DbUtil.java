package Pension.common.db;

import java.sql.Connection;

/**
 * User: Administrator
 * Date: 14-4-13
 * Time: 上午7:52
 */
public class DbUtil {

    public static Connection get(){
        return ConnManager.getConnection();
    }
    public static void close(){
        ConnManager.closeConnection();
    }
    public static void begin(){
        ConnManager.beginTransaction();
    }
    public static void commit(){
        ConnManager.commitTransaction();
    }
    public static void rollback(){
        ConnManager.rollbackTransaction();
    }
}
