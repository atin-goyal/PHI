package com.psr.main;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class PhiSummary
 */
public class PhiCustom extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PhiServerDelegate d1 = new PhiServerDelegate();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhiCustom() {
        super();
    }
    
    public static JSONArray convertToJSON(ResultSet resultSet)
    throws Exception {
    	JSONArray jsonArray = new JSONArray();
    	while (resultSet.next()) {
    		int total_rows = resultSet.getMetaData().getColumnCount();
    		JSONObject obj = new JSONObject();
    		for (int i = 0; i < total_rows; i++) {
    			obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
    		}
    		jsonArray.put(obj);
    	}
    	return jsonArray;
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clause = request.getParameter("clause");
		String columns = request.getParameter("columns");
		ResultSet rs = null;JSONArray jsonArray = new JSONArray();
		int flag=0;String message = null;
		
		if (clause==null || clause.isEmpty() || columns==null || columns.isEmpty())
		{
			request.setAttribute("message", "Enter Query");
			request.getRequestDispatcher("Custom.jsp").forward(request, response);

		}
		else
		{
			try {
				rs=d1.getCustomAdvData(columns,clause);
				jsonArray=convertToJSON(rs);
			} catch (Exception e) {
				flag=1;
				e.printStackTrace();
			}
			finally {
				try { rs.close(); } catch (Exception ignore) { }
			}

			if (flag==0)
			{
				request.setAttribute("message", "Query Results");
				request.setAttribute("results", jsonArray);
				request.getRequestDispatcher("Custom.jsp").forward(request, response);
			}
			else
			{
				message="Invalid Query. Please check the syntax";
				request.setAttribute("message", message);
				request.getRequestDispatcher("Custom.jsp").forward(request, response);
			}
			
		}
		
		

	}

}
