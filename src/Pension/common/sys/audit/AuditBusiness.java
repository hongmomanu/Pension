package Pension.common.sys.audit;

import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-21
 * Time: 下午9:23
 */
public class AuditBusiness {

    /*
    根据按钮的权限来限制查询结果
    根据统筹区来限制查询结果
     */
    public String query(CommonDbUtil commonDbUtil,String method,Map map,String loginname,String dvcode){
        String sub="select f.location" +
                "  from xt_rolefunc rf, xt_function f, xt_roleuser ru, xt_user u" +
                "  where f.nodetype = '2'" +
                "   and rf.functionid = f.functionid" +
                "   and rf.roleid = ru.roleid" +
                "   and ru.userid = u.userid" +
                "   and u.loginname = '"+loginname+"'";
        String sql_divsion;//=" and exists(select 1 from dvhz dv where dv.dvhigh='"+dvcode+"' and dv.dvcode=b.dvcode)";
        sql_divsion=" and (b.dvcode like '"+dvcode+"%' or '"+dvcode+"'='330100')" ;
        String sql="select a.*,b.*,u.username,'1' audefault from opaudit a,opauditbean b,xt_user u where u.loginname=b.loginname and a.auditid=b.auditid and b.functionid='"
                +method+"' and a.auendflag='0' and to_char(to_number(a.aulevel)+1) in("+sub+") "+sql_divsion +" order by b.auditid desc";
        List list=commonDbUtil.query(sql);
        return JSONArray.fromObject(list).toString();
    }
    public String doBanchAudit(CommonDbUtil commonDbUtil,String method,JSONArray jarray,String loginname) throws Exception {
        for(int i=0;i<jarray.size();i++){
            Map map= ParameterUtil.toMap((JSONObject) jarray.get(i));
            String auditid=map.get("auditid").toString();
            if(null==auditid){
                return "缺少参数auditid";
            }
            map.put("auuser",loginname+"");
            //map.put("audate", new java.sql.Date(new Date().getTime()));
            Map where=new HashMap();
            where.put("auditid",map.get("auditid"));

            if("1".equals(map.get("auflag"))){
                int aulevel=Integer.parseInt(map.get("aulevel").toString())+1;
                map.put("aulevel",aulevel+"");
                if(aulevel==3){
                    map.put("auendflag","1");
                    commonDbUtil.updateTableVales(map, "opaudit", where);
                    System.out.println("****************************************************************************");
                    System.out.println("审核全部通过,执行回调方法");
                    CallBack.doAudit(Long.parseLong(auditid));
                    System.out.println("****************************************************************************");
                }else{
                    commonDbUtil.updateTableVales(map,"opaudit",where);
                }
            }

        }
        return RtnType.SUCCESS;
    }

}
