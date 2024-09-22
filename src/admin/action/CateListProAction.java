package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import admin.*;
import java.util.*;

public class CateListProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");

		int topcate_idx = Integer.parseInt(request.getParameter("topcate_idx"));
		
		CategoryMgr cateMgr = CategoryMgr.getDao();
		List<CategoryDTO> subList = cateMgr.getSubcateList(topcate_idx);
		
		request.setAttribute("subList", subList);		
		
		return "/admin/cateListPro.jsp";
	}
}
