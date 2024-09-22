package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import java.util.*;
import admin.*;

public class CateListAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		CategoryMgr cateMgr = CategoryMgr.getDao();
		List<CategoryDTO> topList = null;
		
		topList = cateMgr.getTopcateList();
		
		request.setAttribute("topList", topList);
		
		return "/admin/cateList.jsp";
	}
}
