package Pension.propertycheck.control;

import Pension.jdbc.JdbcFactory;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-10
 * Time: 下午10:04
 * To change this template use File | Settings | File Templates.
 */
public class FamilyPropertyQuery {
    private static final Logger log = Logger.getLogger( FamilyPropertyQuery.class);
    //private SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Connection conn=null;
    public FamilyPropertyQuery(){
        conn= JdbcFactory.getConn("sqlite");
    }

    public void test(Map paraMap){
        Statement stmt=null;
        try{
            stmt=conn.createStatement();
            int len=100;
            for(int i=0;i<len;i++){
                Date d1=new Date();
                Thread.sleep(1000/2);
                //stmt.execute("insert into A values(null,current_timestamp)");
                System.out.println("执行第"+i+"次新增数据操作,耗时(毫秒):"+(new Date().getTime()-d1.getTime()));
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
