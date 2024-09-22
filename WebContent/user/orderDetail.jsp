<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/view/color.jspf" %>
    
<html>
	<body>
		<center><h3>주문 상세 조회</h3></center>
		
		<div id="#orderDetail_wrap">
			<b>주문 정보</b><br>
			<table id="infotable">
				<tr>
					<td width="150px">주문번호</td>
					<td>${ordDto.ordno}</td>
				</tr>
				<tr>
					<td>주문일자</td>
					<td>
						<fmt:formatDate value="${ordDto.orddate}" pattern="yyyy-MM-dd HH:mm:ss" /><br>
					</td>
				</tr>
				<tr>
					<td>주문자</td>
					<td>${memDto.name}</td>
				</tr>
				<tr>
					<td>주문처리상태</td>
					<td>
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
			</table><br><br>
			
			<b>배송지 정보</b><br>
			<table id="infotable">
				<tr>
					<td width="150px">받으시는 분</td>
					<td>${memDto.name}</td>
				</tr>
				<tr>
					<td>우편번호</td>
					<td>${memDto.zipcode}</td>
				</tr>
				<tr>
					<td>주소</td>
					<td>${memDto.addr1}&nbsp;${memDto.addr2}</td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td>${memDto.tel}</td>
				</tr>
				<tr>
					<td>이메일</td>
					<td>${memDto.email1}@${memDto.email2}</td>
				</tr>
			</table><br><br><br>
			
			<b>주문 상품 정보</b><br>
			<table id="detailtable">
				<tr height="50">
					<td align="center" width="100px">이미지</td>
					<td align="center" width="436px">상품정보</td>
					<td align="center" width="60px">수량</td>
					<td align="center" width="92px">판매가</td>
					<td align="center" width="92px">주문처리상태</td>
				</tr>
				
				<c:forEach var="ordDto" items="${vec}">
					<c:set var="pro_no" value="${ordDto.pro_no}" />
					<c:set var="proDto" value="${proMgr.getProduct(pro_no)}" />
					
					<tr height="80">
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
							
							<c:set var="totalPrice" value="${totalPrice+(ordDto.quantity*proDto.price)}" />
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
			</table>
			
			<c:set var="fee" value="0" />
			
			<c:if test="${totalPrice<50000}">
				<c:set var="fee" value="2500" />
			</c:if>
			
			<p class="totalprice">
				상품 구매 금액  ${totalPrice} + 배송비  ${fee} = 총 주문금액  ${totalPrice+fee}원
			</p>
			<br><br>
			
			<b>결제 정보</b><br>
			<table id="infotable">
				<tr>
					<td width="150px">총 주문금액</td>
					<td>${totalPrice}원</td>
				</tr>
				<tr>
					<td>결제수단</td>
					<td>
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
				</tr>
			</table><br><br><br>
			
			<div id="btn_list">
				<input type="button" value="주문 목록 보기" onClick="location.href='${ctxpath}/user/orderList.do'">
			</div>
		</div>
	</body>
</html>


