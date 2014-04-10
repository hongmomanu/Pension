package Pension.model.hzyl;

import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.common.sys.audit.AuditManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-10
 * Time: 下午1:55
 */
public class EvaluateLrInfoChange {
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
}
