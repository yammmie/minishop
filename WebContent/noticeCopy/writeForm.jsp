<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
		<center><h2>공지 작성</h2></center>
		
		<form name="writeForm" method="post" action="writePro.jsp" onSubmit="return writeSave()">
			<table width="410" border="1" cellspacing="0" cellpadding="5" align="center">
				<tr>
					<td align="center">글 제목</td>
					<td>
						<input type="text" name="subject" id="subject" size="40">
					</td>
				</tr>
				<tr>
					<td align="center">글 내용</td>
					<td>
						<textarea name="content" id="content" rows="10" cols="40"></textarea>
					</td>
				</tr>
				<tr height="50">
					<td colspan="2" align="center">
						<input type="submit" value="글 쓰기">
						<input type="reset" value="초기화">
						<input type="button" value="글 목록" onClick="location='list.jsp'">
					</td>
				</tr>	
			</table>
		</form>
	</body>
</html>