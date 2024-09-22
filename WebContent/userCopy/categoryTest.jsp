<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="shopdb.*" %>
<%@ page import="java.util.*" %>

<%
	ProductMgr proMgr = ProductMgr.getDao();
	List<ProductDTO> list = null;
	ProductDTO dto = null;
%>

<%
	//topcate_idx 얻기
	int topidx = proMgr.getTopcate_idx("하의");
	int subidx = proMgr.getInsertSubcate_idx(topidx)+1;
	

	/* dto = new ProductDTO();
	
	dto.setTopcate_idx(topidx);
	dto.setSubcate_idx(subidx);
	dto.setSubcate_title("반바지"); */
	
	// subcate 추가
	//proMgr.insertSubcate(dto); 
	
	
	// subcate 수정
	/* subidx = proMgr.getSubcate_idx("반바지");  // 3
	
	dto = new ProductDTO();
	
	dto.setTopcate_idx(topidx); // 3 (하의)
	dto.setSubcate_idx(subidx);
	dto.setSubcate_title("스커트");
		
	proMgr.updateSubcate(dto); */
	
	
	// subcate 삭제
	/* subidx = proMgr.getSubcate_idx("스커트");
	
	proMgr.deleteSubcate(topidx, subidx); */
%>
<%-- subcate list 출력--%>
<%
	list = proMgr.getSubcateList(topidx);
	
	for(int i=0; i<list.size(); i++) {
		dto = (ProductDTO)list.get(i);
	%>
		<%= dto.getTopcate_idx() %>
		<%= dto.getSubcate_idx() %>
		<%= dto.getSubcate_title() %>
		<br>
	<%
	}
%> 

<br><br>
topidx : <%= topidx %>
subidx : <%= subidx %>

<%-- 
<%
	// topcate 추가
	//proMgr.insertTopcate("가방");

	// topcate 출력
	
	list = proMgr.getTopcateList();
	
	for(int i=0; i<list.size(); i++) {
		dto = (ProductDTO)list.get(i);
	%>
		<%= dto.getTopcate_idx() %>
		<%= dto.getTopcate_title() %>
		<br>
	<%
	}

	// topcate_idx 얻기
	int idx = proMgr.getTopcate_idx("가방");
%>
	<br>
	idx : <%= idx %><br><br>

<%
	// topcate 수정
	//proMgr.updateTopcate("하의", idx);

	// topcate 삭제
	//proMgr.deleteTopcate(idx);	
%> --%>


