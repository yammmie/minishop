<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf"%>

<html>
	<head>
		<script type="text/javascript" src="//code.jquery.com/jquery-3.4.0.min.js"></script>
		<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
		<script>
			function openDaumPostcode() {
				new daum.Postcode({
					oncomplete:function(data){
						document.getElementById('zipcode').value=data.postcode1+"-"+data.postcode2;
						document.getElementById('addr1').value=data.address;
		 			}
				}).open();
			}
		</script>
		<script>
			$(function() {
				$('#btn_join').attr('disabled', true);
				
				$('#email3').on('change', function(){
					$('#email2').val($('#email3 option:selected').text());
					//alert($('#email3 option:selected').text());
				});
				
				$('#tel1').on('');
			});
		</script>
		<script>
			function check(){
				if($('#id').val()==''){
					alert("id를 입력 하시요");
					$('#id').focus();
					return false;
				}
				
				if($('#passwd').val()==''){
					alert("암호를 입력 하시요");
					$('#passwd').focus();
					return false;
				}
				
				if($('#passwd2').val()==''){
					alert("암호 확인을 입력 하시요");
					$('#passwd2').focus();
					return false;
				}
				
				//암호와 암호 확인이 같은지 비교
				if($('#passwd').val() != $('#passwd2').val()){
					alert("암호와 암호 확인이 일치 하지 않습니다");
					$('#passwd').val('').focus();
					$('#passwd2').val('');
					return false;
				}
				
				if($('#name').val()==''){
					alert("이름을 입력하시요");
					$('#name').focus();
					return false;
				}
				
				if($('#zipcode').val()==''){
					alert("우편번호를 입력하시요");
					$('#zipcode').focus();
					return false;
				}
				
				if($('#addr1').val()==''){
					alert("주소를 입력하시요");
					$('#addr').focus();
					return false;
				}
				
				if($('#addr2').val()==''){
					alert("상세주소를 입력하시요");
					$('#addr').focus();
					return false;
				}
				
				if($('#tel').val()==''){
					alert("전화번호를 입력하시요");
					$('#tel').focus();
					return false;
				}
				
				if($('#email1').val()==''){
					alert("이메일을 입력하시요");
					$('#email1').focus();
					return false;
				}
				
				if($('#email2').val()==''){
					alert("이메일을 입력하시요");
					$('#email2').focus();
					return false;
				}
				
				return true;
			}
			
			function confirmIdcheck(form) {
				if($('#id').val()==''){
					alert("아이디를 입력하세요");
					form.id.focus();
					
					return false;
				} else {
					$.ajax({
						type:"POST",
						url:"confirmId.jsp",
						data:"id="+$('#id').val(),
						dataType:'JSON',
						success:function(data) {
							alert(data);
							
							if(data.check==1){
								alert("사용중인 id 입니다");
								$('#id').val('').focus();
							} else if(data.check==-1){//data.check-1
								alert("사용가능한 id입니다");
								$('#passwd').focus();
								
								$('#btn_join').attr('disabled', false);
							}
						}
					});
				}	
			}
		</script>
	</head>
	<body>
		<center><h3>회원가입</h3></center>
		
		<form method="post" action="${ctxpath}/admin/inputPro.do" onSubmit="return check()">
			<table id="iptable">
				<tr height="40">
					<td align="center">아이디</td>
					<td>
						<input type="text" name="id" id="id" size="20" maxlength="20">
						<input type="button" id="checkId" value="ID중복체크" onClick="confirmIdcheck()">
					</td>
				</tr>
				<tr height="40">
					<td align="center">비밀번호</td>
					<td>
						<input type="password" name="passwd" id="passwd" size="20" maxlength="20">
					</td>
				</tr>
				<tr height="40">
					<td align="center">비밀번호 확인</td>
					<td>
						<input type="password" name="passwd2" id="passwd2" size="20" maxlength="20">
					</td>
				</tr>
				<tr height="40">
					<td align="center">이름</td>
					<td>
						<input type="text" name="name" id="name" size="20" maxlength="10">
					</td>
				</tr>
				<tr height="50">
					<td align="center">주소</td>
					<td>
						<input type="text" name="zipcode" id="zipcode" size="7" readonly="readonly">
						<input type="button" value="우편번호찾기" onClick="openDaumPostcode()"><br>
						<input type="text" name="addr1" id="addr1" size="40" readonly="readonly">&nbsp;기본주소<br>
						<input type="text" name="addr2" id="addr2" size="40">&nbsp;상세주소
					</td>
				</tr>
				<tr height="40">
					<td align="center">전화번호</td>
					<td>
						<select name="tel1" id="tel1">
							<option value="010" selected="selected">010</option>
							<option value="011">011</option>
							<option value="016">016</option>
							<option value="017">017</option>
							<option value="018">018</option>
							<option value="019">019</option>
						</select>
						-
						<input type="text" name="tel2" id="tel2" size="4">
						-
						<input type="text" name="tel3" id="tel3" size="4">
					</td>
				</tr>
				<tr height="40">
					<td align="center">이메일</td>
					<td>
						<input type="text" name="email1" id="email1" size="20">
						@
						<input type="text" name="email2" id="email2" size="20">
						<select name="email3" id="email3">
							<option value="0">-이메일선택-</option>
							<option value="naver.com">naver.com</option>
							<option value="daum.net">daum.net</option>
							<option value="nate.com">nate.com</option>
							<option value="gmail.com">gmail.com</option>
							<option value="">직접입력</option>
						</select>
					</td>
				</tr>
				<tr height="50">
					<td colspan="2" align="center">
						<input type="submit" id="btn_join" value="회원가입" disabled="">
						<input type="button" value="취소" onClick="location='${ctxpath}/user/main.do'">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>