package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;

public class ProductUpdateProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		ProductMgr proMgr = ProductMgr.getDao();
		
		proMgr.updateProduct(request);
		
		return "/admin/productUpdatePro.jsp";
	}
}
