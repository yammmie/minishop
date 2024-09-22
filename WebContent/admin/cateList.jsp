<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
			function top_btn(str, form) {
				if(str == "update") {
					if(form.top_title.value == "") {
						alert("수정할 상위 카테고리명을 입력하세요");
					
						return false;
					}
					
					form.action="updateTopCatePro.do";
				}
				
				else if(str == "delete")
					form.action="deleteCatePro.do";
			}
			
			function sub_btn(str, form) {
				if(str == "update") {
					if(form.sub_title.value == "") {
						alert("수정할 하위 카테고리명을 입력하세요");
					
						return false;
					}
					
					form.action="updateSubCatePro.do";
				}
				
				else if(str == "delete")
					form.action="deleteCatePro.do";
			}
		</script>
		<script>
			$(function() {
				$("#sel_top2").on("change", function() {
					var x = $(this).val();
					
					$.ajax({
						type:"post",
						url:"cateListPro.do",
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
		<div id="cateList_wrap">
			<div id="cateList_top">
				<center><h3>상위 카테고리</h3></center>
				
				<form name="topForm" method="post">
					<table class="catetable">
						<tr height="40">
							<td align="center">상위 카테고리</td>
							<td>
								<select name="sel_top1" id="sel_top1" required="required">
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
								수정할 카테고리명
							</td>
							<td>
								<input type="text" id="top_title" name="top_title">
							</td>
						</tr>
						<tr height="50">
							<td colspan="2" align="center">
								<input type="submit" value="수정" onClick="top_btn('update', this.form)">
								<input type="submit" value="삭제" onClick="top_btn('delete', this.form)">
							</td>
						</tr>
					</table>
				</form><br>
				
				<center><h3>상위 카테고리 추가</h3></center>
				
				<form name="topInForm" method="post" action="insertCatePro.do">
					<table class="catetable2">
						<tr height="40">
							<td colspan="2" align="center"><b>추가할 상위 카테고리명</b></td>		
						</tr>
						<tr height="40">
							<td>
								<input type="text" id="input_top" name="input_top">
							</td>
							<td>
								<input type="submit" value="추가">
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<div id="cateList_sub">
				<center><h3>하위 카테고리</h3></center>
				
				<form name="subForm" method="post">
					<table class="catetable">
						<tr height="40">
							<td>상위 카테고리</td>
							<td>
								<select name="sel_top2" id="sel_top2" required="required">
									<option value="">상위 카테고리 선택</option>
									<option value="">-------------------------------------</option>
									
									<c:forEach var="dto" items="${topList}">
										<option value="${dto.topcate_idx}">${dto.topcate_title}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr height="40">
							<td>하위 카테고리</td>
							<td>
								<select name="sel_sub" id="sel_sub" required="required">
									<option value="">하위 카테고리 선택</option>
									<option value="">-------------------------------------</option>
								</select>
							</td>
						</tr>
						<tr height="40">
							<td>
								수정할 카테고리명
							</td>
							<td>
								<input type="text" id="sub_title" name="sub_title">
							</td>
						</tr>
						<tr height="50">
							<td colspan="2" align="center">
								<input type="submit" value="수정" onClick="sub_btn('update', this.form)">
								<input type="submit" value="삭제" onClick="sub_btn('delete', this.form)">
							</td>
						</tr>
					</table>
				</form><br>
				
				<center><h3>하위 카테고리 추가</h3></center>
				
				<form name="subInForm" method="post" action="insertCatePro.do">
					<table class="catetable2">
						<tr height="40">
							<td colspan="2" align="center"><b>상위 카테고리 선택</b></td>
						</tr>
						<tr height="40">
							<td colspan="2" align="center">
								<select name="sel_top3" id="sel_top3" required="required">
									<option value="">상위 카테고리 선택</option>
									<option value="">-------------------------------------</option>
									
									<c:forEach var="dto" items="${topList}">
										<option value="${dto.topcate_idx}">${dto.topcate_title}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr></tr>
						<tr height="40">
							<td colspan="2" align="center"><b>추가할 하위 카테고리명</b></td>		
						</tr>
						<tr height="40">
							<td>
								<input type="text" id="input_sub" name="input_sub">
							</td>
							<td>
								<input type="submit" value="추가">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
    
    
    