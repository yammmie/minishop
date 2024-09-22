<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
    
<jsp:useBean id="orderMgr" class="user.OrderMgr" />

<%
	String id = "mm";
	ProductMgr proMgr = ProductMgr.getDao();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>

<%
	request.setCharacterEncoding("utf-8");
	
	// 넘어온 id
	String state = request.getParameter("state");
	String start_date = request.getParameter("start_date");
	String end_date = request.getParameter("end_date");
	
	if(state==null || start_date==null || end_date==null) {
		state = "0";
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -3);
		
		start_date = sdf.format(cal.getTime());
		end_date = sdf.format(new Date());
	}
%>

<html>
	<head>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="//code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
		<script>
			$(function() {		    	
		        $.datepicker.setDefaults({
		        	dateFormat: 'yy-mm-dd',
		        	showOn: "both",
		        	buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif", //버튼 이미지 경로
		            buttonImageOnly: true,
		            showAnim: "slideDown"
		        });
		        
		        $('#start_date').datepicker({
		        	altField: '#start_date'
		        }).datepicker('setDate', '-3M');
	
		        $('#end_date').datepicker({
		        	altField: '#end_date'
		        }).datepicker('setDate', 'today');
	
		        // 현재 url 가져오기
				var url = location.href;
								
				if(url.indexOf('?') != -1) { // url에 파라미터 값이 넘어왔으면
					var params = (url.slice(url.indexOf('?')+1, url.length)).split('&'); // ? 없으면 -1
							
					var state = params[0].split('=')[1];
					var start_date = params[1].split('=')[1];
					var end_date = params[2].split('=')[1];
					
					$('#state option[value='+state+']').prop('selected', true);
					$('#start_date').val(start_date);
					$('#end_date').val(end_date);
				}
				
				$('#today').on('click', function() {
					$('#start_date').datepicker('setDate', 'today');
				});
	
				$('#week').on('click', function() {
					$('#start_date').datepicker('setDate', '-7D');
				});
	
				$('#one_month').on('click', function() {
					$('#start_date').datepicker('setDate', '-1M');
				});
	
				$('#six_month').on('click', function() {
					$('#start_date').datepicker('setDate', '-6M');
				});
	
				$('#year').on('click', function() {
					$('#start_date').datepicker('setDate', '-1Y');
				});
			});
		</script>
		<style>
			.ui-datepicker { font-size: 12px; width: 160px; }
			.ui-datepicker select.ui-datepicker-month { width:30%; font-size: 11px; }
			.ui-datepicker select.ui-datepicker-year { width:40%; font-size: 11px; }
		
			/*datepicer 버튼 롤오버 시 손가락 모양 표시*/
			.ui-datepicker-trigger { 
				cursor: pointer;
				margin: 0 5px 0 2px;
				border: none;
			}
			
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
			
			fieldset {
				min-width: 750px;
			}
			
			#start_date, #end_date {
				width: 90px;
			}
			
			ul {
				padding-left: 20px;
				margin-bottom: 40px;
				color: #6f7072;
			}
			
			#ordertable {
				width: 100%;
				min-width: 780px;
				font-size: 13px;
				margin-top: 10px;
			}
			
			.option {
				margin-left: 10px;
			}
			
			.option font {
				color: gray;
			}
			
			a {
				color: black;
			}
			
			#state {
				height: 25px;
			}
		</style>
	</head>
	<body>
		<center><h3>주문 조회</h3></center>
		
		<div id="wrap">
			<form method="get" name="choiceForm" action="OrderList.jsp">
				<fieldset>
					<select name="state" id="state">
						<option value="0">전체 주문 처리 상태</option>
						<option value="1">입금 전</option>
						<option value="2">배송 준비 중</option>
						<option value="3">배송 중</option>
						<option value="4">배송 완료</option>
					</select>
					&nbsp;|&nbsp;
					<input type="button" name="today" id="today" value="오늘">
					<input type="button" name="week" id="week" value="일주일">
					<input type="button" name="one_month" id="one_month" value="1개월">
					<input type="button" name="six_month" id="six_month" value="6개월">
					<input type="button" name="year" id="year" value="1년">
					&nbsp;
					
					<input type="text" name="start_date" id="start_date" readonly="readonly" value=""> - <input type="text" name="end_date" id="end_date" readonly="readonly" value="">
					&nbsp;
					
					<input type="hidden" id="id" value="<%= id %>">					
					<input type="submit" value="조회">
				</fieldset><br>
			</form>
			
			<ul>
				<li>기본적으로 최근 3개월 간의 자료가 조회되며, 기간 검색시 지난 주문 내역을 조회하실 수 있습니다.</li>
				<li>주문 번호를 클릭하시면 해당 주문에 대한 상세내역을 확인하실 수 있습니다.</li>
			</ul>
			
			<b>주문 상품 정보</b>
			<table id="ordertable" border="1" cellspacing="0">
				<tr height="50">
					<td align="center" width="120px">
						주문일자<br>
						[ 주문번호 ]
					</td>
					<td align="center" width="100px">이미지</td>
					<td align="center" width="330px">상품정보</td>
					<td align="center" width="50px">수량</td>
					<td align="center" width="90px">판매가</td>
					<td align="center" width="90px">주문처리상태</td>
				</tr>
			<%
				int cnt = 0;
				int cnt2 = 1;
			
				//Vector<OrderDTO> vec = orderMgr.getUserOrder(id);
				
				Vector<OrderDTO> vec = orderMgr.getUserOrder(id, state, start_date, end_date);
				
				Vector<OrderDTO> cntvec = orderMgr.getOrdnoCount(id); // ordno, orddate, count(*)
				OrderDTO cntDto = (OrderDTO)cntvec.get(cnt);
			
				if(vec.size() == 0) { // 주문 내역이 없을 때
				%>
					<tr height="70">
						<td align="center" colspan="6">
							주문 내역이 없습니다.
						</td>
					</tr>
				<%
				} else { // 주문 내역이 있을 때
					for(int i=0; i<vec.size(); i++) {
						OrderDTO ordDto = (OrderDTO)vec.get(i);
						cntDto = (OrderDTO)cntvec.get(cnt);
						
						int pro_no = ordDto.getPro_no();
						ProductDTO proDto = proMgr.getProduct(pro_no);
					%>
						<tr height="80">
						<%
							if(cnt2 < cntDto.getCnt()) { // 한번에 여러 상품 주문한 경우
								if(cnt2 == 1) { // 없으면 3개 이상을 구매했을 때 td를 다시 실행 (깨짐)
								%>
									<td align="center" rowspan="<%=cntDto.getCnt()%>">
										<%= sdf.format(ordDto.getOrddate()) %><br>
										[ <a href="OrderDetail.jsp?ordno=<%=ordDto.getOrdno()%>"><%= ordDto.getOrdno() %></a> ]
									</td>
								<%
								}
								
								cnt2 ++;
							} else if(cntDto.getCnt() == 1) { // 1개의 상품만 주문한 경우
							%>
								<td align="center">
									<%= sdf.format(ordDto.getOrddate()) %><br>
									[ <a href="OrderDetail.jsp?ordno=<%=ordDto.getOrdno()%>"><%= ordDto.getOrdno() %></a> ]
								</td>
							<%
								cnt ++;
							} else if(cnt2 == cntDto.getCnt()) {
								cnt ++;
								cnt2 = 1;
							}
						%>
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
					} // for
				} // else
			%>
			</table>
		</div>
	</body>
</html>