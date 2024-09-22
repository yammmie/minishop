<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="shopdb.*" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="orderMgr" class="shopdb.OrderMgr"/>
<jsp:useBean id="ordDto" class="shopdb.OrderDTO"/>
   
<%
	request.setCharacterEncoding("utf-8");

	int pro_no = Integer.parseInt(request.getParameter("pro_no"));
	int quantity = Integer.parseInt(request.getParameter("quantity"));
	String id = request.getParameter("id");
	String color = request.getParameter("color");
	String size = request.getParameter("size");
	String payment = request.getParameter("payment");
	
	int ordno = orderMgr.getOrdno()+1;
	
	ordDto.setOrdno(ordno);
	ordDto.setPro_no(pro_no);
	ordDto.setQuantity(quantity);
	ordDto.setId(id);
	ordDto.setColor(color);
	ordDto.setSize(size);
	ordDto.setPayment(payment);
	
	orderMgr.insertOrder(ordDto);
	
	ProductMgr proMgr = ProductMgr.getDao();
	proMgr.reduceProduct(quantity, pro_no, color, size);
	
	response.sendRedirect("OrderList.jsp");
%>