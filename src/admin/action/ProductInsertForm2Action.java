package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;

public class ProductInsertForm2Action implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		ProductMgr proMgr = ProductMgr.getDao();
		
		int pro_no = Integer.parseInt(request.getParameter("pro_no"));
		String name = proMgr.getProductName(pro_no);
		
		request.setAttribute("pro_no", pro_no);
		request.setAttribute("name", name);
		
		return "/admin/productInsertForm2.jsp";
	}
}
