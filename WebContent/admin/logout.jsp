<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<c:remove var="id" scope="session" /> <%-- 변수 제거도 하면서 세션 무효화 --%>

<meta http-equiv="Refresh" content="0;url=${ctxpath}/user/main.do">