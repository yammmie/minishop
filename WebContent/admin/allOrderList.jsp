<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
	</head>
	<body>
		<center><h3>모든 주문목록 조회</h3></center>
	
		<table id="ordertable">
			<tr height="50">
				<td align="center" width="120px">
					주문일자<br>
					[ 주문번호 ]
				</td>
				<td align="center" width="100px">이미지</td>
				<td align="center" width="330px">상품정보</td>
				<td align="center" width="50px">수량</td>
				<td align="center" width="90px">id</td>
				<td align="center" width="90px">결제수단</td>
				<td align="center" width="90px">주문처리상태</td>
			</tr>
				
			<c:if test="${fn:length(ordList)==0}">
				<tr height="70">
					<td align="center" colspan="7">
						주문 내역이 없습니다.
					</td>
				</tr>
			</c:if>
			
			<c:if test="${fn:length(ordList)>0}">
				<c:forEach var="ordDto" items="${ordList}"> <%-- 모든 주문조회 --%>
					<c:set var="cntDto" value="${cntvec[cnt]}" />
					<c:set var="pro_no" value="${ordDto.pro_no}" />
					<c:set var="proDto" value="${proMgr.getProduct(pro_no)}" />
					
					<tr height="80">
						<c:choose>
							<c:when test="${cnt2<cntDto.cnt}">
								<c:if test="${cnt2==1}"> <%-- 없으면 3개 이상을 구매했을 때 td를 다시 실행 --%>
									<td align="center" rowspan="${cntDto.cnt}" class="ord">
										<fmt:formatDate value="${ordDto.orddate}" pattern="yyyy-MM-dd" /><br>
										<%-- [ <a href="${ctxpath}/user/orderDetail.do?ordno=${ordDto.ordno}">${ordDto.ordno}</a> ] --%>
										[ ${ordDto.ordno} ]
									</td>
								</c:if>
							</c:when>
							
							<c:when test="${cntDto.cnt==1}">
								<td align="center" class="ord">
									<fmt:formatDate value="${ordDto.orddate}" pattern="yyyy-MM-dd" /><br>
										<%-- [ <a href="${ctxpath}/user/orderDetail.do?ordno=${ordDto.ordno}">${ordDto.ordno}</a> ] --%>
										[ ${ordDto.ordno} ]
								</td>
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
						<td align="center" class="ord">
							${ordDto.quantity}
						</td>
						
						<c:choose>
							<c:when test="${cnt2<cntDto.cnt}">
								<c:if test="${cnt2==1}">
									<td align="center" rowspan="${cntDto.cnt}">
										${ordDto.id}
									</td>
									<td align="center" rowspan="${cntDto.cnt}">
										<c:choose>
											<c:when test="${ordDto.payment=='1'}">
												무통장 입금
											</c:when>
											<c:when test="${ordDto.payment=='2'}">
												카드 결제
											</c:when>
											<c:when test="${ordDto.payment=='3'}">
												휴대폰 결제
											</c:when>
											<c:when test="${ordDto.payment=='4'}">
												에스크로(실시간 계좌이체)
											</c:when>
										</c:choose>
									</td>
									<td align="center" rowspan="${cntDto.cnt}">
										<form name="allForm" method="post" action="${ctxpath}/admin/allOrderListPro.do">
											<input type="hidden" id="ordno" name="ordno" value="${ordDto.ordno}">
										
											<select id="sel_state" name="sel_state" onchange="this.form.submit()">
												<option value="1" <c:if test="${ordDto.state == '1'}">selected</c:if>>입금 전</option>
												<option value="2" <c:if test="${ordDto.state == '2'}">selected</c:if>>배송 준비 중</option>
												<option value="3" <c:if test="${ordDto.state == '3'}">selected</c:if>>배송 중</option>
												<option value="4" <c:if test="${ordDto.state == '4'}">selected</c:if>>배송 완료</option>
											</select>
										</form>
									</td>
								</c:if>
									
								<c:set var="cnt2" value="${cnt2+1}" />
							</c:when>
							
							<c:when test="${cntDto.cnt==1}">
								<td align="center">
									${ordDto.id}
								</td>
								<td align="center">
									<c:choose>
										<c:when test="${ordDto.payment=='1'}">
											무통장 입금
										</c:when>
										<c:when test="${ordDto.payment=='2'}">
											카드 결제
										</c:when>
										<c:when test="${ordDto.payment=='3'}">
											휴대폰 결제
										</c:when>
										<c:when test="${ordDto.payment=='4'}">
											에스크로(실시간 계좌이체)
										</c:when>
									</c:choose>
								</td>
								<td align="center">
									<form name="allForm" method="post" action="${ctxpath}/admin/allOrderListPro.do">
										<input type="hidden" id="ordno" name="ordno" value="${ordDto.ordno}">
									
										<select id="sel_state" name="sel_state" onchange="this.form.submit()">
											<option value="1" <c:if test="${ordDto.state == '1'}">selected</c:if>>입금 전</option>
											<option value="2" <c:if test="${ordDto.state == '2'}">selected</c:if>>배송 준비 중</option>
											<option value="3" <c:if test="${ordDto.state == '3'}">selected</c:if>>배송 중</option>
											<option value="4" <c:if test="${ordDto.state == '4'}">selected</c:if>>배송 완료</option>
										</select>
									</form>
								</td>
							
								<c:set var="cnt" value="${cnt+1}" />
							</c:when>
							
							<c:when test="${cnt2==cntDto.cnt}">
								<c:set var="cnt" value="${cnt+1}" />
								<c:set var="cnt2" value="1" />
							</c:when>
						</c:choose>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</body>
</html>
