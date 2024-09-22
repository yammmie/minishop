package admin.action;

import javax.servlet.http.*;
import admin.*;
import shop.action.*;

public class UpdateTopCateProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		int top_idx = Integer.parseInt(request.getParameter("sel_top1"));
		String top_title = request.getParameter("top_title");
		
		CategoryMgr cateMgr = CategoryMgr.getDao();
		
		cateMgr.updateTopcate(top_title, top_idx);
		
		return "/admin/updateTopCatePro.jsp";
	}
}
