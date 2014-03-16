package Pension.model.manager;

import Pension.conmmon.CommonDbUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-16
 * Time: 上午10:41
 */
public class Role {

    private HttpServletRequest request;
    private Connection conn;

    public String queryRole(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        List list=commonDbUtil.query("select * from jet_role");
        Map map=new HashMap();
        map.put("total",0);
        map.put("rows",list);
        return JSONObject.fromObject(map).toString();
    }


    public String saveRole(){
        return null;
    }

    public String deleteRole(){
        return null;
    }
}
