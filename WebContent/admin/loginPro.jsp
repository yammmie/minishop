<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<c:if test="${check==1}">
	<c:set var="id" value="${id}" scope="session"/>
	
	<meta http-equiv="Refresh" content="0;url=${ctxpath}/user/main.do">
</c:if>

<c:if test="${check==0}">
	<script>
		alert("비밀번호가 틀립니다");
		history.go(-1);
	</script>
</c:if>

<c:if test="${check==-1}">
	<script>
		alert("해당 id가 없습니다");
		history.go(-1);
	</script>
</c:if>