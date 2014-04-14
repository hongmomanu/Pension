package Pension.business.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private String userid;
	private String passwd;
	private String loginname;
	private String dept;
	private String description;
	private String useful;
	private String regionid;
	private String username;
	private String owner;
	private String rate;
	private String otherinfo;
	private Date createdate;
	private String dvname;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String useful) {
		this.useful = useful;
	}

	/** full constructor */
	public User(String passwd, String loginname, String dept,
			String description, String useful, String regionid,
			String username, String owner, String rate, String otherinfo,
			Date createdate) {
		this.passwd = passwd;
		this.loginname = loginname;
		this.dept = dept;
		this.description = description;
		this.useful = useful;
		this.regionid = regionid;
		this.username = username;
		this.owner = owner;
		this.rate = rate;
		this.otherinfo = otherinfo;
		this.createdate = createdate;
	}

	// Property accessors

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUseful() {
		return this.useful;
	}

	public void setUseful(String useful) {
		this.useful = useful;
	}

	public String getRegionid() {
		return this.regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRate() {
		return this.rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getOtherinfo() {
		return this.otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

    public String getDvname() {
        return dvname;
    }

    public void setDvname(String dvname) {
        this.dvname = dvname;
    }
}