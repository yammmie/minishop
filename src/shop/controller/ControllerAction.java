package shop.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*; // HashMap

import shop.action.CommandAction;

public class ControllerAction extends HttpServlet {
	private Map map = new HashMap();
	
	public void init(ServletConfig config) throws ServletException {
		String path = config.getServletContext().getRealPath("/");
		String pros = path + config.getInitParameter("proFile");
		Properties pr = new Properties();
		FileInputStream f = null;
		
		try {
			f = new FileInputStream(pros);
			
			pr.load(f);
		} catch(Exception e) {
			System.out.println("파일 읽기 에러 : " + e);
		} finally {
			try {
				if(f != null)
					f.close();
			} catch(Exception e2) {}
		}
		
		Iterator keys = pr.keySet().iterator();
		
		while(keys.hasNext()) {
			String key = (String)keys.next();
			String className = pr.getProperty(key);
			
			try {
				Class commandClass = Class.forName(className);
				Object commandObject = commandClass.newInstance();
				
				map.put(key, commandObject);
			} catch(Exception e) {
				System.out.println(".properties 파일 내용을 클래스 객체로 만드는 중 예외 : " + e);
			}
		}
	}
	
	public void reqPro(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String viewName = "";
		CommandAction commandAction = null;
		String uri;
		
		try {
			uri = request.getRequestURI();
			
			if(uri.indexOf(request.getContextPath()) == 0) {
				uri = uri.substring(request.getContextPath().length());
			}	
			
			commandAction = (CommandAction)map.get(uri);
			viewName = commandAction.requestPro(request, response);
		} catch(Throwable t) {
			throw new ServletException(t);
		}
		
		/*if(viewName.equals("/member/main.jsp"))
			request.setAttribute("main", "/member/main.jsp");
		else
			request.setAttribute("CONTENT", viewName);*/
		
//		RequestDispatcher rd = request.getRequestDispatcher(viewName);
//		RequestDispatcher rd = request.getRequestDispatcher("/template/template.jsp");
		
		if(viewName.equals("/user/productDetailPro.jsp") || viewName.equals("/admin/cateListPro.jsp") || viewName.equals("/admin/productInsertSelect.jsp"))
			request.setAttribute("selDetail", viewName);
//			request.setAttribute("detail", viewName);
			
		
		request.setAttribute("CONTENT", viewName);
			
		RequestDispatcher rd = request.getRequestDispatcher("/template/template.jsp");
		
		rd.forward(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		reqPro(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		reqPro(request, response);
	}
}
