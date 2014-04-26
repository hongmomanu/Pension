package Pension.common.sys.audit;

import Pension.common.AppException;
import Pension.common.CommQuery;
import Pension.common.CommonDbUtil;
import Pension.common.IParam;
import Pension.common.sys.LogBean;
import Pension.common.sys.ReqBean;
import Pension.common.sys.util.CurrentUser;
import Pension.common.sys.util.SysUtil;
import Pension.jdbc.JdbcFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-15
 * Time: 下午8:01
 */
public class AuditManager {



    public static void addAudit(Long paramLong)
    {
        LogBean logBean =new LogBean();
        Long opseno=Long.parseLong(logBean.getLocalLog().get("opseno").toString());
        System.out.println("操作日志流水号-审核:"+opseno);
        CommonDbUtil dbUtil =new CommonDbUtil();
        String sql_audit="insert into opaudit(opseno,auditid,auflag,auendflag,aulevel)values("+opseno+",SEQ_OPAUDIT.NEXTVAL,0,0,'0')";
        String sql_userlog="update xt_userlog set  tprkey="+paramLong+" where opseno="+opseno;
        dbUtil.execute(sql_audit);
        dbUtil.execute(sql_userlog);
    }

}
