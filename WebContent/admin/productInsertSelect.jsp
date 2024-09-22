<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<option value="">하위 카테고리 선택</option>
<option value="">-------------------------------------</option>

<c:forEach var="dto" items="${subList}">
	<option value="${dto.subcate_idx}">${dto.subcate_title}</option>
</c:forEach>
