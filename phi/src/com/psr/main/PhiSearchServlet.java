package com.psr.main;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Servlet implementation class PhiSearchServlet
 */
public class PhiSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String email,name;
	PhiServerDelegate d1 = new PhiServerDelegate();
	PhiNASDelegate d2 = new PhiNASDelegate();
	ArrayList <PhiServerBean> myServers = new ArrayList<PhiServerBean>();
	ArrayList <PhiNASBean> myNAS = new ArrayList<PhiNASBean>();
	public int no_of_servers,no_of_free_servers;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhiSearchServlet() {
        super();
        
    }
    
    private String getName(String email)
    {
    	String n;
    	int index;
    	if(EmailValidator.getInstance().isValid(email))
    	{
    	index=email.indexOf('@');
    	n=email.substring(0,index);
     	n=n.replace('.', ' ');
    	n=WordUtils.capitalize(n);
    	return n;
    	}
    	else
    	return null;
    }
    
    private String[] fielder(String search)
    {
		int index;
		String [] fields = new String [2];
		index=search.indexOf(':');
		if(index==-1)
			{
			return fields;
			}
    	if(search.charAt(index+1)==' ')
			{
			fields[0]=search.substring(0,index-1);
			fields[1]=search.substring(index+2);
 			}
		else
		{
			fields[0]=search.substring(0,index);
			fields[1]=search.substring(index+1);
 		}
    	return fields;
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] search=request.getParameterValues("SearchString");
		String [] query;
		//System.out.println(request.getParameterValues("SearchString"));
    	ArrayList <String> filters=new ArrayList <String>();
    	ArrayList <String> values=new ArrayList <String>();
		HttpSession session = request.getSession(false);
		ArrayList<PhiServerBean> servers = new ArrayList<PhiServerBean>();
		ArrayList<PhiNASBean> nas = new ArrayList<PhiNASBean>();
		int i,free=0,unfree=0,nas_flag=0;
		if(session == null)
			{
			System.out.println("null session");
			StringBuffer url=request.getRequestURL();
			i=url.lastIndexOf("/");
			int j=url.length();
			url.replace(i+1, j, "PhiWelcome");
			response.sendRedirect(url.toString());
			return;
			}
		else
			email=(String) session.getAttribute("email");
		System.out.println("Login:" + email);
        myServers=d1.getUserData(email);
        myNAS=d2.getUserData(email);
        no_of_free_servers=d1.getFreeCount();
		no_of_servers=d1.getTotalCount();

		if (search==null || search.length==0)
			{
				name = getName(email);
				request.setAttribute("message1", "Welcome " + name);
				request.setAttribute("email", email);
				request.setAttribute("no_of_servers", no_of_servers);
				request.setAttribute("no_of_free_servers", no_of_free_servers);
				request.setAttribute("servers",myServers);
				request.setAttribute("nas", myNAS);
				request.getRequestDispatcher("Search.jsp").forward(request, response);
			}
		else
			{	
				for(i=0;i<search.length;i++)
				{
					query=fielder(search[i]);
					if(query[0].equals("all"))
						{
						filters.add("server_name");
						values.add("%");
						nas=d2.getAllData();
						}
					if(query[0].equals("name"))
						{
						filters.add("server_name");
						values.add(query[1].toUpperCase());
						}
					if(query[0].equals("site"))
						{
						filters.add("site");
						values.add(query[1].toUpperCase());
						}
					if(query[0].equals("user"))
						{
						filters.add("puser");
						values.add(query[1].toLowerCase());
						nas=d2.getUserData(query[1].toLowerCase());
						}
					if(query[0].equals("OS"))
						{
						filters.add("os");
						values.add(query[1]);
						}
					if(query[0].equals("ST"))
					{
						filters.add("server_type");
						values.add(query[1]);
					}
					if(query[0].equals("NAS"))
					{
						nas=d2.getAllData();
						nas_flag = 1;
					}
					
				}
				if(nas_flag==0)
				servers=d1.getCustomData(filters, values);
				
				if(servers.isEmpty() && nas.isEmpty())
					request.setAttribute("message", "No Results");
				else
				{
				i=0;
				while(i<servers.size())
				{
					if(!servers.get(i).getPuser().equals("unassigned"))
						{
							servers.get(i).setPuser_name(getName(servers.get(i).getPuser()));
							unfree=1;
						}
					else
						{
							servers.get(i).setPuser_name(servers.get(i).getPuser());
							free=1;
						}
					if(servers.get(i).getManager()!=null)
						servers.get(i).setManager_name(getName(servers.get(i).getManager()));
					else
						servers.get(i).setManager_name("NA");
					i++;
				}
				i=0;
				while(i<nas.size())
				{
					if(!nas.get(i).getPuser().equals("unassigned"))
						nas.get(i).setPuser_name(getName(nas.get(i).getPuser()));
					else
						nas.get(i).setPuser_name(nas.get(i).getPuser());
					i++;
				}}
				nas_flag=0;
				if(free==0)
					request.setAttribute("free", "no");
				else
					request.setAttribute("free", "yes");
				if(unfree==0)
					request.setAttribute("unfree", "no");
				else
					request.setAttribute("unfree", "yes");
				request.setAttribute("message", "Results");
				request.setAttribute("servers", servers);
				request.setAttribute("nas",nas);
				request.setAttribute("email", email);
				request.getRequestDispatcher("Main.jsp").forward(request, response);
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	doGet(request,response);
	
	}

}
