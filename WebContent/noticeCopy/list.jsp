<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="notice.*" %>
<%@ page import="java.util.*" %>
<%@ include file="/view/color.jspf" %>

<%
	String pageNum = request.getParameter("pageNum");
	
	if(pageNum == null)
		pageNum = "1";

	int pageSize = 10;
	int currentPage = Integer.parseInt(pageNum); // 현재 페이지
	int startRow = (currentPage-1)*pageSize+1; // 페이지의 시작 글번호
	int endRow = currentPage*pageSize; // 페이지의 끝 번호
	int count = 0; // 글 개수
	int number = 0; // 글 번호
	List list = null; // 데이터 받을 변수
	
	NoticeDAO dao = NoticeDAO.getDao();
	count = dao.getArticleCount(); // 글 개수 얻기
	
	if(count > 0)
		list = dao.getList(startRow, pageSize);
	
	number = count-(currentPage-1)*10;
%>

<html>
	<body>
		<center><h3>글 목록 / 전체 글 : <%= count %></h3></center>
		
	<%
		if(count == 0) { // 글이 없으면
		%>
			<table width="700" border="1" cellspacing="0" cellpadding="3" align="center">
				<tr>
					<td align="right">
						<input type="button" value="작성" onClick="location.href='writeForm.jsp'">
					</td>
				</tr>
				<tr>
					<td align="center">
						저장된 공지가 없습니다
					</td>
				</tr>
			</table>
		<%
		} else {
		%>
			<%-- id가 관리자일 경우 띄우기 --%>
			<table width="700" border="1" cellspacing="0" cellpadding="3" align="center">
				<tr>
					<td align="right">
						<input type="button" value="작성" onClick="location.href='writeForm.jsp'">
					</td>
				</tr>
			</table>
			<table width="700" border="1" cellspacing="0" cellpadding="3" align="center">
				<tr>
					<td align="center" width="50">번호</td>
					<td align="center" width="200">제목</td>
					<td align="center" width="100">작성자</td>
					<td align="center" width="200">작성일</td>
				</tr>
			<%
				for(int i=0; i<list.size(); i++) {
					NoticeDTO dto = (NoticeDTO)list.get(i);
				%>
					<tr>
						<td align="center"><%= number-- %></td>
						<td>
							<a href="content.jsp?num=<%=dto.getNum()%>&pageNum=<%=currentPage%>"><%=dto.getSubject()%></a>
						</td>
						<td align="center">
							<b>관리자</b>
						</td>
						<td align="center">
							<%= dto.getRegdate() %>
						</td>
					</tr>
				<%	
				}
			%>
			</table>
		<%
		}
	%>
	
		<%-- 블럭 처리, 페이지 처리 --%>
	<%
		if(count > 0) { // 글이 있을 때만 블럭과 페이지 처리
		%>
			<table width="700" border="1" cellspacing="0" cellpadding="3" align="center">
				<tr>
					<td align="center">
					<%
						int pageCount = count/pageSize+(count%pageSize==0?0:1);
						int pageBlock = 10; // 블럭당 페이지 수 10개
						int startPage = (int)(currentPage/pageBlock)*10+1; // 시작 페이지						
						int endPage = startPage+pageBlock-1; // 끝 페이지
						// 에러 방지를 위해
						if(endPage > pageCount) 
							endPage = pageCount;
						
						// 이전 블럭
						if(startPage > 10) { 
						%>
							<a href="list.jsp?pageNum=<%=startPage-pageBlock%>">[ 이전 블럭 ]</a>
						<%
						}
						
						// page 처리
						for(int i=startPage; i<=endPage; i++) {
						%>
							<a href="list.jsp?pageNum=<%=i%>">[ <%=i%> ]</a>
						<%
						}
						
						// 다음 블럭
						if(endPage < pageCount) {
						%>
							<a href="list.jsp?pageNum=<%=startPage+pageBlock%>">[ 다음 블럭 ]</a>
						<%
						}
					%>
					</td>
				</tr>
			</table>
		<%
		}
	%>
	</body>
</html>
