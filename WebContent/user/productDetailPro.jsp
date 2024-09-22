<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<option value="">[필수] 사이즈</option>
<option>-------------------------------</option>

<c:set var="num" value="0" />

<c:forEach var="i" items="${size}">
	<c:if test="${stock[num]=='0'}">
		<option value="${i}" disabled="disabled">${i} [ 품절 ]</option>
	</c:if>
	
	<c:if test="${stock[num]!='0'}">
		<option value="${i}">${i}</option>
	</c:if>
	
	<c:set var="num" value="${num+1}" />
</c:forEach>