package com.psr.main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.EmailValidator;

public class PhiAssign extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PhiServerDelegate d1 = new PhiServerDelegate();
	private PhiNASDelegate d2 = new PhiNASDelegate();
	
	private PhiServerBean p1;
	private PhiNASBean p2;
	private PhiAuto a1=new PhiAuto();
    public PhiAssign() {
        super();
    }


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action=request.getParameter("action");
		String email=request.getParameter("email");
		int res;
		if ((action==null || action.isEmpty()) || (email==null || email.isEmpty() || !EmailValidator.getInstance().isValid(email)))
		{
			request.setAttribute("message", "Invalid Request");
			request.getRequestDispatcher("PhiSearch").forward(request, response);
		}
		else
		{
			if(action.equals("assign"))
			{
				System.out.println("assign");
				String project=request.getParameter("project");
				String name=request.getParameter("name");
				String comments=request.getParameter("comments");
				String drive=request.getParameter("drive");
				if (comments.indexOf('\'') != -1)
						{
							 comments=comments.replace('\'', '"');
						}
				p1=d1.getNameData(name).get(0);
				if(p1==null || (project==null || project.isEmpty()))
				{
					request.setAttribute("message", "Invalid Request");
					request.getRequestDispatcher("PhiSearch").forward(request, response);
				}
				else
				{
					p1.setPuser(email);
					p1.setPproject(project);
					p1.setComments(comments);
					p1.setDrive(drive);
					if(p1.getLuser()=="NA")
					{
						p1.setLuser(email);
					}
					
					new Thread(new Runnable()
                     {
                             @Override
                             public void run()
                             {
                            	 if (p1.getOs().indexOf("Linux")!=-1)
                            	 {
                                	 System.out.println("child started");
                            		 p1=a1.ServerDriveInfo(p1);
                            		 if (!p1.equals(null))
                            		 {
                            			try {
											//Thread.sleep(60000);  //sleep to allow main thread to complete its setRow transaction
											d1.setRow(p1);
											System.out.println("row set child"); 
										} catch (Exception e) {
											e.printStackTrace();
										}
										
                            		 }
                            	 }
                             }
                             
                     }).start();

					res=d1.setRow(p1);
					System.out.println("row set main");
					if(res==1)
					{
						request.setAttribute("message", "Machine assigned to you");
						request.getRequestDispatcher("PhiSearch").forward(request, response);
					}
					else
					{
						request.setAttribute("message", "Invalid Request");
						request.getRequestDispatcher("PhiSearch").forward(request, response);
					}
						
				}
			}
			else
				if(action.equals("unassign"))
				{
					System.out.println("unassign");
					String name=request.getParameter("name");
					p1=d1.getNameData(name).get(0);
					if (p1==null)
						{
								request.setAttribute("message", "Invalid Request");
								request.getRequestDispatcher("PhiSearch").forward(request, response);
						}
					else
					{
						p1.setLuser(p1.getPuser());
						p1.setPuser("unassigned");
						res=d1.setRow(p1);
						if(res==1)
						{
							Runtime.getRuntime().exec(new String[] { "bash", "-c", "/usr/bin/mutt -s \"PHI Notification: " + name + " unassigned" + "\"" + " < /dev/null"});
		                    request.setAttribute("message", "Machine unassigned");
							request.getRequestDispatcher("PhiSearch").forward(request, response);
						}
						
						else
						{
							request.setAttribute("message", "Invalid Request");
							request.getRequestDispatcher("PhiSearch").forward(request, response);
						}
					}
				}
				else
					if (action.equals("assign_nas"))
					{
						System.out.println("assign_nas");
						String nas_name=request.getParameter("nas_name");
						String share_name=request.getParameter("share_name");
						String project=request.getParameter("project");
						String comments=request.getParameter("comments");
						p2=d2.getNASData(nas_name,share_name).get(0);
						if(p2==null || project==null || project.isEmpty())
						{
							request.setAttribute("message", "Invalid Request");
							request.getRequestDispatcher("PhiSearch").forward(request, response);
						}
						else
						{
							p2.setPuser(email);
							p2.setPproject(project);
							p2.setComments(comments);
							res=d2.setRow(p2);
							if(res==1)
							{
								request.setAttribute("message", "Machine assigned to you");
								request.getRequestDispatcher("PhiSearch").forward(request, response);
							}
							else
							{
								request.setAttribute("message", "Invalid Request");
								request.getRequestDispatcher("PhiSearch").forward(request, response);
							}
								
						}
						
					}
					else
						if (action.equals("unassign_nas"))
						{
							System.out.println("unassign_nas");
							String nas_name=request.getParameter("nas_name");
							String share_name=request.getParameter("share_name");
							p2=d2.getNASData(nas_name,share_name).get(0);
							if (p2!=null)
							{
								p2.setPuser("unassigned");
								res=d2.setRow(p2);
								if(res==1)
								{
									request.setAttribute("message", "Machine unassigned");
									request.getRequestDispatcher("PhiSearch").forward(request, response);
								}
								else
								{
									request.setAttribute("message", "Invalid Request");
									request.getRequestDispatcher("PhiSearch").forward(request, response);
								}
							}
							else
							{
							request.setAttribute("message", "Invalid Request");
							request.getRequestDispatcher("PhiSearch").forward(request, response);
							}
						}
						if(action.equals("change_credentials"))
						{
							System.out.println("change_credentials by " + email);
							String username=request.getParameter("username");
							String name=request.getParameter("name");
							String password=request.getParameter("password");
							p1=d1.getNameData(name).get(0);
							if(p1==null || (username==null || username.isEmpty()) || (password==null || password.isEmpty()))
							{
								request.setAttribute("message", "Invalid Request");
								request.getRequestDispatcher("PhiSearch").forward(request, response);
							}
							else
							{
								p1.setUsername(username);
								p1.setPwd(password);
								res=d1.setRow(p1);
								System.out.println("row set main");
								if(res==1)
								{
									request.setAttribute("message", "Credentials changed for server " + name);
									request.getRequestDispatcher("PhiSearch").forward(request, response);
								}
								else
								{
									request.setAttribute("message", "Invalid Request");
									request.getRequestDispatcher("PhiSearch").forward(request, response);
								}
							}
						}
						else
						{
						request.setAttribute("message", "Invalid Request");
						request.getRequestDispatcher("PhiSearch").forward(request, response);
						}
		}
			
	}

	
}
