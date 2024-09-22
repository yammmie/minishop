<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="notice.*" %>

<%
	request.setCharacterEncoding("utf-8");

	int num = Integer.parseInt(request.getParameter("num"));
	String pageNum = request.getParameter("pageNum");
	
	NoticeDAO dao = NoticeDAO.getDao();
	NoticeDTO dto = dao.getUpdateNotice(num);
%>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script src="script.js"></script>
		<style>
			body {
				margin-top: 20px;
			}
		
			table {
				margin-top: 20px;
			}
		</style>
	</head>
	<body>
		<center><h3>글 수정</h3></center>
		<form name="writeForm" method="post" action="updatePro.jsp?pageNum=<%=pageNum%>" onSubmit="return writeSave()">
			<table width="410" border="1" cellspacing="0" cellpadding="5" align="center">
				<tr>
					<td align="center">제목</td>
					<td>
						<input type="text" name="subject" id="subject" size="40" value="<%=dto.getSubject()%>">
					</td>
				</tr>
				<tr>
					<td align="center">내용</td>
					<td>
						<textarea name="content" id="content" rows="10" cols="40">
							<%=dto.getContent()%>
						</textarea>
					</td>
				</tr>
				<tr height="50">
					<td colspan="2" align="center">
						<input type="hidden" name="num" value="<%=num%>">
						<input type="submit" value="글 수정">
						<input type="reset" value="초기화">
						<input type="button" value="글 목록" onClick="location='list.jsp?pageNum=<%=pageNum%>'">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>