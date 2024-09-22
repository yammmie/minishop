<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf"%>

<html>
	<head>
		<script type="text/javascript" src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
			function umck(){
				if($("#passwd").val()==""){
					alert("비밀번호를 입력하시요");
					$("#passwd").focus();
					
					return false;
				}
			}
		</script>
	</head>
	<body>
		<center><h3>비밀번호 확인</h3></center>
		
		<form method="post" action="${ctxpath}/admin/confirmPwPro.do" onSubmit="return umck()">
			<p>
				<b>${sessionScope.id}</b>님의 정보를 안전하게 보호하기 위해 비밃먼호를 다시 한번 확인합니다.
			</p>
			
			<table>
				<tr>
					<td>아이디</td>
					<td>
						<input type="hidden" name="id" value="${sessionScope.id}">${sessionScope.id}
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td>
						<input type="password" id="passwd" name="passwd" size="20">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="확인">
						<input type="button" value="취소" onClick="location.href='${ctxpath}/admin/login.do'">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>