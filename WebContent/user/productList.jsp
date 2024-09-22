<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<body>
		<center><h3>${topcateTitle}</h3></center>
	
		<div id="submenubar">
			<c:forEach var="dto" items="${subCateList}">
				<a href="${ctxpath}/user/productList.do?cate_no=${cate_no}&sub_no=${dto.subcate_idx}" class="menubar_a">${dto.subcate_title}</a>
			</c:forEach>
		</div>
		
		<div id="proList_wrap">
			<ul class="prdList">
				<c:if test="${empty subGoodList}">
					<c:if test="${empty topGoodList}">
						<p class="pro_empty">제품이 없습니다.</p>
					</c:if>
					
					<c:forEach var="topDto" items="${topGoodList}"> <%-- 상위 카테고리만 선택한 경우 --%>
						<li>
							<div class="mainImg">
								<a href="${ctxpath}/user/productDetail.do?pro_no=${topDto.pro_no}">
									<img src="${ctxpath}/imgs/${topDto.image}" width="100%" height="100%">	
								</a>
							</div>
							<div class="mainText">
								<p><b>${topDto.name}</b></p>
							</div>
						</li>
					</c:forEach>
				</c:if>
				
				<c:if test="${!empty subGoodList}">
					<c:forEach var="subDto" items="${subGoodList}">
						<li>
							<div class="mainImg">
								<a href="${ctxpath}/user/productDetail.do?pro_no=${subDto.pro_no}">
									<img src="${ctxpath}/imgs/${subDto.image}" width="100%" height="100%">	
								</a>
							</div>
							<div class="mainText">
								<p><b>${subDto.name}</b></p>
							</div>
						</li>
					</c:forEach>
				</c:if>
			</ul>
		</div>
	</body>
</html>



          