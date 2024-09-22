<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	int pro_no = Integer.parseInt(request.getParameter("pro_no"));
	String sel_color = request.getParameter("sel_color");
	String sel_size = request.getParameter("sel_size");
	int quantity = Integer.parseInt(request.getParameter("quantity"));
%>

Cart.jsp<br>

pro_no : <%= pro_no %>
sel_color : <%= sel_color %>
sel_size : <%= sel_size %>
quantity : <%= quantity %>

