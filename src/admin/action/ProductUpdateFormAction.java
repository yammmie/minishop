package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;

public class ProductUpdateFormAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		ProductMgr proMgr = ProductMgr.getDao();
		int pro_no = Integer.parseInt(request.getParameter("pro_no"));
		String size = request.getParameter("size");
		String color = request.getParameter("color");
		ProductDTO proDto = proMgr.getProduct(pro_no, color, size);
		
		request.setAttribute("proDto", proDto);
		
		return "/admin/productUpdateForm.jsp";
	}
}
