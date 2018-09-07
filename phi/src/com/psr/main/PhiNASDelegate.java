package com.psr.main;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PhiNASDelegate {

	private Connection conn;
	private Statement st;
	private ResultSet rs;

	public PhiNASDelegate()
	{
		getConn();
	}

	private void getConn()
	{
		try {
			Context envCtx = (Context) new InitialContext().lookup("java:comp/env");
			//DataSource  ds = (DataSource) envCtx.lookup("jdbc/TestDB");
			DataSource  ds = (DataSource) envCtx.lookup("jdbc/ProdDB");
			conn=ds.getConnection();
			if (conn==null)
			{
				System.out.println("PhiNASDelegate connection failed");
			}
			else
				System.out.println("PhiNASDelegate connection");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public ArrayList<PhiNASBean> getAllData()
	{	
		System.out.println("NAS ALL data");
		ArrayList<PhiNASBean> nas = new ArrayList<PhiNASBean>();
		int i=0;
		String query = "select * from NAS";
		//System.out.println(query);
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return nas;
			}
			st = conn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				nas.add(new PhiNASBean());
				//System.out.println ("NAS_name:" + rs.getString("NAS_name"));
				nas.get(i).setServer_name(rs.getString("Server_name"));
				nas.get(i).setNas_name(rs.getString("nas_name"));
				nas.get(i).setMount_path(rs.getString("mount_path"));
				nas.get(i).setShare_name(rs.getString("share_name"));
				nas.get(i).setDspace_total(rs.getString("dspace_total"));
				nas.get(i).setStatus(rs.getString("status"));
				nas.get(i).setSite(rs.getString("site"));
				nas.get(i).setPuser(rs.getString("puser"));
				//System.out.println(nas.get(i).getPuser());
				nas.get(i).setPproject(rs.getString("pproject"));
				if(rs.getString("comments")!=null)
					nas.get(i).setComments(rs.getString("comments"));
				else
					nas.get(i).setComments("none");
				nas.get(i).setDspace_free(rs.getString("dspace_free"));
				i++;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return nas;
	}

	public ArrayList<PhiNASBean> getNameData(String name) {
		System.out.println("Name data");
		ArrayList<PhiNASBean> nas = new ArrayList<PhiNASBean>();
		int i = 0;
		String query = "select * from nas where server_name like '" + name + "%'";
		System.out.println(query);
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return nas;
			}
			st = conn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				nas.add(new PhiNASBean());
				nas.get(i).setServer_name(rs.getString("Server_name"));
				nas.get(i).setNas_name(rs.getString("nas_name"));
				nas.get(i).setMount_path(rs.getString("mount_path"));
				nas.get(i).setShare_name(rs.getString("share_name"));
				nas.get(i).setDspace_total(rs.getString("dspace_total"));
				nas.get(i).setStatus(rs.getString("status"));
				nas.get(i).setSite(rs.getString("site"));
				nas.get(i).setPuser(rs.getString("puser"));
				//System.out.println(nas.get(i).getPuser());
				nas.get(i).setPproject(rs.getString("pproject"));
				if(rs.getString("comments")!=null)
					nas.get(i).setComments(rs.getString("comments"));
				else
					nas.get(i).setComments("none");
				nas.get(i).setDspace_free(rs.getString("dspace_free"));
				i++;			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return nas;
	}

	public ArrayList<PhiNASBean> getUserData(String name) {
		System.out.println("User data");
		ArrayList<PhiNASBean> nas = new ArrayList<PhiNASBean>();
		int i=0;
		String query = "select * from nas where puser like '" + name + "%'";
		//System.out.println(query);
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return nas;
			}
			st = conn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				nas.add(new PhiNASBean());
				nas.get(i).setServer_name(rs.getString("Server_name"));
				nas.get(i).setNas_name(rs.getString("nas_name"));
				nas.get(i).setMount_path(rs.getString("mount_path"));
				nas.get(i).setShare_name(rs.getString("share_name"));
				nas.get(i).setDspace_total(rs.getString("dspace_total"));
				nas.get(i).setStatus(rs.getString("status"));
				nas.get(i).setSite(rs.getString("site"));
				nas.get(i).setPuser(rs.getString("puser"));
				//System.out.println(nas.get(i).getPuser());
				nas.get(i).setPproject(rs.getString("pproject"));
				if(rs.getString("comments")!=null)
					nas.get(i).setComments(rs.getString("comments"));
				else
					nas.get(i).setComments("none");
				nas.get(i).setDspace_free(rs.getString("dspace_free"));
				i++;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return nas;
	}

	public int setRow(PhiNASBean a)
	{
		String query = "update nas " +
		"set puser='" + a.getPuser() + "',pproject='" + a.getPproject() + "',comments='" + a.getComments() +"' " +
		"where nas_name='" + a.getNas_name() + "' and share_name='" + a.getShare_name() +"'";
		//System.out.println(query);
		int count = 0;
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return count;
			}
			st = conn.createStatement();
			count = st.executeUpdate(query);
			return count;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public ArrayList<PhiNASBean> getNASData(String nas_name, String share_name) {
		System.out.println("NAS data");
		ArrayList<PhiNASBean> nas = new ArrayList<PhiNASBean>();
		int i = 0;
		String query = "select * from nas where nas_name='" + nas_name + "' and share_name='" + share_name + "'";
		System.out.println(query);
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return nas;
			}
			st = conn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				nas.add(new PhiNASBean());
				nas.get(i).setServer_name(rs.getString("Server_name"));
				nas.get(i).setNas_name(rs.getString("nas_name"));
				nas.get(i).setMount_path(rs.getString("mount_path"));
				nas.get(i).setShare_name(rs.getString("share_name"));
				nas.get(i).setDspace_total(rs.getString("dspace_total"));
				nas.get(i).setStatus(rs.getString("status"));
				nas.get(i).setSite(rs.getString("site"));
				nas.get(i).setPuser(rs.getString("puser"));
				//System.out.println(nas.get(i).getPuser());
				nas.get(i).setPproject(rs.getString("pproject"));
				if(rs.getString("comments")!=null)
					nas.get(i).setComments(rs.getString("comments"));
				else
					nas.get(i).setComments("none");
				nas.get(i).setDspace_free(rs.getString("dspace_free"));
				i++;			
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return nas;
	}

	public ArrayList<PhiNASBean> getCustomData(ArrayList<String> filters, ArrayList<String> values) {
		System.out.println("Custom data");
		ArrayList<PhiNASBean> nas = new ArrayList<PhiNASBean>();
		int i=0;String query1;
		StringBuffer query = new StringBuffer();
		query.append("select * from nas where ");
		for(i=0;i<filters.size();i++)
		{
			if(i==0)
				query.append(filters.get(i) + " like '" + values.get(i) + "%'");
			else 
				query.append(" and " + filters.get(i) + " like '" + values.get(i) + "%'");
		}
		try {
			i=0;
			if (conn==null)
			{
				System.out.println("Null Connection");
				return nas;
			}
			st = conn.createStatement();
			query1=query.toString();
			System.out.println(query1);
			rs=st.executeQuery(query1);
			while(rs.next())
			{
				nas.add(new PhiNASBean());
				nas.get(i).setServer_name(rs.getString("Server_name"));
				nas.get(i).setNas_name(rs.getString("nas_name"));
				nas.get(i).setMount_path(rs.getString("mount_path"));
				nas.get(i).setShare_name(rs.getString("share_name"));
				nas.get(i).setDspace_total(rs.getString("dspace_total"));
				nas.get(i).setStatus(rs.getString("status"));
				nas.get(i).setSite(rs.getString("site"));
				nas.get(i).setPuser(rs.getString("puser"));
				//System.out.println(nas.get(i).getPuser());
				nas.get(i).setPproject(rs.getString("pproject"));
				if(rs.getString("comments")!=null)
					nas.get(i).setComments(rs.getString("comments"));
				else
					nas.get(i).setComments("none");
				nas.get(i).setDspace_free(rs.getString("dspace_free"));
				i++;			
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return nas;
	}


}
