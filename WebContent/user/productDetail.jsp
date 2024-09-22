<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
			function btn_click(str) {
				if(str == "cart") {
					proForm.action="insertCartPro.do";
				} else if(str == "buy") { // buy
					proForm.action="buy.do";
				}
			}
		</script>
		<script>
			$(function() {
				var url = location.href;
				
				if(url.indexOf('?') != -1) { // url에 파라미터 값이 넘어왔으면
					var params = (url.slice(url.indexOf('?')+1, url.length)).split('&'); // ? 없으면 -1
					var pro_no = params[0].split('=')[1]; // pro_no
				}
				
				$("#sel_color").on("change", function() {
					var x = $(this).val();
					
					$.ajax({
						type:"post",
						url:"productDetailPro.do",
						data:"pro_no="+pro_no+"&color="+x,
						dataType:"html",
						success:function(data) {
							$("#sel_size").html(data);
						}
					});
				});
			});
		</script>
	</head>
	<body>
		<div id="detail_wrap">
			<div id="product_image">
				<div id="img">
					<img src="${ctxpath}/imgs/${proDto.image}" width="100%" height="100%">
				</div>
			</div>
			<div id="product_info">
				<div id="product_content">
					<h3>${proDto.name}</h3>
					<hr id="title_hr">
					<div id="detail">
						<p class="prodetail">
							${pro_detail}
						</p>
					</div>
					<hr>
					<form name="proForm" method="post">
						<table id="proinfo">
							<tr>
								<td width="100px">판매가</td>
								<td>${proDto.price} 원</td>
							</tr>
							<tr>
								<td>색상</td>
								<td>
									<select name="sel_color" id="sel_color" required>
										<option value="">[필수] 색상</option>
										<option value="">-------------------------------</option>
										
										<c:forEach var="col" items="${color}">
											<option value="${col}">${col}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td>사이즈</td>
								<td>
									<select name="sel_size" id="sel_size" required>
										<option value="">[필수] 사이즈</option>
										<option value="">-------------------------------</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>수량</td>
								<td>
									<input type="number" name="quantity" id="quantity" value="1" min="1" required>
								</td>
							</tr>
						</table>
						
						<input type="hidden" name="pro_no" value="${proDto.pro_no}">
						<input type="hidden" name="id" value="${sessionScope.id}">
						<input type="hidden" name="before" value="productDetail">
						
						<input type="submit" id="btn_buy" onClick="btn_click('buy')" value="BUY IT NOW">
						<input type="submit" id="btn_cart" onClick="btn_click('cart')" value="CART">
					</form>
				</div>
			</div>
		</div>
	</body>
</html>

