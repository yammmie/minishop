<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script>
			function delCheck() {
				var x = confirm("선택하신 상품을 삭제하시곘습니까?");
				
				if(x == false)
					return false;
			}
		</script>
	</head>
	<body>
		<table width="100%">
			<tr height="50">
				<td colspan="7" align="right">
					<div id="btn_list">
						<input type="button" value="상품 등록" onClick="location.href='${ctxpath}/admin/productInsertForm.do'">
					</div>
				</td>
			</tr>
		</table>
		<table id="ordertable">
			<tr height="40">
				<td align="center">상품명</td>
				<td align="center">색상</td>
				<td align="center">사이즈</td>
				<td align="center">가격</td>
				<td align="center">입고일</td>
				<td align="center">재고량</td>
				<td align="center"></td>
			</tr>
			
			<c:if test="${empty goodList}">
				<tr height="50">
					<td colspan="7" align="center">
						상품이 없습니다
					</td>
				</tr>
			</c:if>
			
			<c:if test="${!empty goodList}">
				<c:forEach var="dto" items="${goodList}">
					<tr height="50">
						<td align="center">${dto.name}</td>
						<td align="center">${dto.color}</td>
						<td align="center">${dto.size}</td>
						<td align="center">${dto.price}</td>
						<td align="center">${dto.regdate}</td>
						<td align="center">
							<c:set var="stock" value="${dto.stock}" />
							
							<c:if test="${stock>0}">
								${stock}
							</c:if>
							<c:if test="${stock==0}">
								품절
							</c:if>
						</td>
						<td align="center">
							<form name="update" method="post" action="${ctxpath}/admin/productUpdateForm.do">
								<input type="hidden" name="pro_no" value="${dto.pro_no}">
								<input type="hidden" name="color" value="${dto.color}">
								<input type="hidden" name="size" value="${dto.size}">
								
								<input type="submit" value="수정">
							</form>
							
							<form name="delete" method="post" onSubmit="return delCheck()" action="${ctxpath}/admin/productDeletePro.do">
								<input type="hidden" name="pro_no" value="${dto.pro_no}">
								<input type="hidden" name="color" value="${dto.color}">
								<input type="hidden" name="size" value="${dto.size}">
								
								<input type="submit" value="삭제">
							</form>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</body>
</html>