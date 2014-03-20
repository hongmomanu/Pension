package Pension.common.sys.audit;

import Pension.common.CommonDbUtil;
import Pension.jdbc.JdbcFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * User: Administrator
 * Date: 14-3-15
 * Time: 下午8:01
 */
public class AuditManager {
    public static void addAudit(Long paramLong)
    {
        //_$1().addAudit(paramLong);
        Connection conn=JdbcFactory.getConn();

        CommonDbUtil dbUtil=new CommonDbUtil(conn);
        Long auditid=dbUtil.getSequence("SEQ_OPAUDIT");
        String sql_auditbean="insert into opauditbean(auditid,beanvalue) values("+auditid+","+paramLong+")";
        String sql_audit="insert into opaudit(auditid,auflag,auendflag)values("+auditid+",0,0)";
        dbUtil.execute(sql_auditbean);
        dbUtil.execute(sql_audit);
        try {
            if(null!=conn)conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
