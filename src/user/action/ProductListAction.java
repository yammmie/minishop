package user.action;

import javax.servlet.http.*;
import shop.action.*;
import admin.*;
import user.*;
import java.util.*;

public class ProductListAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		int cate_no = Integer.parseInt(request.getParameter("cate_no"));
		String tmpsub = request.getParameter("sub_no");
		int sub_no;
		
		CategoryMgr cateMgr = CategoryMgr.getDao();
		ProductMgr proMgr = ProductMgr.getDao();
		List<CategoryDTO> topCateList = new ArrayList<CategoryDTO>();
		List<CategoryDTO> subCateList = new ArrayList<CategoryDTO>();
		List<ProductDTO> topGoodList = new ArrayList<ProductDTO>();
		List<ProductDTO> subGoodList = new ArrayList<ProductDTO>();
		
		String topcateTitle = cateMgr.getTopcateTitle(cate_no);
		
		if(tmpsub != null) { // 선택된 하위 카테고리가 있으면
			sub_no = Integer.parseInt(tmpsub);
						
			subGoodList = proMgr.getSubcateGoodList(cate_no, sub_no);
		} else { // 상위 카테고리만 선택한 경우
			topGoodList = proMgr.getTopcateGoodList(cate_no);
		}
				
		topCateList = cateMgr.getTopcateList();
		subCateList = cateMgr.getSubcateList(cate_no);
		
		request.setAttribute("cate_no", cate_no);
		request.setAttribute("topCateList", topCateList);
		request.setAttribute("subCateList", subCateList);
		request.setAttribute("topcateTitle", topcateTitle);
		request.setAttribute("topGoodList", topGoodList);
		request.setAttribute("subGoodList", subGoodList);
		
		return "/user/productList.jsp";
	}
}
