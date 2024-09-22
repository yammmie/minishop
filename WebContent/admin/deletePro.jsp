<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf"%>

<c:if test="${check==1}">
	<c:remove var="id" scope="session"/>
	
	<center>
		<h3>회원탈퇴가 완료되었습니다</h3>
		<input type="button" value="확인" onClick="location='${ctxpath}/user/main.do'">
	</center>
</c:if>

<c:if test="${check==0}">
	<script>
		alert("비밀번호가 틀립니다.");
		history.back();
	</script>
</c:if>

<c:if test="${check==-1}">
	<script>
		alert("존재하지않는 아이디입니다");
		history.back();
	</script>
</c:if>
