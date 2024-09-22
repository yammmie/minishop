<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="notice.*" %>

<%
	//list.jsp에서 보내준 num, pageNum 받기
	int num = Integer.parseInt(request.getParameter("num"));
	String pageNum = request.getParameter("pageNum");
	
	NoticeDAO dao = NoticeDAO.getDao();
	NoticeDTO dto = dao.getArticle(num);
%>

<html>
	<body>
		<center><h3>글 내용보기</h3></center>
		<table width="500" border="1" cellspacing="0" cellpadding="3" align="center">
			<tr height="30">
				<td align="center" width="125">제목</td>
				<td colspan="3" width="375"><%=dto.getSubject()%></td>
			</tr>
			<tr height="30">
				<td align="center">작성자</td>
				<td><b>관리자</b></td>
				<td align="center">작성일</td>
				<td><%=dto.getRegdate()%></td>
			</tr>
			<tr height="30">
				<td align="center" width="125">내용</td>
				<td colspan="3" width="375">
				<%
					String im = dto.getContent();
					im = im.replace("\n", "<br>");
				%>
					<%= im %>
				</td>
			</tr>
			<tr height="30">
				<td colspan="4" align="center">
					<%-- id가 관리자일 경우 수정, 삭제 띄우기 --%>
					<input type="button" value="수정" onClick="location='updateForm.jsp?num=<%=dto.getNum()%>&pageNum=<%=pageNum%>'">
					<input type="button" value="삭제" onClick="location='deleteForm.jsp?num=<%=dto.getNum()%>&pageNum=<%=pageNum%>'">
					<input type="button" value="목록으로" onClick="location='list.jsp?pageNum=<%=pageNum%>'">
				</td>
			</tr>
		</table>
	</body>
</html>
    
    