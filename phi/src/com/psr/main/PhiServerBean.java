package com.psr.main;
 
public class PhiServerBean {
    private String server_name,os,processor,ram,dspace_total,status,site,server_type,puser,pproject,manager,comments,puser_name,manager_name,dspace_free;
    private String luser,username,pwd,drive;
    
	@Override
	public String toString() {
		return "PhiServerBean [server_name=" + server_name + ", os=" + os
				+ ", processor=" + processor + ", ram=" + ram
				+ ", dspace_total=" + dspace_total + ", status=" + status
				+ ", site=" + site + ", server_type=" + server_type
				+ ", puser=" + puser + ", pproject=" + pproject + ", manager="
				+ manager + ", comments=" + comments + ", puser_name="
				+ puser_name + ", manager_name=" + manager_name
				+ ", dspace_free=" + dspace_free + ", luser=" + luser
				+ ", username=" + username + ", pwd=" + pwd + ", drive="
				+ drive + "]";
	}
	public String getDrive() {
		return drive;
	}
	public void setDrive(String drive) {
		this.drive = drive;
	}
	public String getLuser() {
		return luser;
	}
	public void setLuser(String luser) {
		this.luser = luser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getPuser_name() {
		return puser_name;
	}
	public void setPuser_name(String puser_name) {
		this.puser_name = puser_name;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getRam() {
		return ram;
	}
	public void setRam(String ram) {
		this.ram = ram;
	}
	public String getDspace_total() {
		return dspace_total;
	}
	public void setDspace_total(String dspace_total) {
		this.dspace_total = dspace_total;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getServer_type() {
		return server_type;
	}
	public void setServer_type(String server_type) {
		this.server_type = server_type;
	}
	public String getPuser() {
		return puser;
	}
	public void setPuser(String puser) {
		this.puser = puser;
	}
	public String getPproject() {
		return pproject;
	}
	public void setPproject(String pproject) {
		this.pproject = pproject;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getDspace_free() {
		return dspace_free;
	}
	public void setDspace_free(String dspace_free) {
		this.dspace_free = dspace_free;
	}
   
}

