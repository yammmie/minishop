package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;

public class ProductInsertProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		ProductMgr proMgr = ProductMgr.getDao();
		int pro_no_re = proMgr.insertProduct(request);
		
		request.setAttribute("pro_no_re", pro_no_re);
		
		return "/admin/productInsertPro.jsp";
	}
}
