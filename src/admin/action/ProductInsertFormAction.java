package admin.action;

import java.util.List;
import javax.servlet.http.*;
import shop.action.*;
import admin.*;

public class ProductInsertFormAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		CategoryMgr cateMgr = CategoryMgr.getDao();
		List<CategoryDTO> topList = null;
		
		topList = cateMgr.getTopcateList();
		
		request.setAttribute("topList", topList);
		
		return "/admin/productInsertForm.jsp";
	}
}
