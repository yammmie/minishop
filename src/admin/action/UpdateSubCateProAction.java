package admin.action;

import javax.servlet.http.*;
import admin.*;
import shop.action.*;

public class UpdateSubCateProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		int top_idx = Integer.parseInt(request.getParameter("sel_top2"));
		int sub_idx = Integer.parseInt(request.getParameter("sel_sub"));
		String sub_title = request.getParameter("sub_title");
		
		System.out.println("top_idx : " + top_idx);
		System.out.println("sub_idx : " + sub_idx);
		System.out.println("sub_title : " + sub_title);
		
		CategoryMgr cateMgr = CategoryMgr.getDao();
		CategoryDTO cateDto = new CategoryDTO();
		
		cateDto.setSubcate_title(sub_title);
		cateDto.setSubcate_idx(sub_idx);
		cateDto.setTopcate_idx(top_idx);
		
		cateMgr.updateSubcate(cateDto);
		
		return "/admin/updateSubCatePro.jsp";
	}
}
