<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
	
	</head>
	<body>
		<div id="main_wrap">
			<ul class="prdList">
				<c:forEach var="dto" items="${proList}">
					<li>
						<div class="mainImg">
							<a href="${ctxpath}/user/productDetail.do?pro_no=${dto.pro_no}">
								<img src="${ctxpath}/imgs/${dto.image}" width="100%" height="100%">	
							</a>
						</div>
						<div class="mainText">
							<p><b>${dto.name}</b></p>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</body>
</html>
          