<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.*" %>
<%@ page import="java.util.*" %>

<%
	request.setCharacterEncoding("utf-8");

	int pro_no = Integer.parseInt(request.getParameter("pro_no"));
	String color = request.getParameter("color");

	ProductMgr proMgr = ProductMgr.getDao();
	int cnt = proMgr.getCountStock(pro_no, color);
	List<ProductDTO> stockList = proMgr.getProductStock(pro_no, color);
	
	String size[] = new String[cnt];
	String stock[] = new String[cnt];
	
	for(int i=0; i<stockList.size(); i++) {
		ProductDTO stockDto = (ProductDTO)stockList.get(i);
		
		size[i] = stockDto.getSize();
		stock[i] = stockDto.getStock();
	}
%>
	<option value="">[필수] 사이즈</option>
	<option>-------------------------------</option>
	
	<%
		for(int i=0; i<size.length; i++) {
			if(stock[i].equals("0")) {
		%>
				<option value="<%=size[i]%>" disabled="disabled"><%= size[i] %> [ 품절 ]</option>
		<%		
			} else {
		%>
				<option value="<%=size[i]%>"><%= size[i] %></option>
		<%
			}
		}
	%>