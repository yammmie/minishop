<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
			function deleteCheck() {
				var result = confirm("삭제하시겠습니까?");
				
				if(result) {
					document.delForm.submit();
				} else {
					return false;
				}
			}
		</script>
	</head>
	<body>
		<center><h3>공지</h3></center>
		
		<table width="500" border="1" cellspacing="0" cellpadding="3" align="center">
			<tr height="30">
				<td align="center" width="125">제목</td>
				<td colspan="3" width="375">${dto.subject}</td>
			</tr>
			<tr height="30">
				<td align="center">작성자</td>
				<td><b>관리자</b></td>
				<td align="center">작성일</td>
				<td>${dto.regdate}</td>
			</tr>
			<tr height="30">
				<td align="center" width="125">내용</td>
				<td colspan="3" width="375" height="200">${content}</td>
			</tr>
		</table>
		<table width="500" cellpadding="3" align="center">
			<tr height="50">
				<td colspan="4" align="center">
					<%-- id가 관리자일 경우 수정, 삭제 띄우기 --%>
					<form name="delForm" method="post" action="${ctxpath}/notice/deletePro.do?num=${dto.num}&pageNum=${pageNum}" onSubmit="return deleteCheck()">
						<input type="button" value="수정" onClick="location='${ctxpath}/notice/updateForm.do?num=${dto.num}&pageNum=${pageNum}'">
						<input type="submit" value="삭제">
						<input type="button" value="목록으로" onClick="location='${ctxpath}/notice/list.do?pageNum=${pageNum}'">
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
    
    