<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="shopdb.*" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="orderMgr" class="shopdb.OrderMgr"/>

<%
	request.setCharacterEncoding("utf-8");

	int pro_no = Integer.parseInt(request.getParameter("pro_no"));
	String color = request.getParameter("sel_color");
	String size = request.getParameter("sel_size");
	int quantity = Integer.parseInt(request.getParameter("quantity"));
	String id = "mm";
	
	MemberDAO memDao = MemberDAO.getInstance();
	MemberDTO memDto = memDao.getMember(id);
	
	ProductMgr proMgr = ProductMgr.getDao();
	ProductDTO proDto = proMgr.getProduct(pro_no);

	int totalPrice = 0;
	int stock = proMgr.getMaxReduce(pro_no, color, size);
	
	if(stock < quantity) {
	%>
		<script>
			alert("재고보다 많은 수량을 선택하셨습니다\n수량을 다시 선택해주세요");
			history.back();
		</script>
	<%
	}
		
%>

<html>
	<head>
		<style>
			body {
				margin: 20px 0;
			}
			
			#wrap {
				width: 80%;
				margin: auto;
				font-size: 13px;
			}
			
			h3 {
				margin: 50px 0;
			}
			
			#ordertable, #infotable {
				width: 100%;
				min-width: 780px;
				font-size: 13px;
				margin-top: 10px;
			}
			
			#infotable tr {
				height: 25;
			}
			
			#infotable td {
				padding: 15px 10px;
			}
			
			.totalprice {
				min-width: 780px;
				text-align: right;
			}
			
			.totalprice>span {
				color: red;
			}
			
			#pay {
				width: 100%;
				min-width: 770px;
				border: 1px solid black;
				margin-top: 10px; 
				line-height: 4em; 
				padding-left: 10px;
			}
			
			#btn_list {
				margin: 10px auto;
				width: 200px;
				height: 50px;
			}
			
			#btn_list input[type="submit"] {
				width: 200px;
				height: 50px;
				border: none;
				background-color: black;
				color: white;
			}
			
			#btn_list input[type="submit"]:hover {
				cursor: pointer;
			}
		</style>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
		</script>
	</head>
	<body>
		<center><h3>구매 페이지</h3></center>
		
		<div id="wrap">
			<b>주문 내역</b>
			<table id="ordertable" border="1" cellspacing="0">
				<tr height="50">
					<td align="center" width="100px">이미지</td>
					<td align="center" width="528px">상품정보</td>
					<td align="center" width="60px">수량</td>
					<td align="center" width="92px">판매가</td>
				</tr>
				<tr height="80"> <%-- 한개만 주문할때 --%>
					<td align="center">
						<img src="<%=request.getContextPath()%>/imgs/<%=proDto.getImage()%>" width="60" height="80">
					</td>
					<td>
						<p class="option">
							<b><%= proDto.getName() %></b><br>
							<font size="2">
								[ 옵션 : <%= color %> / <%= size %> ]
							</font>
						</p>
					</td>
					<td align="center">
						<%= quantity %>
					</td>
					<td align="center">
						<%= quantity * proDto.getPrice() %>원
						<%
							totalPrice += quantity * proDto.getPrice();
						%>
					</td>
				</tr>
			</table>
		<%
			int fee = 0;
		
			if(totalPrice < 50000)
				fee = 2500;
		%>
			<p class="totalprice">
				상품 구매 금액  <%= totalPrice %> + 배송비  <%= fee %> = 총 주문금액  <%= totalPrice+fee %>원
			</p>
			<%-- <p class="totalprice">
				적립 예정 포인트  <span><%= memDto.getPoint() %>원</span>
			</p> --%>
			<br><br>
			
			<b>주문 배송 정보</b>
			<table id="infotable" border="1" cellspacing="0">
				<tr>
					<td width="150px">주문하시는 분</td>
					<td><%= memDto.getName() %></td>
				</tr>
				<tr>
					<td>우편번호</td>
					<td><%= memDto.getZipcode() %></td>
				</tr>
				<tr>
					<td>주소</td>
					<td><%= memDto.getAddr1() %>&nbsp;<%= memDto.getAddr2() %></td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td><%= memDto.getTel() %></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><%= memDto.getEmail() %></td>
				</tr>
			</table><br><br>
			
			<form name="buyForm" method="post" action="BuyProc.jsp">
				<b>결제 예정 금액</b>
				<table id="infotable" border="1" cellspacing="0">
					<tr>
						<td width="150px">총 주문금액</td>
						<td><%= totalPrice %>원</td>
					</tr>
					<%-- <tr>
						<td>적립금</td>
						<td>
							<input type="text" id="inputpoint" name="inputpoint">원 (총 사용 가능 적립금 : <%= memDto.getPoint() %>원)
							<p>
								- 최대 사용 금액은 제한이 없습니다.<br>
								- 1회 구매시 적립금 최대 사용 금액은 <span><%= memDto.getPoint() %>원</span>입니다.
							</p>
						</td>
					</tr> --%>
				</table><br><br>
				
				<b>결제수단</b>
				<div id="pay">
					<input type="radio" id="payment" name="payment" value="1" required="required">무통장 입금
					<input type="radio" id="payment" name="payment" value="2">카드 결제
					<input type="radio" id="payment" name="payment" value="3">휴대폰 결제
					<input type="radio" id="payment" name="payment" value="4">에스크로(실시간 계좌이체)
				</div><br>
				
				<input type="checkbox" id="agree" required="required"> 결제 정보를 확인하였으며, 구매 진행에 동의합니다.<br><br>
				
				<input type="hidden" id="pro_no" name="pro_no" value="<%= pro_no %>">
				<input type="hidden" id="quantity" name="quantity" value="<%= quantity %>">
				<input type="hidden" id="id" name="id" value="<%= id %>">
				<input type="hidden" id="color" name="color" value="<%= color %>">
				<input type="hidden" id="size" name="size" value="<%= size %>">
				
				<div id="btn_list">
					<input type="submit" value="구매하기">
				</div>
			</form>
		</div>
	</body>
</html>



<%-- pro_no : <%= pro_no %>
sel_color : <%= sel_color %>
sel_size : <%= sel_size %>
quantity : <%= quantity %>

ordno : <%= ordno %> --%>
