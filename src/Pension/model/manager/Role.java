package Pension.model.manager;

import Pension.common.AppException;
import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.common.sys.util.CurrentUser;
import Pension.common.sys.util.SysUtil;
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

    public int queryRole() throws AppException {
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        CurrentUser user= SysUtil.getCacheCurrentUser();
        String userid=user.getUserid();
        String sql="select r.*,'true' selected from xt_role r";
        if(null!=userid){
            String useridsql="select r.roleid from xt_user u,xt_roleuser r where u.userid=r.userid and u.userid='"+userid+"'";
            sql="select a.*,'true' selected from xt_role a where a.roleid in(" + useridsql+
                    " ) union " +
                    " select a.*,'false' selected from xt_role a where a.roleid not in("+useridsql+")";
        }
        query(sql,1,100);
        return RtnType.NORMALSUCCESS;
    }


    public String saveRole(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Map map= ParameterUtil.toMap(request);
        //map.put("createdate",) 指定时间
        int result=commonDbUtil.insertTableVales(map,"xt_role");
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }

    public String deleteRole(){
        return null;
    }
}
