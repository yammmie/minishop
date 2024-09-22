<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script>
			function check(){
				if(document.inform.id.value==''){
					alert("아이디를 입력하세요");
					document.inform.id.focus();
					return false;
				}
				
				if(document.inform.passwd.value==''){
					alert("비밀번호를 입력하세요");
					document.inform.passwd.focus();
					return false;
				}
				
				document.inform.submit();//서버로 전송
			}
		</script>
	</head>
	<body>
		<c:if test="${empty sessionScope.id}">
			<center><h3>로그인</h3></center>
			
			<form name="inform" method="post" action="${ctxpath}/admin/loginPro.do" onSubmit="return check()">
				<table id="logintable">
					<tr>
						<td align="center">
							<input type="text" class="iptx" name="id" size="30" maxlength="20" placeholder="아이디">
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="password" class="ippw" name="passwd" size="30" maxlength="20" placeholder="비밀번호">
						</td>
					</tr>
					<tr height="50">
						<td align="center">
							<input type="submit" class="ipbt" value="로그인">
							<input type="button" class="ipbt" value="회원가입" onClick="document.location.href='${ctxpath}/admin/inputForm.do'">
						</td>
					</tr>
				</table>
			</form>
		</c:if>
	</body>
</html>