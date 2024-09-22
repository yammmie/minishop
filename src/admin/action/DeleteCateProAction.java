package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import admin.*;

public class DeleteCateProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");

		String sel_top1 = request.getParameter("sel_top1");
		String sel_top2 = request.getParameter("sel_top2");
		String sel_sub = request.getParameter("sel_sub");
		
		CategoryMgr cateMgr = CategoryMgr.getDao();
		
		if(sel_top1 != null) {
			int top_idx = Integer.parseInt(sel_top1);
			
			cateMgr.deleteTopcate(top_idx);
		} else if(sel_top2 != null && sel_sub != null) {
			int top_idx = Integer.parseInt(sel_top2);
			int sub_idx = Integer.parseInt(sel_sub);
			
			cateMgr.deleteSubcate(top_idx, sub_idx);
		}
		
		return "/admin/deleteCatePro.jsp";
	}
}
