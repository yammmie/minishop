<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<body>
		<center><h3>문의 내용</h3></center>
		
		<table width="500" border="1" cellspacing="0" cellpadding="3" align="center">
			<tr height="30">
				<td align="center" width="125">작성자</td>
				<td>${dto.name}</td>
			</tr>
			<tr height="30">
				<td align="center">작성일</td>
				<td>${dto.regdate}</td>
			</tr>
			<tr height="30">
				<td align="center">제목</td>
				<td>${dto.subject}</td>
			</tr>
			<tr height="200">
				<td align="center">내용</td>
				<td>${content}</td>
			</tr>		
		</table>
		<table width="500" cellpadding="3" align="center">
			<tr height="50">
				<td colspan="4" align="center">
					<input type="button" value="수정" onClick="location.href='${ctxpath}/ask/updateForm.do?num=${num }&pageNum=${pageNum}'">
					<input type="button" value="삭제" onClick="location.href='${ctxpath}/ask/deleteForm.do?num=${num }&pageNum=${pageNum}'">
					
					<c:if test="${sessionScope.id=='admin'}">
						<input type="button" value="답변 작성" onClick="location.href='${ctxpath}/ask/writeForm.do?num=${num}&pageNum=${pageNum}&ref=${ref}&re_step=${re_step}&re_level=${re_level}'">					
					</c:if>

					<input type="button" value="목록" onClick="location.href='${ctxpath}/ask/list.do?pageNum=${pageNum}'">	
				</td>
			</tr>
		</table>
	</body>
</html>
