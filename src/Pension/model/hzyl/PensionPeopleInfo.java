package Pension.model.hzyl;

import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.common.sys.audit.AuditManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-20
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public class PensionPeopleInfo {
    private HttpServletRequest request;
    private Connection conn;

    public String save(){

        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Map map= ParameterUtil.toMap(request);
        int result=0;
        Long id=commonDbUtil.getSequence("seq_t_oldpeople");
        map.put("id",id);
        //AuditManager.addAudit(id);
        result=commonDbUtil.insertTableVales(map,"t_oldpeople");
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }
}
