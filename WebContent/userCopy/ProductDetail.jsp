<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="shopdb.*" %>
<%@ page import="java.util.*" %>

<%
	request.setCharacterEncoding("utf-8");

	int pro_no = Integer.parseInt(request.getParameter("pro_no"));
	ProductMgr proMgr = ProductMgr.getDao();
	ProductDTO proDto = (ProductDTO)proMgr.getProduct(pro_no);
	List<ProductDTO> colorList = proMgr.getProductColor(pro_no);
	String color[] = new String[colorList.size()];

	for(int i=0; i<colorList.size(); i++) {
		ProductDTO colorDto = (ProductDTO)colorList.get(i);
		
		color[i] = colorDto.getColor();
	}
%>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script>
			function btn_click(str) {
				if(str == "cart") {
					proForm.action="Cart.jsp";
				} else { // buy
					proForm.action="Buy.jsp";
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
				
				$("#sel_color").on('change', function() {
					var x = $(this).val();
					//alert(x);
					
					$.ajax({
						type:"post",
						url:"ProductDetailProc.jsp",
						data:"pro_no="+pro_no+"&color="+x,
						dataType: "html",
						success:function(data) {
							$('#sel_size').html(data);
						}
					});
				});
			});
		</script>
		<style>
			body {
				margin: 20px 0;
			}
			
			#wrap {
				width: 900px;
				margin: auto;
				font-size: 13px;
			}
			
			#product_image {
				width: 500px;
				height: 650px;
				float: left;
			}
			
			#img {
				width: 90%;
				height: 90%;
				margin: 0 auto;
			}
			
			#product_info {
				width: 400px;
				height: 80%;
				float: right;
			}
			
			#content {
				width: 90%;
				height: 530px;
				margin: 0 auto;
				vertical-align: middle;
			}
			
			#title_hr {
				border: 1px solid gray;
			}
			
			#detail {
				height: 170px;
				width: 100%;
			}
			
			#detail>p {
				width: 90%;
				height: 90%;
				margin: 5%;
				text-align: center;
				color: gray;
				font-size: 13px;
			}
			
			#proinfo {
				width: 100%;
				margin: 20px 0;
			}
			
			#proinfo tr {
				height: 40px;
			}
			
			#proinfo td {
				padding: 5px;
			}
			
			#btn_buy {
				width: 100%;
				height: 40px;
				border: none;
				color: white;
				background-color: #bc937c;
				font-weight: bold;
				margin-bottom: 10px;
			}
			
			#btn_cart {
				width: 100%;
				height: 40px;
				border: 1px solid #6f7072;
				background-color: white;
				font-weight: bold;
			}
			
			#btn_buy, #btn_cart {
				cursor: pointer;
			}
			
			#sel_color, #sel_size {
				height: 35px;
				width: 100%;
			}
			
			input[type='number'] {
				height: 35px;
				width: 100px;
			}
		</style>
	</head>
	<body>
		<div id="wrap">
			<div id="product_image">
				<div id="img">
					<img src="<%=request.getContextPath()%>/imgs/<%=proDto.getImage()%>" width="100%" height="100%">
				</div>
			</div>
			<div id="product_info">
				<div id="content">
					<h3><%= proDto.getName() %></h3>
					<hr id="title_hr">
					<div id="detail">
						<p>
							<%= proDto.getDetail() %>
						</p>
					</div>
					<hr>
					<form name="proForm" method="post">
						<table id="proinfo">
							<tr>
								<td width="100px">판매가</td>
								<td><%= proDto.getPrice() %> 원</td>
							</tr>
							<tr>
								<td>색상</td>
								<td>
									<select name="sel_color" id="sel_color" required>
										<option value="">[필수] 색상</option>
										<option value="">-------------------------------</option>
									<%
										for(int i=0; i<color.length; i++) {
										%>
											<option value="<%=color[i]%>"><%= color[i] %></option>
										<%
										}
									%>
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
						
						<input type="hidden" name="pro_no" value="<%=proDto.getPro_no()%>">
						
						<input type="submit" id="btn_buy" onClick="btn_click('buy')" value="BUY IT NOW">
						<input type="submit" id="btn_cart" onClick="btn_click('cart')" value="CART">
					</form>
				</div>
			</div>
		</div>
	</body>
</html>

