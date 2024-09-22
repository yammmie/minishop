<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script type="text/javascript" src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
			function writeSave() {
				if(!$("#topcate_idx>option:selected").val()){
					alert("상위 카테고리를 선택하세요");
					
					return false;
			    }
			    
			    if(!$("#subcate_idx>options:selected").val()){
			    	alert("하위 카테고리을 선택하세요");
					
					return false;
			    }
			    
				if($("#name").val()==''){
				   alert("상품명을 입력하세요 ");
				   $("#name").focus();
					
					return false;
			  	}
				
				if($("#price").val()==''){
				   alert("가격을 입력하세요 ");
				   $("#price").focus();
					
					return false;
			   	}		
				
				if($("#detail").val()==''){
				   alert("설명을 입력하세요 ");
				   $("#detail").focus();
					
					return false;
			   	}		
				
				document.inputForm.submit();
			}
		</script>
		<script>
			$(function() {
				$("#sel_top").on("change", function() {
					var x = $(this).val();
					
					$.ajax({
						type:"post",
						url:"productInsertSelect.do",
						data:"topcate_idx="+x,
						dataType:"html",
						success:function(data) {
							$("#sel_sub").html(data);
						}
					});
				});
			});
		</script>
	</head>
	<body>
		<center><h3>상품 등록</h3></center>
	
		<form name="inputForm" onSubmit="return writeSave()" method="post" action="${ctxpath}/admin/productInsertPro.do" encType="multipart/form-data">
			<table id="ordertable">
				<tr height="40">
					<td align="center">
						상위 카테고리
					</td>
					<td>
						<select name="sel_top" id="sel_top" required="required">
							<option value="">상위 카테고리 선택</option>
							<option value="">-------------------------------------</option>
							
							<c:forEach var="dto" items="${topList}">
								<option value="${dto.topcate_idx}">${dto.topcate_title}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="40">
					<td align="center">
						하위 카테고리
					</td>
					<td>
						<select name="sel_sub" id="sel_sub" required="required">
							<option value="">하위 카테고리 선택</option>
							<option value="">-------------------------------------</option>
						</select>
					</td>
				</tr>
				<tr height="40">
					<td align="center">상품명</td>
					<td>
						<input type="text" name="name" id="name" size="20">
					</td>
				</tr>
				<tr height="40">
					<td align="center">가격</td>
					<td>
						<input type="text" name="price" id="price" size="20">원
					</td>
				</tr>
				<tr height="40">
					<td align="center">이미지</td>
					<td>
						<input type="file" name="image" id="image">
					</td>
				</tr>
				<tr height="40">
					<td align="center">설명</td>
					<td>
						<textarea name="detail" id="detail" rows="10" cols="45"></textarea>
					</td>
				</tr>
				<tr height="50">
					<td colspan="2" align="center">
						<input type="submit" value="등록">
						<input type="button" value="취소" onClick="location.href='${ctxpath}/admin/productList.do'">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>