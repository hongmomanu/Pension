package Pension.model.pension;

import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.model.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-10
 * Time: 下午1:55
 */
public class EvaluateLrInfoChange extends Model {
    private HttpServletRequest request;
    private Connection conn;

    public String save(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Map map= ParameterUtil.toMap(request);
        String digest=request.getParameter("name")+
                request.getParameter("identityid")+
                request.getParameter("registration");
        int result=0;
        Long id=Long.parseLong((String)map.get("pg_id"));
        map.put("pg_id",id);
        /*AuditManager.addAudit(id, "wJhlMNIq8C20mH7Bm6tj", tablename,
                this.getClass().getName(), digest,
                request.getSession().getAttribute("loginname").toString(),
                request.getSession().getAttribute("username").toString(),
                request.getSession().getAttribute("dvcode").toString()
        );*/
        Map where=new HashMap();
        where.put("pg_id",id);
        result=commonDbUtil.updateTableVales(map,"t_needassessmentsum",where);
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }
}
