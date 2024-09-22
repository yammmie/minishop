package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;

public class ProductDeleteProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		ProductMgr proMgr = ProductMgr.getDao();
		int pro_no = Integer.parseInt(request.getParameter("pro_no"));
		String size = request.getParameter("size");
		String color = request.getParameter("color");
		
		proMgr.deleteProduct(request, pro_no, size, color);		
		
		return "/admin/productDeletePro.jsp";
	}
}
