<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="notice.*" %>

<%
	request.setCharacterEncoding("utf-8");

	int num = Integer.parseInt(request.getParameter("num"));
	String pageNum = request.getParameter("pageNum");
	
	NoticeDAO dao = NoticeDAO.getDao();
	dao.deleteArticle(num);
	
	// 관리자가 맞는지 관리자 비밀번호 확인
	
	
	response.sendRedirect("list.jsp?pageNum="+pageNum);
%>
