<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<c:if test="${flag=='update'}">
	<script>
		alert("수정하였습니다");
	</script>
</c:if>

<c:if test="${flag=='delete'}">
	<script>
		alert("삭제되었습니다");
	</script>
</c:if>

<c:if test="${flag=='false'}">
	<script>
		alert("재고보다 많은 수량을 선택하셨습니다\n수량을 다시 선택해주세요");
		history.back();
	</script>
</c:if>

<meta http-equiv="Refresh" content="0;url=${ctxpath}/user/cartList.do">