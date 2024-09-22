<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ include file="/view/color.jspf" %>

<fmt:requestEncoding value="UTF-8" />

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
	</head>
	<body>
		<center><h3>주문 조회</h3></center>
		
		<div id="order_wrap">
			<form method="get" name="choiceForm" action="${ctxpath}/user/orderList.do">
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
					
					<input type="hidden" id="id" value="${id}">			
					<input type="submit" value="조회">
				</fieldset><br>
			</form>
			
			<ul>
				<li>기본적으로 최근 3개월 간의 자료가 조회되며, 기간 검색시 지난 주문 내역을 조회하실 수 있습니다.</li>
				<li>주문 번호를 클릭하시면 해당 주문에 대한 상세내역을 확인하실 수 있습니다.</li>
			</ul>
			
			<b>주문 상품 정보</b>
			<table id="ordertable">
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
				
				<c:if test="${fn:length(vec)==0}"> <%-- 주문 내역이 없을 때 --%>
					<tr height="70">
						<td align="center" colspan="6">
							주문 내역이 없습니다.
						</td>
					</tr>
				</c:if>
				
				<c:if test="${fn:length(vec)>0}"> <%-- 주문 내역이 있을 때 --%>
					<c:forEach var="ordDto" items="${vec}">
						<c:set var="cntDto" value="${cntvec[cnt]}" />
						<c:set var="pro_no" value="${ordDto.pro_no}" />
						<c:set var="proDto" value="${proMgr.getProduct(pro_no)}" />
						
						<tr height="80">
							<c:choose>
								<c:when test="${cnt2<cntDto.cnt}">
									<c:if test="${cnt2==1}"> <%-- 없으면 3개 이상을 구매했을 때 td를 다시 실행 --%>
										<td align="center" rowspan="${cntDto.cnt}" class="ord">
											<fmt:formatDate value="${ordDto.orddate}" pattern="yyyy-MM-dd" /><br>
											[ <a href="${ctxpath}/user/orderDetail.do?ordno=${ordDto.ordno}">${ordDto.ordno}</a> ]
										</td>
									</c:if>
										
									<c:set var="cnt2" value="${cnt2+1}" />
								</c:when>
								
								<c:when test="${cntDto.cnt==1}">
									<td align="center" class="ord">
										<fmt:formatDate value="${ordDto.orddate}" pattern="yyyy-MM-dd" /><br>
											[ <a href="${ctxpath}/user/orderDetail.do?ordno=${ordDto.ordno}">${ordDto.ordno}</a> ]
									</td>
									
									<c:set var="cnt" value="${cnt+1}" />
								</c:when>
								
								<c:when test="${cnt2==cntDto.cnt}">
									<c:set var="cnt" value="${cnt+1}" />
									<c:set var="cnt2" value="1" />
								</c:when>
							</c:choose>
							
							<td align="center">
								<img src="${ctxpath}/imgs/${proDto.image}" width="60" height="80">
							</td>
							<td>
								<p class="option">
									<b>${proDto.name}</b><br>
									<font size="2">
										[ 옵션 : ${ordDto.color} / ${ordDto.size} ]
									</font>
								</p>
							</td>
							<td align="center">
								${ordDto.quantity}
							</td>
							<td align="center">
								${ordDto.quantity * proDto.price}원
							</td>
							<td align="center">
								<c:choose>
									<c:when test="${ordDto.state=='1'}">
										입금 전
									</c:when>
									<c:when test="${ordDto.state=='2'}">
										배송 준비 중
									</c:when>
									<c:when test="${ordDto.state=='3'}">
										배송 중
									</c:when>
									<c:when test="${ordDto.state=='4'}">
										배송 완료
									</c:when>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
	</body>
</html>



