package Pension.model.hzyl;

import Pension.common.CommonDbUtil;
import Pension.common.MakeRandomString;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.common.sys.audit.AuditBean;
import Pension.common.sys.audit.AuditManager;
import Pension.common.sys.audit.IMultilevelAudit;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
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
        result=commonDbUtil.insertTableVales(map,tablename);
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }

    @Override
    public Long audit(AuditBean auditBean) {
        System.out.println(this.getClass().getName()+"*******************************************************************");
        System.out.println(auditBean.getAuditid()+"***回调函数执行*******************************************************");
        System.out.println(this.getClass().getName()+"*******************************************************************");
        return null;
    }
}
