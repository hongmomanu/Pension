package Pension.common.sys.audit;

import Pension.business.entity.User;
import Pension.common.AppException;
import Pension.common.CommQuery;
import Pension.common.CommonDbUtil;
import Pension.common.IParam;
import Pension.common.sys.ReqBean;
import Pension.jdbc.JdbcFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-15
 * Time: 下午8:01
 */
public class AuditManager {



    public static void addAudit(Long paramLong)
    {
        ReqBean reqBean=new ReqBean();
        HttpServletRequest req=reqBean.getLocalReq();
        User user=(User)req.getSession().getAttribute("user");
        String functionid=(String)req.getParameter("functionid");
        //_$1().addAudit(paramLong);
        CommonDbUtil dbUtil=new CommonDbUtil();
        Long auditid=dbUtil.getSequence("SEQ_OPAUDIT");
        Map map=new HashMap();
        String digest=makeDigest(getFunctionBSDigest(functionid),req);
        map.put("auditid",auditid);
        map.put("tprkey",paramLong);
        map.put("functionid",functionid);
        map.put("digest",digest);
        map.put("loginname",user.getLoginname());
        map.put("username",user.getUsername());
        map.put("dvcode",user.getRegionid());

        dbUtil.insertTableVales(map,"opauditbean");
        String sql_auditbean="insert into opauditbean(auditid,beanvalue) values("+auditid+","+paramLong+")";
        String sql_audit="insert into opaudit(auditid,auflag,auendflag,aulevel)values("+auditid+",0,0,'0')";
        dbUtil.execute(sql_auditbean);
        dbUtil.execute(sql_audit);
    }
    private static JSONArray getFunctionBSDigest(String functionid) {
        Map map= null;
        try {
            map = CommQuery.query("select bsdigest from xt_function where functionid='" + functionid + "'");
        } catch (AppException e) {
            e.printStackTrace();
        }
        List list=(List)map.get(IParam.ROWS);
        if(list.size()>0){
            return JSONArray.fromObject(((Map)list.get(0)).get("bsdigest"));
        }else{
            System.out.println("没有functionid或者没有业务摘要的相关配置");
            return null;
        }
    }

    private static String makeDigest(JSONArray array,HttpServletRequest request){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<array.size();i++){
            JSONObject obj=array.getJSONObject(i);
            sb.append(obj.getString("label")+request.getParameter(obj.getString("property"))+" ");
        }
        return sb.toString();
    }
}
