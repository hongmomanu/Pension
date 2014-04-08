package Pension.jdbc;

import Pension.common.Config;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;

import javax.activation.DataSource;
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
    private static Long count=0l;
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
            System.out.print("*******************");
            printC3p0Status();
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("无法从数据源获取连接 ", e);
        }
    }
    public void printC3p0Status() throws SQLException {
        // make sure it's a c3p0 PooledDataSource
        if ( dataSource instanceof PooledDataSource) {
            PooledDataSource pds = (PooledDataSource) dataSource;
            System.out.print("   连接数量: " + pds.getNumConnectionsDefaultUser());
            System.out.print("   忙: " + pds.getNumBusyConnectionsDefaultUser());
            System.out.print("   空闲: " + pds.getNumIdleConnectionsDefaultUser());
            System.out.print("   取连接次数: " + (++DBPool.count));
            System.out.println();
        } else {
            System.out.println("Not a c3p0 PooledDataSource!");
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