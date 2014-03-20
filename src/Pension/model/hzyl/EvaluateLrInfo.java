package Pension.model.hzyl;

import Pension.common.CommonDbUtil;
import Pension.common.MakeRandomString;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.common.sys.audit.AuditManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class EvaluateLrInfo {

    private HttpServletRequest request;
    private Connection conn;

    public String save(){

        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Map map= ParameterUtil.toMap(request);
        int result=0;
        Long id=commonDbUtil.getSequence("opseno");
        map.put("id",id);
        AuditManager.addAudit(id);
        result=commonDbUtil.insertTableVales(map,"tt");
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }
}
