package user.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;

public class InsertCartProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		int pro_no = Integer.parseInt(request.getParameter("pro_no"));
		String color = request.getParameter("sel_color");
		String size = request.getParameter("sel_size");
		String quantity = request.getParameter("quantity");
		String id = request.getParameter("id");
		
		ProductMgr proMgr = ProductMgr.getDao();
		ProductDTO proDto = proMgr.getProduct(pro_no, size, color);
		
		String name = proDto.getName();
		String image = proDto.getImage();
		int price = proDto.getPrice();
		
		CartMgr cartMgr = CartMgr.getDao();
		CartDTO cartDto = new CartDTO();
		
		cartDto.setPro_no(pro_no);
		cartDto.setName(name);
		cartDto.setQuantity(quantity);
		cartDto.setImage(image);
		cartDto.setPrice(price);
		cartDto.setColor(color);
		cartDto.setSize(size);
		cartDto.setId(id);
		
		cartMgr.insertCart(cartDto);
		
		return "/user/insertCartPro.jsp";
	}
}