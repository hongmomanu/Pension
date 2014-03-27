package Pension.model;

import Pension.common.CommonDbUtil;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-13
 * Time: 下午4:07
 */
public class Test {
    private HttpServletRequest request;
    private Map param;
    private Integer i;
    private String a;
    private Connection conn;
    public String test(){
       return new CommonDbUtil(conn).isExist("select * from users")+"";
    }

    public String editLrbasicInfo() {
        String peopleid=request.getParameter("peopleid");
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        //StringBuffer sb=new StringBuffer();
        //String node=request.getParameter(peopleid);
        String sql="SELECT * FROM T_OLDPEOPLE WHERE identityid='"+peopleid+"'";
        List list=commonDbUtil.query(sql);
        Map map=new HashMap();
        if(list.size()>0){
            map=(Map)list.get(0);
        }
        return JSONObject.fromObject(map).toString();

    }
}
