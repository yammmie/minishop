package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;

public class ProductInsertPro2Action implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		int pro_no = Integer.parseInt(request.getParameter("pro_no"));
		ProductDTO proDto = new ProductDTO();
		
		proDto.setPro_no(pro_no);
		proDto.setName(request.getParameter("name"));
		proDto.setColor(request.getParameter("color"));
		proDto.setSize(request.getParameter("size"));
		proDto.setStock(request.getParameter("stock"));
		
		ProductMgr proMgr = ProductMgr.getDao();
		
		proMgr.insertStock(proDto);
		
		request.setAttribute("proDto", proDto);
		
		return "/admin/productInsertPro2.jsp";
	}
}
