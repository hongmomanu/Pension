package Pension.model.hzyl;

import Pension.common.CommonDbUtil;
import Pension.common.MakeRandomString;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.common.sys.audit.AuditBean;
import Pension.common.sys.audit.AuditManager;
import Pension.common.sys.audit.IMultilevelAudit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluateLrInfo implements IMultilevelAudit {

    private HttpServletRequest request;
    private Connection conn;

    public String save(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Map map= ParameterUtil.toMap(request);
        String digest=request.getParameter("name")+
                request.getParameter("identityid")+
                request.getParameter("registration");
        int result=0;
        Long id=commonDbUtil.getSequence("SEQ_T_NEEDASSESSMENT");
        map.put("pg_id",id);
        String tablename="t_needassessment";
        AuditManager.addAudit(id, "wJhlMNIq8C20mH7Bm6tj", tablename,
                this.getClass().getName(), digest,
                request.getSession().getAttribute("loginname").toString(),
                request.getSession().getAttribute("username").toString(),
                request.getSession().getAttribute("dvcode").toString()
                );
        commonDbUtil.insertTableVales(map,"t_needassessmentsum");
        result=commonDbUtil.insertTableVales(map,tablename);
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }
    public String query(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Integer page=Integer.parseInt(request.getParameter("page"));
        Integer rows=Integer.parseInt(request.getParameter("rows"));

        Map map= new HashMap();
        String sql="select o.name,o.identityid,o.birthd,o.gender,o.age,o.nation,o.address,o.type,o.registration" +
                ",n.* from t_needassessment n,t_oldpeople o where o.lr_id=n.lr_id order by n.pg_id desc";
        List list=commonDbUtil.query("SELECT * FROM (SELECT tt.*, ROWNUM ro FROM ("+sql+") tt WHERE ROWNUM <="+(page)*rows+") WHERE ro > "+(page-1)*rows);
        int count=commonDbUtil.query(sql).size();
        map.put("total",count);
        map.put("rows",list);
        return JSONObject.fromObject(map).toString();
    }
    public String queryById(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Integer id=Integer.parseInt(request.getParameter("id"));

        Map map= new HashMap();
        String sql="SELECT t1.*,t2.* from t_needassessment t1,t_needassessmentsum t2 where t1.pg_id=t2.pg_id and t1.pg_id="+id;
        List list=commonDbUtil.query(sql);
        if(list.size()>0){
            map=(Map)list.get(0);
        }
        return JSONObject.fromObject(map).toString();
    }

    /*审核成功后的回调
    * 设置评估信息的状态为有效
    * */
    @Override
    public Long audit(Connection conn,AuditBean auditBean) {
        Long pg_id=Long.parseLong(auditBean.getTprkey());
        CommonDbUtil dbUtil=new CommonDbUtil(conn);
        String sql="update t_needassessment set active='1' where pg_id="+pg_id;
        String sql2="update t_needassessmentsum set pingguenddate=sysdate where pg_id="+pg_id;
        dbUtil.execute(sql);
        dbUtil.execute(sql2);
        return null;
    }
}
