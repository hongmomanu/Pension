package Pension.model.manager;

import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.model.Model;
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
public class Role extends Model {

    private HttpServletRequest request;
    private Connection conn;

    public String queryRole(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        String userid=request.getParameter("userid");
        String sql="select r.*,'true' selected from xt_role r";
        if(null!=userid){
            String useridsql="select r.roleid from xt_user u,xt_roleuser r where u.userid=r.userid and u.userid='"+userid+"'";
            sql="select a.*,'true' selected from xt_role a where a.roleid in(" + useridsql+
                    " ) union " +
                    " select a.*,'false' selected from xt_role a where a.roleid not in("+useridsql+")";
        }
        List list=commonDbUtil.query(sql);
        Map map=new HashMap();
        map.put("total",0);
        map.put("rows",list);
        return JSONObject.fromObject(map).toString();
    }


    public String saveRole(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Map map= ParameterUtil.toMap(request);
        //map.put("createdate",) 指定时间
        int result=commonDbUtil.insertTableVales(map,"xt_role");
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }

    public String deleteRole(){
        return null;
    }
}
