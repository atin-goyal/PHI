package com.psr.main;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.*;
import javax.sql.*;


public class PhiServerDelegate {

	private Connection conn;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement ps;
	private String setrow_query="update servers set server_name=?, os=?, processor=?, ram=?, dspace_total=?, status=?, site=?, server_type=?, " +
								"puser=?, pproject=?, manager=?, comments=?, dspace_free=?, luser=?, username=?, pwd=?, drive=? where server_name=?";

	public PhiServerDelegate()
	{
		getConn();
		if (conn!=null)
		{
			try {
				ps=conn.prepareStatement(setrow_query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
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
				System.out.println("PhiServerDelegate connection failed");
			}
			else
				System.out.println("PhiServerDelegate connection");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<PhiServerBean> getNameData(String name) {
		System.out.println("Name data");
		ArrayList<PhiServerBean> servers = new ArrayList<PhiServerBean>();
		int i = 0;
		String query = "select * from servers where server_name like '" + name + "%' order by server_name";
		System.out.println(query);
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return servers;
			}
			st = conn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				servers.add(new PhiServerBean());
				//System.out.println ("Server_name:" + rs.getString("Server_name"));
				servers.get(i).setServer_name(rs.getString("Server_name"));
				servers.get(i).setOs(rs.getString("os"));
				servers.get(i).setProcessor(rs.getString("processor"));
				servers.get(i).setRam(rs.getString("ram"));
				servers.get(i).setDrive(rs.getString("drive"));
				servers.get(i).setDspace_total(rs.getString("dspace_total"));
				servers.get(i).setStatus(rs.getString("status"));
				servers.get(i).setSite(rs.getString("site"));
				servers.get(i).setServer_type(rs.getString("server_type"));
				servers.get(i).setPuser(rs.getString("puser"));
				servers.get(i).setManager(rs.getString("manager"));
				servers.get(i).setPproject(rs.getString("pproject"));
				if(rs.getString("comments")!=null)
					servers.get(i).setComments(rs.getString("comments"));
				else
					servers.get(i).setComments("none");
				servers.get(i).setDspace_free(rs.getString("dspace_free"));
				servers.get(i).setLuser(rs.getString("luser"));
				servers.get(i).setUsername(rs.getString("username"));
				servers.get(i).setPwd(rs.getString("pwd"));
				i++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return servers;
	}

	public ArrayList<PhiServerBean> getUserData(String name) {
		System.out.println("User data");
		ArrayList<PhiServerBean> servers = new ArrayList<PhiServerBean>();
		int i=0;
		String query = "select * from servers where puser like '" + name + "%' order by server_name";
		//System.out.println(query);
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return servers;
			}
			st = conn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				servers.add(new PhiServerBean());
				//System.out.println ("Server_name:" + rs.getString("Server_name"));
				servers.get(i).setServer_name(rs.getString("Server_name"));
				servers.get(i).setOs(rs.getString("os"));
				servers.get(i).setProcessor(rs.getString("processor"));
				servers.get(i).setRam(rs.getString("ram"));
				servers.get(i).setDrive(rs.getString("drive"));
				servers.get(i).setDspace_total(rs.getString("dspace_total"));
				servers.get(i).setStatus(rs.getString("status"));
				servers.get(i).setSite(rs.getString("site"));
				servers.get(i).setServer_type(rs.getString("server_type"));
				servers.get(i).setPuser(rs.getString("puser"));
				servers.get(i).setManager(rs.getString("manager"));
				servers.get(i).setPproject(rs.getString("pproject"));
				if(rs.getString("comments")!=null)
					servers.get(i).setComments(rs.getString("comments"));
				else
					servers.get(i).setComments("none");
				servers.get(i).setDspace_free(rs.getString("dspace_free"));
				servers.get(i).setLuser(rs.getString("luser"));
				servers.get(i).setUsername(rs.getString("username"));
				servers.get(i).setPwd(rs.getString("pwd"));
				i++;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return servers;
	}

	public ArrayList<PhiServerBean> getCustomData(ArrayList <String> filters, ArrayList <String> values) {
		System.out.println("Custom data");
		ArrayList<PhiServerBean> servers = new ArrayList<PhiServerBean>();
		int i=0,lastq=-1;String query1,temp = null;
		ArrayList<String> q = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		query.append("select * from servers");
		for(i=0;i<filters.size();i++)
		{
			if(i==0)
			{
				q.add("(" + filters.get(i) + " like '%" + values.get(i) + "%')");
				lastq=i;
			}
			else 
			{
				if(temp.equals(filters.get(i)))
				{
					temp="(" + q.get(lastq) + " or (" + filters.get(i) + " like '%" + values.get(i) + "%'))";
					q.set(lastq, temp);
				}
				else
				{
					q.add("(" + filters.get(i) + " like '%" + values.get(i) + "%')");
					lastq=i;
				}
			}
			temp=(filters.get(i));
		}
		for(i=0;i<q.size();i++)
			if(i==0)
				query.append(" where " + q.get(i));
			else
				query.append(" and " + q.get(i));
		query.append(" order by server_name");
		try {
			i=0;
			if (conn==null)
			{
				System.out.println("Null Connection");
				return servers;
			}
			st = conn.createStatement();
			query1=query.toString();
			System.out.println(query1);
			rs=st.executeQuery(query1);
			while(rs.next())
			{
				servers.add(new PhiServerBean());
				//System.out.println ("Server_name:" + rs.getString("Server_name"));
				servers.get(i).setServer_name(rs.getString("Server_name"));
				servers.get(i).setOs(rs.getString("os"));
				servers.get(i).setProcessor(rs.getString("processor"));
				servers.get(i).setRam(rs.getString("ram"));
				servers.get(i).setDrive(rs.getString("drive"));
				servers.get(i).setDspace_total(rs.getString("dspace_total"));
				servers.get(i).setStatus(rs.getString("status"));
				servers.get(i).setSite(rs.getString("site"));
				servers.get(i).setServer_type(rs.getString("server_type"));
				servers.get(i).setPuser(rs.getString("puser"));
				servers.get(i).setManager(rs.getString("manager"));
				servers.get(i).setPproject(rs.getString("pproject"));
				if(rs.getString("comments")!=null)
					servers.get(i).setComments(rs.getString("comments"));
				else
					servers.get(i).setComments("none");
				servers.get(i).setDspace_free(rs.getString("dspace_free"));
				servers.get(i).setLuser(rs.getString("luser"));
				servers.get(i).setUsername(rs.getString("username"));
				servers.get(i).setPwd(rs.getString("pwd"));
				i++;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return servers;
	}

	public ResultSet getCustomAdvData(String columns,String clause) {
		System.out.println("CustomAdv data");
		String query = "select " + columns + " from servers " + clause;
		System.out.println(query);
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return rs;
			}
			st = conn.createStatement();
			rs=st.executeQuery(query);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public int getFreeCount()
	{
		System.out.println("Free Count");
		int count=0;
		String query = "select count(*) from servers where puser like 'unassigned'";
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return count;
			}
			st = conn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				count=rs.getInt(1);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return count;

	}

	public int getTotalCount()
	{
		System.out.println("Total Count");
		int count=0;
		String query = "select count(*) from servers";
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return count;
			}
			st = conn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				count=rs.getInt(1);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return count;

	}

	public int setRow(PhiServerBean a)
	{
//		String query = "update servers " + "set server_name='" + a.getServer_name() + "', os='" +	a.getOs() + "', processor='" +	a.getProcessor() + 
//		"', ram='" +	a.getRam() + "', dspace_total='" +	a.getDspace_total() + "', status='" +	a.getStatus() + "', site='" +	a.getSite() + "', "
//		+ "server_type='" +	a.getServer_type() + "', puser='" +	a.getPuser() + "', pproject='" +	a.getPproject() + "', manager='" +	a.getManager()
//		+ "', comments='" +	a.getComments() + "', dspace_free='" +	a.getDspace_free() + "', luser='" +	a.getLuser() + "', username='" +
//		a.getUsername() + "', pwd='" +	a.getPwd() + "', drive='" +	a.getDrive() + "' where server_name='" + a.getServer_name() + "'";
//		System.out.println(query);
		int count = 0;
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return count;
			}
//			String setrow_query="update servers set server_name=?, os=?, processor=?, ram=?, dspace_total=?, status=?, site=?, server_type=?, " +
//			"puser=?, pproject=?, manager=?, comments=?, dspace_free=?, luser=?, username=?, pwd=?, drive=? where server_name=?";

			ps.setString(1, a.getServer_name());
			ps.setString(2, a.getOs());
			ps.setString(3, a.getProcessor());
			ps.setString(4, a.getRam());
			ps.setString(5, a.getDspace_total());
			ps.setString(6, a.getStatus());
			ps.setString(7, a.getSite());
			ps.setString(8, a.getServer_type());
			ps.setString(9, a.getPuser());
			ps.setString(10, a.getPproject());
			ps.setString(11, a.getManager());
			ps.setString(12, a.getComments());
			ps.setString(13, a.getDspace_free());
			ps.setString(14, a.getLuser());
			ps.setString(15, a.getUsername());
			ps.setString(16, a.getPwd());
			ps.setString(17, a.getDrive());
			ps.setString(18, a.getServer_name());
			
			//st = conn.createStatement();
			count = ps.executeUpdate();
			conn.commit();
			return count;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String getDriveData(String server_name) {
		System.out.println("Drive data");
		String query = "select DRIVE from DRIVES where SERVER_NAME='" + server_name + "'";
		String drive = null;
		//System.out.println(query);
		try {
			if (conn==null)
			{
				System.out.println("Null Connection");
				return drive;
			}
			st = conn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				drive=rs.getString("DRIVE");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { rs.close(); } catch (Exception ignore) { }
		}
		return drive;
	}
}
