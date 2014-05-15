package Pension.model.pension;

import Pension.common.*;
import Pension.common.sys.annotation.OpLog;
import Pension.common.sys.audit.AuditBean;
import Pension.common.sys.audit.AuditManager;
import Pension.common.sys.audit.IMultilevelAudit;
import Pension.model.Model;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-10
 * Time: 下午1:55
 */
public class EvaluateLrInfoLogout extends Model implements IMultilevelAudit {

    /*
    保存注销数据
     */
    @OpLog
    public String save() throws AppException, SQLException {
        Map map= ParameterUtil.toMap(this.getRequest());
        int result=0;
        Long id=Long.parseLong((String)map.get("pg_id"));
        AuditManager.addAudit(id);
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Long bgid=commonDbUtil.getSequence("SEQ_T_NEEDASSESSMENTBG");
        Map bgmap=new HashMap();
        bgmap.put("bgid",bgid);
        bgmap.put("pg_id",id);
        bgmap.put("bgreason",map.get("bgreason"));
        bgmap.put("bgtype", "2"); //2为注销
        result=commonDbUtil.insertTableVales(bgmap,"t_needassessmentbg");
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }

    /*审核接口实现*/
    @Override
    public Long audit(AuditBean auditBean) {
        Long pg_id=Long.parseLong(auditBean.getTprkey());
        CommonDbUtil dbUtil=new CommonDbUtil();
        Map currentAudit=auditBean.getCurrentAudit();
        String level=(String)currentAudit.get("aulevel");
        String auflag=(String)currentAudit.get("auflag");
        if("3".equals(level)){
            dbUtil.execute("update t_needassessmentbg set bgactive='"+auflag+
                    "' where bgid=(select max(a.bgid) from t_needassessmentbg a where a.pg_id ="+pg_id+")");
            dbUtil.execute("update t_needassessment set active='2' where pg_id="+pg_id);
        }
        return null;
    }
}
