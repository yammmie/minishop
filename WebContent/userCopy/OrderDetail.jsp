<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="shopdb.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
    
<jsp:useBean id="orderMgr" class="shopdb.OrderMgr" />

<%
	request.setCharacterEncoding("utf-8");

	int ordno = Integer.parseInt(request.getParameter("ordno"));
	ProductMgr proMgr = ProductMgr.getDao();
	MemberDAO memDao = MemberDAO.getInstance();

	Vector<OrderDTO> vec = orderMgr.getOrderDetail(ordno);
	OrderDTO ordDto = (OrderDTO)vec.get(0);
	MemberDTO memDto = memDao.getMember(ordDto.getId());
	
	int totalPrice = 0;
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
				font-size: 14px;
			}
			
			h3 {
				margin: 50px 0;
			}
			
			#infotable, #detailtable {
				width: 100%;
				min-width: 780px;
				font-size: 13px;
				margin-top: 10px;
			}
			
			#infotable tr {
				height: 40;
			}
			
			#infotable td {
				padding: 15px 10px;
			}
			
			.option {
				margin-left: 10px;
			}
			
			.option font {
				color: gray;
			}
			
			.totalprice {
				min-width: 780px;
				text-align: right;
			}
			
			#btn_list {
				margin: auto;
				width: 200px;
				height: 50px;
			}
			
			#btn_list input[type="button"] {
				width: 200px;
				height: 50px;
				border: none;
				background-color: black;
				color: white;
			}
			
			#btn_list input[type="button"]:hover {
				cursor: pointer;
			}
		</style>
	</head>
	<body>
		<center><h3>주문 상세 조회</h3></center>
		
		<div id="wrap">
			<b>주문 정보</b><br>
			<table id="infotable" border="1" cellspacing="0">
				<tr>
					<td width="150px">주문번호</td>
					<td><%= ordDto.getOrdno() %></td>
				</tr>
				<tr>
					<td>주문일자</td>
					<td><%= ordDto.getOrddate() %></td>
				</tr>
				<tr>
					<td>주문자</td>
					<td><%= memDto.getName() %></td>
				</tr>
				<tr>
					<td>주문처리상태</td>
					<td><%= ordDto.getState() %></td>
				</tr>
			</table><br><br>
			
			<b>배송지 정보</b><br>
			<table id="infotable" border="1" cellspacing="0">
				<tr>
					<td width="150px">받으시는 분</td>
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
			</table><br><br><br>
			
			<b>주문 상품 정보</b><br>
			<table id="detailtable" border="1" cellspacing="0">
				<tr height="50">
					<td align="center" width="100px">이미지</td>
					<td align="center" width="436px">상품정보</td>
					<td align="center" width="60px">수량</td>
					<td align="center" width="92px">판매가</td>
					<td align="center" width="92px">주문처리상태</td>
				</tr>
			<%
				for(int i=0; i<vec.size(); i++) {
					ordDto = (OrderDTO)vec.get(i);
					
					int pro_no = ordDto.getPro_no();
					ProductDTO proDto = proMgr.getProduct(pro_no);
				%>
					<tr height="80">
						<td align="center">
							<img src="<%=request.getContextPath()%>/imgs/<%=proDto.getImage()%>" width="60" height="80">
						</td>
						<td>
							<p class="option">
								<b><%= proDto.getName() %></b><br>
								<font size="2">
									[ 옵션 : <%= ordDto.getColor() %> / <%= ordDto.getSize() %> ]
								</font>
							</p>
						</td>
						<td align="center">
							<%= ordDto.getQuantity() %>
						</td>
						<td align="center">
							<%= ordDto.getQuantity() * proDto.getPrice() %>원
							<%
								totalPrice += ordDto.getQuantity() * proDto.getPrice();
							%>
						</td>
						<td align="center">
						<%
							switch(ordDto.getState()) {
								case "1": out.println("입금 전"); break;
								case "2": out.println("배송 준비 중"); break;
								case "3": out.println("배송 중"); break;
								case "4": out.println("배송 완료"); break;
							}
						%>
						</td>
					</tr>
				<%
				}
			%>
			</table>
		<%
			int fee = 0;
		
			if(totalPrice < 50000)
				fee = 2500;
		%>
			<p class="totalprice">
				상품 구매 금액  <%= totalPrice %> + 배송비  <%= fee %> = 총 주문금액  <%= totalPrice+fee %>원
			</p>
			<br><br>
			
			<b>결제 정보</b><br>
			<table id="infotable" border="1" cellspacing="0">
				<tr>
					<td width="150px">총 주문금액</td>
					<td><%= totalPrice %>원</td>
				</tr>
				<tr>
					<td>결제수단</td>
					<td>
					<%
						switch(ordDto.getPayment()) {
							case "1": out.println("무통장 입금"); break;
							case "2": out.println("카드 결제"); break;
							case "3": out.println("휴대폰 결제"); break;
							case "4": out.println("에스크로(실시간 계좌이체)"); break;
						}
					%>
					</td>
				</tr>
			</table><br><br><br>
			
			<div id="btn_list">
				<input type="button" value="주문 목록 보기" onClick="location.href='OrderList.jsp'">
			</div>
		</div>
	</body>
</html>


