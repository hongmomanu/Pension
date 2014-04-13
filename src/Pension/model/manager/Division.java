package Pension.model.manager;

import Pension.common.CommonDbUtil;
import Pension.model.Model;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: weipan
 * Date: 14-4-3
 * Time: 下午2:40
 */
public class Division extends Model {
    private HttpServletRequest request;
    private Connection conn;

    public String query(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        String userid=request.getParameter("userid");
        String keyword=request.getParameter("q");
        String sql="select t.*,t2.dvname parentname from division t,division t2 " +
                "where t.dvhigh=t2.dvcode and t.dvcode like '33010%' " +
                "and (t.dvcode like '"+keyword+"%' or t.dvname like '"+keyword+"%')";
        List list=commonDbUtil.query(sql);
        Map map=new HashMap();
        map.put("total",list.size());  //暂时总数这样写
        map.put("rows",list);
        return JSONObject.fromObject(map).toString();
    }
}
