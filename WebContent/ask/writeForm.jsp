<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
			function writeSave() {
				if($("#subject").val()=='') {
					alert("제목을 입력하세요 ");
					$("#subject").focus();
					
					return false;
				}
					
				if($("#name").val()=='') {
					alert("이름을 입력하세요 ");
					$("#name").focus();
					
					return false;
				}
					 
				if(document.writeForm.content.value==''){
					 alert("내용을 입력하세요 ");
					 document.writeForm.content.focus();
					 
					 return false;
				}
				 
			   	if($("#passwd").val()=='') {
					alert("비민번호를 입력하세요 ");
					$("#passwd").focus();
				
					return false;
				}  
			   	
				document.writeForm.submit();
			}
		</script>
	</head>
	<body>
		<form name="writeForm" method="post" action="${ctxpath}/ask/writePro.do" onSubmit="return writeSave()">
		    <input type="hidden" name="pageNum" value="${pageNum}">
			<input type="hidden" name="num" value="${num}">
			<input type="hidden" name="ref" value="${ref}">
			<input type="hidden" name="re_step" value="${re_step}">
			<input type="hidden" name="re_level" value="${re_level}">
			
			<center><h3>1대1 문의</h3></center>
			
			<table border="1" align="center" cellspacing="0" cellpadding="5">
				<tr>
					<td align="center" width="100">제목</td>
					<td>
						<c:if test="${num==0}">
							<input type="text" name="subject" id="subject" size="30">
						</c:if>
						
						<c:if test="${num!=0}">
							<input type="text" name="subject" id="subject" size="30" value="[답변]">
						</c:if>
					</td>
				</tr>
			 	<tr>
			 		<td align="center">작성자</td>
			 		<td>
						<c:if test="${num==0}">
							<input type="text" name="name" id="name" size="20">
						</c:if>
						
						<c:if test="${num!=0}">
							<input type="text" name="name" id="name" size="20" value="관리자">
						</c:if>
			 		</td>
			 	</tr>
				<tr>
				 	<td align="center">문의내용</td>
				 	<td>
				 		<textarea name="content" id="content" rows="10" cols="50"></textarea>
				 	</td>
			 	</tr>
			 	<tr>
					<td align="center">비밀번호</td>
					<td>
						<input type="password" name="passwd" id="passwd" size="20">
					</td>
				</tr>
				<tr height="50">
					<td colspan="2" align="center">
						<input type="submit" value="등록">
						<input type="button" value="취소" onClick="location='${ctxpath}/ask/list.do?pageNum=${pageNum}'">
					</td>
				</tr>	
			</table>
		</form>
	</body>
</html>