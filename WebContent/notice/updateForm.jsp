<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script>
			function writeSave() {
				if(this.subject.value == '') {
					alert("제목을 입력하세요");
					this.subject.focus();
					
					return false;
				}
	
				if(this.content.value == '') {
					alert("내용을 입력하세요");
					this.content.focus();
					
					return false;
				}
			}
		</script>
	</head>
	<body>
		<center><h3>글 수정</h3></center>
		<form name="writeForm" method="post" action="${ctxpath}/notice/updatePro.do?pageNum=${pageNum}" onSubmit="return writeSave()">
			<table width="500" border="1" cellspacing="0" cellpadding="5" align="center">
				<tr>
					<td align="center" width="100px">제목</td>
					<td>
						<input type="text" name="subject" id="subject" size="40" value="${dto.subject}">
					</td>
				</tr>
				<tr>
					<td align="center">내용</td>
					<td>
						<textarea name="content" id="content" rows="10" cols="40" valign="top">
							${dto.content}
						</textarea>
					</td>
				</tr>
			</table>
			<table width="500" cellpadding="5" align="center">
				<tr height="50">
					<td colspan="2" align="center">
						<input type="hidden" name="num" value="${num}">
						<input type="submit" value="작성">
						<input type="reset" value="초기화">
						<input type="button" value="목록으로" onClick="location='${ctxpath}/notice/list.do?pageNum=${pageNum}'">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>