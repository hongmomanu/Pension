package Pension.model.pension;

import Pension.business.entity.User;
import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.common.sys.audit.AuditBean;
import Pension.common.sys.audit.AuditManager;
import Pension.common.sys.audit.IMultilevelAudit;
import Pension.model.Model;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluateLrInfo extends Model implements IMultilevelAudit {


    public String save(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Map map= ParameterUtil.toMap(this.getRequest());
        int result=0;
        Long id=commonDbUtil.getSequence("SEQ_T_NEEDASSESSMENT");
        map.put("pg_id",id);
        String tablename="t_needassessment";
        AuditManager.addAudit(id);
        commonDbUtil.insertTableVales(map,"t_needassessmentsum");
        result=commonDbUtil.insertTableVales(map,tablename);
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }
    public String query(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Integer page=Integer.parseInt(this.getRequest().getParameter("page"));
        Integer rows=Integer.parseInt(this.getRequest().getParameter("rows"));

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
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Integer id=Integer.parseInt(this.getRequest().getParameter("id"));

        Map map= new HashMap();
        String sql="SELECT t1.*,t2.* from t_needassessment t1,t_needassessmentsum t2 where t1.pg_id=t2.pg_id and t1.pg_id="+id;
        List list=commonDbUtil.query(sql);
        if(list.size()>0){
            map=(Map)list.get(0);
        }
        return JSONObject.fromObject(map).toString();
    }

    public String queryPeople(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Integer id=Integer.parseInt(this.getRequest().getParameter("id"));

        Map map= new HashMap();
        String sql="select * from t_oldpeople where lr_id="+id;
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
    public Long audit(AuditBean auditBean) {
        Long pg_id=Long.parseLong(auditBean.getTprkey());
        CommonDbUtil dbUtil=new CommonDbUtil();
        String sql="update t_needassessment set active='1' where pg_id="+pg_id;
        Map currentAudit=auditBean.getCurrentAudit();
        String comment=",";
        String level=(String)currentAudit.get("aulevel");
        if("1".equals(level)){
            comment+=" shequcomment";
        }else if("2".equals(level)){
            comment+=" xiangzhencomment";
        }else if("3".equals(level)){
            comment+=" minzhengcomment";
            dbUtil.execute(sql);
        }

        String sql2="";
        if(null==currentAudit.get("audesc")||"null".equals(currentAudit.get("audesc"))){
            sql2="update t_needassessmentsum set pingguenddate=sysdate where pg_id="+pg_id;
        }else{
            comment+="='"+currentAudit.get("audesc")+"' ";
            sql2="update t_needassessmentsum set pingguenddate=sysdate "+comment+" where pg_id="+pg_id;
        }
        dbUtil.execute(sql2);
        return null;
    }
}
