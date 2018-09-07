package com.psr.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PhiWelcome
 */
public class PhiWelcome extends HttpServlet implements Serializable ,ServletContextListener {
	private static final long serialVersionUID = 1L;
	private PhiAuto p1 = new PhiAuto();
	private PhiServerDelegate d1 = new PhiServerDelegate();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhiWelcome() {
        super();
      }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
				InputStream fIn = PhiServerDelegate.class.getResourceAsStream("email.csv");
				BufferedReader br = new BufferedReader(new InputStreamReader(fIn));
				String strLine = null;
				String [] emails = null;
				while ((strLine = br.readLine()) != null)   {
				emails=strLine.split(",");
			}
				request.setAttribute("emails", emails);
				request.getRequestDispatcher("Welcome.jsp").forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);  
		if(session.isNew())
			doGet(request, response);
		session.setAttribute("email", request.getParameter("email"));
		session.setMaxInactiveInterval(1800);
		response.sendRedirect("PhiSearch");
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event)
	  {
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						int count=0;
						while(true)
						{
							try {
								Thread.sleep(1000000);
								count++;
								System.out.println("Starting Drive data update " + count);
								ArrayList <String> filters=new ArrayList <String>();
								ArrayList <String> values=new ArrayList <String>();
								filters.add("os");
								values.add("Linux");
								ArrayList<PhiServerBean> servers = d1.getCustomData(filters, values);
								
								for (PhiServerBean s : servers)
								{
									Thread.sleep(1000);
									s=p1.ServerDriveInfo(s);
									d1.setRow(s);
									//break;
								}
								System.out.println("Finished Drive data update " + count);
								Thread.sleep(86400000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}
