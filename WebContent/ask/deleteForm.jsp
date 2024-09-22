<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
			function deleteCheck(){
				if($("#passwd").val()==''){
					alert("비밀번호를 입력하세요");
					$("#passwd").focus();
					
					return false;
				}//if end
			}
		</script>
	</head>
	<body>
		<center><h3>문의 삭제</h3></center>
		
		<form method="post" action="${ctxpath}/ask/deletePro.do?pageNum=${pageNum}" onSubmit="return deleteCheck()">
			<table width="300" align="center" cellpadding="3">
				<tr height="30">
					<td align="center">
						<h3>비밀번호 입력</h3>
					</td>
				</tr>
				<tr height="30">
					<td align="center">비밀번호 :
						<input type="password" name="passwd" id="passwd" size="12">
						<input type="hidden" name="num" value="${num}">
					</td>
				</tr>
				<tr height="50">
					<td align="center">
						<input type="submit" value="삭제">
						<input type="button" value="목록" onClick="location.href='${ctxpath}/ask/list.do?pageNum=${pageNum}'">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>