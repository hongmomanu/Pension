package Pension.common.sys.util;

import java.util.Date;
import java.util.List;

/**
 * User: Administrator
 * Date: 14-3-17
 * Time: 下午8:05
 */
public class CurrentUser {
    private String userid;
    private String passwd;
    private String loginName;
    private String userName;
    private String regionid;
    private String description;
    private Date logonTime;
    private String userOtherInfo;
    private List<Object> functionList;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegionid() {
        return regionid;
    }

    public void setRegionid(String regionid) {
        this.regionid = regionid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLogonTime() {
        return logonTime;
    }

    public void setLogonTime(Date logonTime) {
        this.logonTime = logonTime;
    }

    public String getUserOtherInfo() {
        return userOtherInfo;
    }

    public void setUserOtherInfo(String userOtherInfo) {
        this.userOtherInfo = userOtherInfo;
    }

    public List<Object> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<Object> functionList) {
        this.functionList = functionList;
    }
}
