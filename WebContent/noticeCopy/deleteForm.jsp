<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int num = Integer.parseInt(request.getParameter("num"));
	String pageNum = request.getParameter("pageNum");
%>

<html>
	<head>
		<script>
			function deleteCheck() {
				this.submit();
			}
		</script>
	</head>
	<body>
		<center><h3>글 삭제</h3></center>
		
		<form method="post" action="deletePro.jsp?pageNum=<%=pageNum%>" onSubmit="return deleteCheck()">
			<%-- id가 관리자가 맞는지 확인 --%>
			<input type="hidden" name="num" value="<%=num%>">
			<input type="submit" value="글 삭제">
		</form>
	</body>
</html>
