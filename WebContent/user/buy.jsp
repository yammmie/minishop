<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<c:if test="${empty cartList}">
	<c:if test="${stock < quantity}">
		<script>
			alert("재고보다 많은 수량을 선택하셨습니다\n수량을 다시 선택해주세요");
			history.back();
		</script>
	</c:if>
</c:if>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
	</head>
	<body>
		<center><h3>구매 페이지</h3></center>
		
		<div id="buy_wrap">
			<b>주문 내역</b>
			
			<table id="ordertable">
				<tr height="50">
					<td align="center" width="100px">이미지</td>
					<td align="center" width="528px">상품정보</td>
					<td align="center" width="60px">수량</td>
					<td align="center" width="92px">판매가</td>
				</tr>
				
				<c:if test="${empty cartList}"> <%-- 한개만 주문할때 --%>
					<tr height="80">
						<td align="center">
							<img src="${ctxpath}/imgs/${proDto.image}" width="60" height="80">
						</td>
						<td>
							<p class="option">
								<b>${proDto.name}</b><br>
								<font size="2">
									[ 옵션 : ${color} / ${size} ]
								</font>
							</p>
						</td>
						<td align="center">
							${quantity}
						</td>
						<td align="center">
							${quantity * proDto.price}원
							<c:set var="totalPrice" value="${totalPrice + (quantity * proDto.price)}" />
						</td>
					</tr>
				</c:if>
				
				<c:if test="${!empty cartList}"> <%-- 여러개 주문할 때 --%>
					<c:forEach var="cartDto" items="${cartList}">
						<tr height="80">
							<td align="center">
								<img src="${ctxpath}/imgs/${cartDto.image}" width="60" height="80">
							</td>
							<td>
								<p class="option">
									<b>${cartDto.name}</b><br>
									<font size="2">
										[ 옵션 : ${cartDto.color} / ${cartDto.size} ]
									</font>
								</p>
							</td>
							<td align="center">
								${cartDto.quantity}
							</td>
							<td align="center">
								${cartDto.quantity * cartDto.price}원
								
								<c:set var="totalPrice" value="${totalPrice + (cartDto.quantity * cartDto.price)}" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			
			<c:set var="fee" value="0" />
			
			<c:if test="${totalPrice < 50000}">
				<c:set var="fee" value="2500" />
			</c:if>

			<p class="totalprice">
				상품 구매 금액  ${totalPrice} + 배송비  ${fee} = 총 주문금액  ${fee+totalPrice}원
			</p>
			<br><br>
			
			<c:set var="totalPrice" value="${totalPrice+fee}" />
			
			<b>주문 배송 정보</b>
			<table id="infotable">
				<tr>
					<td width="150px">주문하시는 분</td>
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
			</table><br><br>
			
			<form name="buyForm" method="post" action="${ctxpath}/user/buyPro.do">
				<input type="hidden" id="id" name="id" value="${id}">
				
				<b>결제 예정 금액</b>
				<table id="infotable">
					<tr>
						<td width="150px">총 주문금액</td>
						<td>${totalPrice}원</td>
					</tr>
				</table><br><br>
				
				<b>결제수단</b>
				<div id="pay">
					<input type="radio" id="payment" name="payment" value="1" required="required">무통장 입금
					<input type="radio" id="payment" name="payment" value="2">카드 결제
					<input type="radio" id="payment" name="payment" value="3">휴대폰 결제
					<input type="radio" id="payment" name="payment" value="4">에스크로(실시간 계좌이체)
				</div><br>
				
				<input type="checkbox" id="agree" required="required"> 결제 정보를 확인하였으며, 구매 진행에 동의합니다.<br><br>
				
				<c:if test="${empty cartList}">
					<input type="hidden" id="before" name="before" value="productDetail">
					<input type="hidden" id="pro_no" name="pro_no" value="${pro_no}">
					<input type="hidden" id="quantity" name="quantity" value="${quantity}">
					<input type="hidden" id="color" name="color" value="${color}">
					<input type="hidden" id="size" name="size" value="${size}">
				</c:if>
				
				<div id="btn_list">
					<input type="submit" value="구매하기">
				</div>
			</form>
		</div>
	</body>
</html>
