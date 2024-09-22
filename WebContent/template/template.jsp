<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<c:if test="${!empty selDetail}">
	<jsp:include page="${selDetail}" flush="false" />
</c:if>

<c:if test="${empty selDetail}">
	<html>
		<head>
			<link href="../view/style.css?ver=1" rel="stylesheet" type="text/css">
		</head>
		<body>
			<div id="wrap">
				<div id="header">
					<jsp:include page="/module/top.jsp" flush="false" />
				</div>
				<%-- 메뉴바 (상위 카테고리 출력) --%>
				<c:if test="${CONTENT=='/user/main.jsp' || CONTENT=='/user/productList.jsp' || CONTENT=='/user/productDetail.jsp'}">
					<div id="menubar">
						<hr>
						<c:forEach var="dto" items="${topCateList}">
							<a class="menubar_a" href="${ctxpath}/user/productList.do?cate_no=${dto.topcate_idx}">${dto.topcate_title}</a>  
						</c:forEach>
						<%-- ${menubar} --%>
						<hr>
					</div>
				</c:if>
				<table id="content">
					<tr>
						<td>
							<jsp:include page="${CONTENT}" flush="false" />
						</td>
					</tr>
				</table>
				<div id="bottom">
					<jsp:include page="/module/bottom.jsp" flush="false" />
				</div>
			</div>
		</body>
	</html>
</c:if>