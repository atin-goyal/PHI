package com.psr.main;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PhiDetail
 */
public class PhiDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PhiServerDelegate d1 = new PhiServerDelegate();
	private PhiNASDelegate d2 = new PhiNASDelegate();

	/**
     * @see HttpServlet#HttpServlet()
     */
    public PhiDetail() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
		HttpSession session = request.getSession(false);
		String email = null;
		if(session!=null)
			email=(String) session.getAttribute("email");
		else
			{
			System.out.println("null session");
			StringBuffer url=request.getRequestURL();
			int i=url.lastIndexOf("/");
			int j=url.length();
			url.replace(i+1, j, "PhiWelcome");
			response.sendRedirect(url.toString());
			return;
			}
		ArrayList<PhiServerBean> servers = new ArrayList<PhiServerBean>();
		ArrayList<PhiNASBean> nas = new ArrayList<PhiNASBean>();
		
		if (action.equals("server_detail"))
		{
			String name = request.getParameter("name");
			servers = d1.getNameData(name);
			if(!servers.isEmpty())
				{
				request.setAttribute("email", email);
				request.setAttribute("servers", servers);
				request.getRequestDispatcher("Detail.jsp").forward(request, response);
				}			
		}
		else
		if (action.equals("nas_detail"))
		{
			String nas_name=request.getParameter("nas_name");
			String share_name=request.getParameter("share_name");
			nas = d2.getNASData(nas_name,share_name);
			if(!nas.isEmpty())
			{
				request.setAttribute("email", email);
				request.setAttribute("nas", nas);
				request.getRequestDispatcher("Detail.jsp").forward(request, response);
			}	

		}
		else
		{
			request.setAttribute("message", "Invalid Request");
			request.getRequestDispatcher("PhiSearch").forward(request, response);
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
