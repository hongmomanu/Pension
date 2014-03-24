package Pension.common.sys.audit;

/**
 * User: Administrator
 * Date: 14-3-15
 * Time: 下午8:00
 */
public class AuditBean {
    private Long auditid;
    private String tname;
    private String tprkey;
    private String classname;


    public Long getAuditid() {
        return auditid;
    }

    public void setAuditid(Long auditid) {
        this.auditid = auditid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTprkey() {
        return tprkey;
    }

    public void setTprkey(String tprkey) {
        this.tprkey = tprkey;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
