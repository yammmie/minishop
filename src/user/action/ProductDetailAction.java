package user.action;

import javax.servlet.http.*;

import admin.CategoryDTO;
import admin.CategoryMgr;
import shop.action.*;
import user.*;

import java.util.*;

public class ProductDetailAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		int pro_no = Integer.parseInt(request.getParameter("pro_no"));
		ProductMgr proMgr = ProductMgr.getDao();
		ProductDTO proDto = (ProductDTO)proMgr.getProduct(pro_no);
		List<ProductDTO> colorList = proMgr.getProductColor(pro_no);
		String color[] = new String[colorList.size()];
		
		CategoryMgr cateMgr = CategoryMgr.getDao();
		List<CategoryDTO> topCateList = new ArrayList<CategoryDTO>();
		
		topCateList = cateMgr.getTopcateList();
		
		for(int i=0; i<colorList.size(); i++) {
			ProductDTO colorDto = (ProductDTO)colorList.get(i);
			
			color[i] = colorDto.getColor();
		}

		topCateList = cateMgr.getTopcateList();

		String pro_detail = proDto.getDetail();
		pro_detail = pro_detail.replace("\n", "<br>");
		
		request.setAttribute("pro_detail", pro_detail);
		
		request.setAttribute("topCateList", topCateList);
		request.setAttribute("proDto", proDto);
		request.setAttribute("color", color);
		
		
		return "/user/productDetail.jsp";
	}
}
