package org.sella.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.sella.dbaccess.DBAccessImpl;

/**
 * Servlet implementation class ContentFeeder
 */ 
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		System.out.println("Came to servlet");
		StringBuffer jb = new StringBuffer();
		String line = null;
		 JSONObject responseObj=new JSONObject();
		 
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null) {
		      jb.append(line);
		    }
		    System.out.println("jb ::: "+jb);
		    JSONParser parser = new JSONParser();
		    JSONObject jsonObject =  (JSONObject) parser.parse(jb.toString());
//		    System.out.println("Users :: "+DBAccessImpl.getInstance().getUsers());
		    JSONArray array = (JSONArray) parser.parse(DBAccessImpl.getInstance().getUsers());
		    System.out.println(array);
		    array.add(jsonObject);
		    DBAccessImpl.getInstance().writeUser(array.toJSONString());
		    responseObj.put("result", "true");
		    responseObj.put("message", "successfully user added");
		  } catch (Exception e) { 
			  e.printStackTrace();
			    responseObj.put("result", "false");
			    responseObj.put("message", e.getMessage());
		  } finally {
			  System.out.println("Json String");
			  response.getWriter().print(responseObj.toJSONString() );
		  }
	}
	

}
