package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import admin.*;

public class InsertCateProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String input_top = request.getParameter("input_top");
		
		String sel_top3 = request.getParameter("sel_top3");
		String input_sub = request.getParameter("input_sub");
		
		CategoryMgr cateMgr = CategoryMgr.getDao();
		
		if(input_top != null) { // 상위 카테고리를 입력한 경우
			cateMgr.insertTopcate(input_top);
		} else if(sel_top3 != null && input_sub != null) {
			int top_idx = Integer.parseInt(sel_top3);
			int cnt = cateMgr.getInsertSubcate_idx(top_idx);
			
			CategoryDTO cateDto = new CategoryDTO();
			
			cateDto.setTopcate_idx(top_idx);
			cateDto.setSubcate_idx(cnt);
			cateDto.setSubcate_title(input_sub);
			
			cateMgr.insertSubcate(cateDto);
		}
		
		return "/admin/insertCatePro.jsp";
	}
}
