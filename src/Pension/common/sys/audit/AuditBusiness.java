package Pension.common.sys.audit;

import Pension.common.*;
import Pension.common.db.DbUtil;
import Pension.common.sys.LogBean;
import Pension.common.sys.userlog.UserLog;
import Pension.jdbc.JdbcFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
    public String query(String method,Map map,String loginname,String dvcode) throws AppException {
        int page=Integer.parseInt(map.get("page").toString());
        int rows=Integer.parseInt(map.get("rows").toString());
        String sub="select f.location" +
                "  from xt_rolefunc rf, xt_function f, xt_roleuser ru, xt_user u" +
                "  where f.nodetype = '2'" +
                "   and rf.functionid = f.functionid" +
                "   and rf.roleid = ru.roleid" +
                "   and ru.userid = u.userid" +
                "   and u.loginname = '"+loginname+"'";
        String sql_divsion;//=" and exists(select 1 from dvhz dv where dv.dvhigh='"+dvcode+"' and dv.dvcode=b.dvcode)";
        sql_divsion=" and (b.dvcode like '"+dvcode+"%' or '"+dvcode+"'='330100')" ;
        String sql="select a.auditid,a.auflag,a.aulevel,a.audesc,b.opseno,b.digest,b.tprkey,b.functionid,b.bsnyue,b.bstime,b.dvcode,b.loginname,b.username," +
                " '1' audefault from opaudit a,xt_userlog b,xt_user u where u.loginname=b.loginname and a.opseno=b.opseno and b.functionid='"
                +method+"' and a.auendflag='0' and to_char(to_number(a.aulevel)+1) in("+sub+") "+sql_divsion +" order by b.opseno desc";

        return JSONObject.fromObject(CommQuery.query(sql,page,rows)).toString();
    }

    /*
    审核,调用各业务模块的审核接口,
    通过,三级审核,每审核一次回调一次,第三次再附加更新结束标示
    不通过,当前级别不通过,也回调接口,附加更新结束标示和不通过原因(备注)
     */
    public String doBanchAudit(String method,JSONArray jarray,String loginname) throws Exception {

        for(int i=0;i<jarray.size();i++){
            Map map= ParameterUtil.toMap((JSONObject) jarray.get(i));
            String auditid=map.get("auditid").toString();
            if(null==auditid){
                return "缺少参数auditid";
            }
            map.put("auuser",loginname+"");
            Map where=new HashMap();
            where.put("auditid",map.get("auditid"));

            int aulevel=Integer.parseInt(map.get("aulevel").toString())+1;
            map.put("aulevel",aulevel+"");

            doOneAuditWithOpLog(auditid,aulevel,map,where);
        }
        return RtnType.SUCCESS;
    }

    private void doOneAudit(String auditid,int aulevel,Map map,Map where) throws Exception {
        CommonDbUtil commonDbUtil=new CommonDbUtil();

        if("1".equals(map.get("auflag"))){   //审核成功 :0不通过1通过
            if(aulevel==3){                   //3级审核结束
                map.put("auendflag", "1");
            }
            commonDbUtil.updateTableVales(map, "opaudit", where);
        }else{    //不成功,也应该调用doAudit;;;;;;;;;;
            map.put("auendflag", "1");        //不通过,审核结束
            commonDbUtil.updateTableVales(map, "opaudit", where);
        }
        CallBack.doAudit(Long.parseLong(auditid));   //回调
    }
    private void doOneAuditWithOpLog(String auditid,int aulevel,Map map,Map where) throws Exception {
        CallableStatement cstmt= null;
        CallableStatement cstmtd= null;
        DbUtil.get();
        try {
            DbUtil.begin();
            cstmt=DbUtil.get().prepareCall("{call glog.cutab()}");
            cstmt.execute();
            cstmt.close();
            UserLog.AddAuditLog(Long.parseLong(auditid),"审核通过测试");       //用户日志
            map.put("auopseno", new LogBean().getLocalLog().get("opseno")); //审核日志号

            doOneAudit(auditid,aulevel,map,where);

            cstmtd=DbUtil.get().prepareCall("{call glog.dutab()}");
            cstmtd.execute();
            cstmtd.close();
            DbUtil.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DbUtil.rollback();
        }finally {
            DbUtil.close();
        }

    }


    public Map queryCurrentAudit(String functionid,String tprkey) throws AppException {
        Map map=CommQuery.query("select a.* from xt_userlog b,opaudit a where a.auditid=b.auditid and b.functionid='"
                +functionid+"' and b.tprkey='"+tprkey+"'");
        List list=(List)map.get(IParam.ROWS);
        if(list.size()>0){
            return (Map)list.get(0);
        }
        return null;
    }

    public Map queryHistoryAudit(String functionid,String tprkey) throws AppException {
        Map map=CommQuery.query("select h.* from xt_userlog b,opaudithistory h where h.auditid=b.auditid and b.functionid='"
                +functionid+"' and b.tprkey='"+tprkey+"'");
        return map;
    }

}
