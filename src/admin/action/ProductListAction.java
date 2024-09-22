package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;
import java.util.*;

public class ProductListAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		ProductMgr proMgr = ProductMgr.getDao();
		List<ProductDTO> goodList = proMgr.getAdminGoodList();
		
		request.setAttribute("goodList", goodList);
		
		return "/admin/productList.jsp";
	}
}
