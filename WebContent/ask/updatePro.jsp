<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<body>
		<c:if test="${check==1}">
			<meta http-equiv="Refresh" content="0;url=${ctxpath}/ask/list.do?pageNum=${pageNum}">
		</c:if>
	
		<c:if test="${check==0}">
			<script>
				alert("비밀번호가 틀립니다");
				history.back();
			</script>
		</c:if>
	</body>
</html>