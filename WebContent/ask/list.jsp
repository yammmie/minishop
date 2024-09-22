<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/view/color.jspf" %>

<fmt:requestEncoding value="UTF-8"/><%--request.setCharacterEncoding("UTF-8") --%>

<html>
	<body>
		<div id="notice_wrap">
			<center><h3>ASK</h3></center>
			
			<table width="700" align="center">
				<tr height="70">
					<td align="right">
						<input type="button" id="ask_write" value="1:1 문의하기" onClick="location.href='${ctxpath}/ask/writeForm.do'">
					</td>
				</tr>
			</table>
			
			<c:if test="${count==0}">
				<table width="700" align="center">
					<tr>
						<td align="center">
							올라온 문의가 없습니다
						</td>
					</tr>
				</table>
			</c:if>
			
			<c:if test="${count>0}">
				<table width="700" id="noticetable" align="center">
					<tr height="40">
						<td align="center" width="70">번호</td>
						<td align="center" width="300">글제목</td>
						<td align="center" width="110">작성자</td>
						<td align="center" width="150">작성일</td>
						<td align="center" width="70">조회</td>
					</tr>
					
					<c:forEach var="dto" items="${articleList}">
						<tr height="40">
							<td align="center">
								<c:out value="${number}"/>
								<c:set var="number" value="${number-1}"/>
							</td>						
							<td>
								<%--답글이면--%>
								<c:if test="${dto.re_level>0}">
									<img src="../imgs11/level.gif" width="${5*dto.re_level}" height="16"/>
									<img src="../imgs11/re.gif">
								</c:if>
								
								<%--답글이 아닐 때 --%>
								<c:if test="${re_level==0 }">
									<img src="../imgs11/hot.gif" width="${5*dto.re_level}" height="16">
								</c:if>
								
								<%--글제목 : 글내용 보기로 가게 해줘야 함 --%>
								<a href="${ctxpath}/ask/content.do?num=${dto.num}&pageNum=${currentPage}">
									${dto.subject}
								</a>
							</td>
							<td align="center">
								${dto.name }
							</td>
							<td align="center">
								<fmt:formatDate value="${dto.regdate}"/>
							</td>
							<td align="center">
								${dto.readcount}
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			
			<%-- 블럭처리, 페이지처리 --%>
			<c:if test="${count>0}">
				<table width="700" cellpadding="3" align="center">
					<tr height="70">
						<td align="center">
							<fmt:parseNumber var="result" value="${currentPage/10}" integerOnly="true"/>
							<c:set var="startPage" value="${result*10+1}"/>
							<c:set var="endPage" value="${startPage+pageBlock-1}"/>
						
							<%--에러방지--%>
							<c:if test="${endPage>pageCount}">
								<c:set var="endPage" value="${pageCount}"/>
							</c:if>
							
							<%--이전블럭--%>
							<c:if test="${startPage>10}">
								<a href="${ctxpath}/ask/list.do?pageNum=${startPage-10}">
									[ 이전블럭 ]
								</a>
							</c:if>
							
							<%--페이지 처리 --%>
							<c:forEach var="i" begin="${startPage}" end="${endPage}">
								<a href="${ctxpath}/ask/list.do?pageNum=${i}">
								[ ${i} ]
								</a>
							</c:forEach>
							
							<%--다음 블럭--%>
							<c:if test="${endPage<pagecount}">
								<a href="${ctxpath}/ask/list.do?pageNum=${startPage+10}">
									[ 다음블럭 ]
								</a>
							</c:if>
						</td>
					</tr>
				</table>
			</c:if>
		</div>
	</body>
</html>