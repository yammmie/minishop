package user.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;
import java.util.*;

public class ProductDetailProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");

		int pro_no = Integer.parseInt(request.getParameter("pro_no"));
		String color = request.getParameter("color");
		
		ProductMgr proMgr = ProductMgr.getDao();
		int cnt = proMgr.getCountStock(pro_no, color);
		List<ProductDTO> stockList = proMgr.getProductStock(pro_no, color);
		
		String size[] = new String[cnt];
		String stock[] = new String[cnt];
		
		for(int i=0; i<stockList.size(); i++) {
			ProductDTO stockDto = (ProductDTO)stockList.get(i);
			
			size[i] = stockDto.getSize();
			stock[i] = stockDto.getStock();
		}
		
		request.setAttribute("size", size);
		request.setAttribute("stock", stock);
		
		return "/user/productDetailPro.jsp";
	}
}
