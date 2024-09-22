<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/view/color.jspf" %>

<html>
	<head>
		<script src="//code.jquery.com/jquery-3.4.0.min.js"></script>
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
			function checkIt() {
				if($("#passwd").val()==""){
					alert("비밀번호를 입력하시요");
					$("#passwd").focus();
					
					return false;
				}
				
				if($("#passwd2").val()==""){
					alert("비밀번호확인을 입력하시요");
					$("#passwd2").focus();
					
					return false;
				}
				
				if($("#passwd").val() != $("#passwd2").val()){
					alert("비밀번호확인이 일치하지않습니다");
					$("#passwd").val('');
					$("#passwd2").val('');
					$("#passwd").focus();
					
					return false;
				}
			}
		</script>
		<script>
			$(function() {
				$('#email3').on('change', function(){
					$('#email2').val($('#email3 option:selected').text());
				});
			});
		</script>
	</head>
	<body>
		<form method="post" action="${ctxpath}/admin/modifyPro.do" onSubmit="return checkIt()">
			<table id="iptable">
				<tr height="40">
					<td align="center">아이디</td>
					<td>
						<input type="text" name="id" id="id" value="${sessionScope.id}" readonly="readonly">
						
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
						<input type="text" name="name" id="name" value="${memDto.name}" size="20" maxlength="10">
					</td>
				</tr>
				<tr height="50">
					<td align="center">주소</td>
					<td>
						<input type="text" name="zipcode" id="zipcode" value="${memDto.zipcode}" size="7" readonly="readonly">
						<input type="button" value="우편번호찾기" onClick="openDaumPostcode()"><br>
						<input type="text" name="addr1" id="addr1" size="40" value="${memDto.addr1}" readonly="readonly">&nbsp;기본주소<br>
						<input type="text" name="addr2" id="addr2" size="40" value="${memDto.addr2}">&nbsp;상세주소
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
						<input type="text" name="tel2" id="tel2" size="4" value="${tel2}">
						-
						<input type="text" name="tel3" id="tel3" size="4" value="${tel3}">
					</td>
				</tr>
				<tr height="40">
					<td align="center">이메일</td>
					<td>
						<input type="text" name="email1" id="email1" size="20" value="${memDto.email1}">
						@
						<input type="text" name="email2" id="email2" size="20" value="${memDto.email2}">
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
						<input type="submit" id="btn_join" value="회원가입">
						<input type="button" value="취소" onClick="location='${ctxpath}/user/main.do'">
					</td>
				</tr>
			</table>
		</form>
		
		<form method="post" action="${ctxpath}/admin/deletePro.do">
			<table width="600px" align="center">
				<tr>
					<td align="right">
						<input type="hidden" name="id" value="${sessionScope.id}">
						<input type="hidden" name="passwd" value="${memDto.passwd}">
						
						<input type="submit" value="회원탈퇴">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>