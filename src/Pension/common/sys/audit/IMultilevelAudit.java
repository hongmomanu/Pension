package Pension.common.sys.audit;

import java.sql.Connection;

/**
 * User: Administrator
 * Date: 14-3-15
 * Time: 下午8:01
 */
public interface IMultilevelAudit {
    public abstract Long audit(AuditBean auditBean);

}
