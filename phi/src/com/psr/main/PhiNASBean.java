package com.psr.main;

public class PhiNASBean {
    private String nas_name,share_name,server_name,mount_path,dspace_total,status,site,puser,pproject,comments,puser_name,dspace_free;
        
	@Override
	public String toString() {
		return "PhiBean [server_name=" + server_name +  ", dspace_total=" + dspace_total + ", status=" + status
				+ ", site=" + site + ", puser=" + puser + ", pproject=" + pproject + ", comments=" + comments + ", puser_name="
				+ puser_name + ", dspace_free=" + dspace_free + "]";
	}

	public String getNas_name() {
		return nas_name;
	}

	public void setNas_name(String nas_name) {
		this.nas_name = nas_name;
	}

	public String getShare_name() {
		return share_name;
	}

	public void setShare_name(String share_name) {
		this.share_name = share_name;
	}

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public String getMount_path() {
		return mount_path;
	}

	public void setMount_path(String mount_path) {
		this.mount_path = mount_path;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPuser_name() {
		return puser_name;
	}

	public void setPuser_name(String puser_name) {
		this.puser_name = puser_name;
	}

	public String getDspace_free() {
		return dspace_free;
	}

	public void setDspace_free(String dspace_free) {
		this.dspace_free = dspace_free;
	}

	
}

