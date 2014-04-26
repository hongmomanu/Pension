package Pension.test;

import Pension.common.db.DbUtil;
import org.junit.Test;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-14
 * Time: 上午9:02
 */
public class Testlocal {
    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public void setLocal(String str){
        local.set(str);
    }
    public String getLocal(){
        return local.get();
    }

    @Test
    public void test() throws SQLException {
        CallableStatement cstmt=DbUtil.get().prepareCall("{call cutab()}");
        cstmt.execute();
        cstmt.close();
        DbUtil.close();
    }
}
