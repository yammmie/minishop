<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/view/color.jspf" %>

<fmt:requestEncoding value="UTF-8" />

<html>
	<body>
		<div id="notice_wrap">
			<center><h3>공지사항</h3></center>
			
			<%-- 관리자일 경우에만 작성 버튼 보이게 --%>
			<c:if test="${sessionScope.id=='admin'}">
				<table width="700" align="center">
					<tr height="70">
						<td align="right">
							<input type="button" id="notice_write" value="작성" onClick="location.href='${ctxpath}/notice/writeForm.do'">
						</td>
					</tr>
				</table>
			</c:if>
			
			<c:if test="${count==0}"> <%-- 글이 없으면 --%>
				<tr>
					<td align="center">
						저장된 공지가 없습니다
					</td>
				</tr>
			</c:if>
				
			<c:if test="${count>0}"> <%-- 글이 있으면 --%>
				<table width="700" id="noticetable" align="center">
					<tr height="40">
						<td align="center" width="70">번호</td>
						<td align="center" width="370">제목</td>
						<td align="center" width="110">작성자</td>
						<td align="center" width="150">작성일</td>
					</tr>
					
					<c:forEach var="dto" items="${noticeList}">
						<tr height="40">
							<td align="center">
								<c:out value="${number}" />
								<c:set var="number" value="${number-1}" />
							</td>
							<td>
								<a href="${ctxpath}/notice/content.do?num=${dto.num}&pageNum=${currentPage}">
									${dto.subject}
								</a>
							</td>
							<td align="center">
								<b>관리자</b>
							</td>
							<td align="center">
								${dto.regdate}
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			
			<%-- 블럭 처리, 페이지 처리 --%>
			<c:if test="${count>0}"> <%-- 글이 있으면 --%>
				<table width="700" cellpadding="3" align="center">
					<tr height="70">
						<td align="center">
							<fmt:parseNumber var="result" value="${currentPage/10}" integerOnly="true" />
							<c:set var="startPage" value="${result*10+1}" />
							<c:set var="endPage" value="${startPage+pageBlock-1}" />
						
							<%-- 에러 방지 --%>
							<c:if test="${endPage>pageCount}">
								<c:set var="endPage" value="${pageCount}" />
							</c:if>
							
							<%-- 이전 블럭 --%>
							<c:if test="${startPage>10}">
								<a href="${ctxpath}/notice/list.do?pageNum=${startPage-10}">[ 이전 블럭 ]</a>
							</c:if>
							
							<%-- 페이지 처리 --%>
							<c:forEach var="i" begin="${startPage}" end="${endPage}">
								<a href="${ctxpath}/notice/list.do?pageNum=${i}">[ ${i} ]</a>
							</c:forEach>
							
							<%-- 다음 블럭 --%>
							<c:if test="${endPage<pageCount}">
								<a href="${ctxpath}/notice/list.do?pageNum=${startPage+10}">[ 다음 블럭 ]</a>
							</c:if>
						</td>
					</tr>
				</table>
			</c:if>
		</div>
	</body>
</html>
