<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<body>
		<center><h3>상품 수정</h3></center>
	
		<form method="post" action="${ctxpath}/admin/productUpdatePro.do?pro_no=${pro_no}" encType="multipart/form-data">
			<table id="ordertable">
				<tr height="40">
					<td align="center">상품명</td>
					<td>
						<input type="text" name="name" id="name" value="${proDto.name}" size="20">
						<input type="hidden" name="pro_no" value="${proDto.pro_no}">
					</td>
				</tr>
				<tr height="40">
					<td align="center">색상</td>
					<td>
						<input type="text" name="color" id="color" value="${proDto.color}" size="20" readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="center">사이즈</td>
					<td>
						<input type="text" name="size" id="size" value="${proDto.size}" readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="center">가격</td>
					<td>
						<input type="text" name="price" id="price" value="${proDto.price}" size="20">원
					</td>
				</tr>
				<tr height="40">
					<td align="center">설명</td>
					<td>
						<textarea name="detail" id="detail" rows="10" cols="45">${proDto.detail}</textarea>
					</td>
				</tr>
				<tr height="40">
					<td align="center">재고량</td>
					<td>
						<input type="text" name="stock" id="stock" value="${proDto.stock}" size="10">개
					</td>
				</tr>
				<tr height="40">
					<td align="center">이미지</td>
					<td>
						<img src="../imgs/${proDto.image}" width="150" height="150"><br>
						<input type="file" name="image">
					</td>
				</tr>
				<tr height="50">
					<td colspan="2" align="center">
						<input type="submit" value="수정">
						<input type="button" value="취소" onClick="location='${ctxpath}/admin/productList.do'">
				</tr>
			</table>
		</form>
	</body>
</html>