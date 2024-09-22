<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<body>
		<table>
			<tr>
				<td><b>회원정보가 수정되었습니다</b></td>
			</tr>
			<tr>
				<td>
					<input type="button" value="메인으로" onClick="location='${ctxpath}/user/main.do'">
				</td>
			</tr>
			<tr>
				<td>
					<b>3초 후에 메인으로 이동합니다</b>
					
					<meta http-equiv="Refresh" content="3;url='${ctxpath}/user/main.do'">
				</td>
			</tr>
		</table>
	</body>
</html>