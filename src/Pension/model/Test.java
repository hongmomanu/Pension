package Pension.model;

import Pension.common.CommonDbUtil;

import java.sql.Connection;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-13
 * Time: 下午4:07
 */
public class Test {
    private Map param;
    private Integer i;
    private String a;
    private Connection conn;
    public String test(){
       return new CommonDbUtil(conn).isExist("select * from users")+"";
    }
}
