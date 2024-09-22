<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
			function writeSave(){		   
				if($("#passwd").val()==''){
					alert("비밀번호를 입력하세요 ");
					$("#passwd").focus();
					
					return false;
				}
					 
				if(document.writeForm.content.value==''){
					 alert("내용을 입력하세요 ");
					 document.writeForm.content.focus();
					 
					 return false;
				}
			
				document.updateForm.submit();
			}
		</script>
	</head>
	<body>
		<center><h3>문의 수정하기</h3></center>
		
		<form name="updateForm" method="post" action="${ctxpath}/ask/updatePro.do?pageNum=${pageNum}" onSubmit="return writeSave()">
			<table width="500" border="1" cellspacing="0" cellpadding="5" align="center">
				<tr>
					<td align="center" width="100">이름</td>
					<td>
						<input type="text" name="name" size="12" value="${dto.name}">
						<input type="hidden" name="num" value="${dto.num}">
					</td>
				</tr>
				<tr>
					<td align="center">제목</td>
					<td>
						<input type="text" name="subject" id="subject" value="${dto.subject}">
					</td>
				</tr>
				<tr>
					<td align="center">내용</td>
					<td>
						<textarea name="content" id="content" rows="10" cols="50">${dto.content}</textarea>
					</td>
				</tr>
				
				<tr>
					<td align="center">이름</td>
					<td>
						<input type="text" name="name" id="name" size="20" value="${dto.name}">
					</td>
				</tr>
				
				<tr>
					<td align="center">비밀번호</td>
					<td>
						<input type="password" name="passwd" id="passwd" size="20">
					</td>
				</tr>
			</table>
			<table width="500" cellpadding="5" align="center">
				<tr height="50">
					<td colspan="2" align="center">
						<input type="submit" value="수정">
						<input type="reset" value="초기화">
						<input type="button" value="취소" onClick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>