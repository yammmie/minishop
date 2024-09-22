package user.action;

import javax.servlet.http.*;
import shop.action.*;
import admin.*;
import user.*;
import java.util.*;

public class MainAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		CategoryMgr cateMgr = CategoryMgr.getDao();
		List<CategoryDTO> topCateList = new ArrayList<CategoryDTO>();
		
		ProductMgr proMgr = ProductMgr.getDao();
		List<ProductDTO> proList = new ArrayList<ProductDTO>();
		
		topCateList = cateMgr.getTopcateList();
		proList = proMgr.getGoodList();
		
		request.setAttribute("topCateList", topCateList);
		request.setAttribute("proList", proList);
		
		return "/user/main.jsp";
	}
}