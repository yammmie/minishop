<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script type="text/javascript" src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
			function check1() {
				alert("상품을 선택하세요");
			}
			 
			function cartUpdate(form) {
				form.flag.value="update";
				form.submit();
			}
			 
			function cartDelete(form) {
				var msg = confirm("삭제하시겠습니까?");
				
				if (msg == true) {
					form.flag.value="delete";
					form.submit();
				}
				
				else
					return false;
			}
		</script>
	</head>
	<body>
		<div id="cart_wrap">
			<center><h3>장바구니</h3></center>
		
			<c:set var="totalPrice" value="0" />
				
			<c:if test="${empty cartList}">
				<table width="780" align="center">
					<tr height="50">
						<td align="center">
							장바구니가 비어 있습니다
						</td>
					</tr>
				</table>
			</c:if>
			
			<c:if test="${!empty cartList}">
				<table id="carttable">
					<tr height="40">
						<td align="center" width="100px">이미지</td>
						<td align="center" width="346px">상품정보</td>
						<td align="center" width="92px">판매가</td>
						<td align="center" width="50px">수량</td>
						<td align="center" width="92px">합계</td>
						<td align="center" width="100px"></td>
					</tr>
					
					<c:forEach var="dto" items="${cartList}">
						<c:set var="totalPrice" value="${totalPrice+(dto.price*dto.quantity)}" />
						
						<form method="post" name="cartForm" action="${ctxpath}/user/cartPro.do">
							<input type="hidden" name="num" value="${dto.num}">
							<input type="hidden" id="flag" name="flag">
							<input type="hidden" name="pro_no" value="${dto.pro_no}">
							<input type="hidden" name="color" value="${dto.color}">
							<input type="hidden" name="size" value="${dto.size}">
							
							<tr>
								<td align="center">
									<a href="${ctxpath}/user/productDetail.do?pro_no=${dto.pro_no}">
										<img src="${ctxpath}/imgs/${dto.image}" width="100" height="100">
									</a>
								</td>
								<td>
									<p class="option">
										<b>${dto.name}</b><br>
										<font size="2">
											[ 옵션 : ${dto.color} / ${dto.size} ]
										</font>
									</p>
								</td>
								<td align="center">
									${dto.price}원
								</td>
								<td align="center">
									<input type="number" name="quantity" id="quantity" min="1" value="${dto.quantity}" size="10">
								</td>
								<td align="center">
									${dto.price*dto.quantity}원
								</td>
								<td align="center">
									<input type="button" value="수정" onclick="cartUpdate(this.form)">
									<input type="button" value="삭제" onclick="cartDelete(this.form)">
								</td>
							</tr>
						</form>
					</c:forEach>
					
					<c:set var="fee" value="0" />
					
					<c:if test="${totalPrice < 50000}">
						<c:set var="fee" value="2500" />
					</c:if>
				</table>
				<p class="totalprice">
					상품 구매 금액  ${totalPrice} + 배송비  ${fee} = 총 주문금액  ${fee+totalPrice}원
				</p><br><br>
				
				<div id="btn_list">
					<form method="post" action="${ctxpath}/user/buy.do">
						<input type="hidden" name="id" value="${id}">
						<input type="submit" value="모두 구매하기">
					</form>
				</div>
			</c:if>
		</div>
	</body>
</html>