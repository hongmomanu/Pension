package Pension.jdbc;

import Pension.common.Config;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * User:
 * Date: 14-3-13
 * Time: 上午10:33
 */
public class DBPool {
    private static DBPool dbPool;
    private ComboPooledDataSource dataSource;

    static {
        dbPool = new DBPool();
    }

    private DBPool() {
        try {
            /*dataSource = new ComboPooledDataSource();
            dataSource.setUser("CIVILAFFAIRS_ZS");
            dataSource.setPassword("hvit");
            dataSource.setJdbcUrl("jdbc:oracle:thin:@192.168.2.141:1521:orcl");
            dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
            dataSource.setInitialPoolSize(2);
            dataSource.setMinPoolSize(1);
            dataSource.setMaxPoolSize(10);
            dataSource.setMaxStatements(50);
            dataSource.setMaxIdleTime(60);*/

            Config dbconfig = Config.getConfig("config.properties");
            dataSource = new ComboPooledDataSource();
            dataSource.setUser(dbconfig.getValue("orclusername"));
            dataSource.setPassword(dbconfig.getValue("orclpassword"));
            dataSource.setJdbcUrl(dbconfig.getValue("orclurl"));
            dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
            dataSource.setInitialPoolSize(2);
            dataSource.setMinPoolSize(1);
            dataSource.setMaxPoolSize(10);
            dataSource.setMaxStatements(50);
            dataSource.setMaxIdleTime(60);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }

    public final static DBPool getInstance() {
        return dbPool;
    }

    public final Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("无法从数据源获取连接 ", e);
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        try {
            con = DBPool.getInstance().getConnection();
        } catch (Exception e) {
        } finally {
            if (con != null)
                con.close();
        }
    }
}