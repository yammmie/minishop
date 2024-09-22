<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<div id="topInner">
	<c:if test="${empty sessionScope.id}">
		<a href="${ctxpath}/admin/login.do">로그인</a>
		<a href="${ctxpath}/admin/inputForm.do">회원가입</a>
		<a href="${ctxpath}/notice/list.do">공지사항</a>
	</c:if>
	<c:if test="${!empty sessionScope.id}">
		<c:if test="${sessionScope.id=='admin'}">
			<a href="${ctxpath}/admin/logout.do">로그아웃</a>
			<a href="${ctxpath}/admin/allOrderList.do">모든주문목록</a>
			<a href="${ctxpath}/admin/productList.do">모든상품목록</a>
			<a href="${ctxpath}/admin/cateList.do">카테고리수정</a>
			<a href="${ctxpath}/notice/list.do">공지사항</a>
			<a href="${ctxpath}/ask/list.do">문의하기</a>
		</c:if>
		
		<c:if test="${sessionScope.id!='admin'}">
			<a href="${ctxpath}/admin/logout.do">로그아웃</a>
			<a href="${ctxpath}/admin/modify.do">회원정보수정</a>
			<a href="${ctxpath}/user/cartList.do">장바구니</a>
			<a href="${ctxpath}/user/orderList.do">주문조회</a>
			<a href="${ctxpath}/notice/list.do">공지사항</a>
			<a href="${ctxpath}/ask/list.do">문의하기</a>
		</c:if>
	</c:if>
</div>
<div id="logo">
	<a href="${ctxpath}/user/main.do">
		<img src="${ctxpath}/imgs/logo.png">
	</a>
</div>