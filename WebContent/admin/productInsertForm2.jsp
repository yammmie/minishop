<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script>
			function writeSave() {
				if($("#color").val()=='') {
				   alert("색상을 입력하세요 ");
				   $("#color").focus();
				   
				   return false;
			  	}

				if($("#size").val()=='') {
				   alert("사이즈를 입력하세요 ");
				   $("#size").focus();
				   
				   return false;
			  	}
				
				if($("#stock").val()=='') {
				   alert("수량을 입력하세요 ");
				   $("#stock").focus();
				   
				   return false;
			  	}
				
				if($("#size").length != $("#stock").length){
					alert("사이즈와 수량의 수가 일치하지 않습니다");
					$("#size").focus();
					
					return false;
				}
				
				document.inputForm.submit();
			}
		</script>
	</head>
	<body>
		<center><h3>상품 등록</h3></center>
		
		<form name="inputForm" method="post" onSubmit="return writeSave()" action="${ctxpath}/admin/productInsertPro2.do">
			<table id="ordertable">
				<tr height="30">
					<td align="center">상품명</td>
					<td>
						${name}
					</td>
				</tr>	
				<tr height="30">
					<td align="center">색상</td>
					<td>
						<input type="text" id="color" name="color" size="20">
						ex) 블랙,블루,레드
					</td>
				</tr>	
				<tr height="30">
					<td align="center">사이즈</td>
					<td>
						<input type="text" id="size" name="size" size="20">
					 	ex) S,M,L
					</td>
				</tr>	
				<tr height="30">
					<td align="center">수량</td>
					<td>
						<input type="text" id="stock" name="stock" size="20">
						ex) 50,40,30....(사이즈에 해당하는 수량을 순서대로 표기)
					</td>
				</tr>
				<tr height="50">
					<td colspan="2" align="center">
						<input type="hidden" id="pro_no" name="pro_no" size="20" value="${pro_no}">
						
						<input type="submit" value="등록">
						<input type="button" value="취소" onClick="location='${ctxpath}/admin/productList.do'">
					</td>
				</tr>	
			</table>
		</form>
	</body>
</html>