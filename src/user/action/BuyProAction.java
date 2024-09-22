package user.action;

import java.util.*;

import javax.servlet.http.*;

import shop.action.*;
import user.*;

public class BuyProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		OrderMgr ordMgr = OrderMgr.getDao();
		OrderDTO ordDto = null;
		CartMgr cartMgr = CartMgr.getDao();
		List<CartDTO> cartList = null;
		CartDTO cartDto = new CartDTO();
		ProductMgr proMgr = ProductMgr.getDao();
		
		String before = request.getParameter("before");
		String id = (String)request.getSession().getAttribute("id");
		String payment = request.getParameter("payment");
		int ordno = ordMgr.getOrdno()+1;
		
		if(before == null) { // 제품 여러개 구입한 경우 (cartList)
			cartList = cartMgr.getCartList(id);
			
			for(int i=0; i<cartList.size(); i++) {
				cartDto = (CartDTO)cartList.get(i);
				ordDto = new OrderDTO();

				ordDto.setOrdno(ordno);
				ordDto.setPro_no(cartDto.getPro_no());
				ordDto.setQuantity(Integer.parseInt(cartDto.getQuantity()));
				ordDto.setId(id);
				ordDto.setColor(cartDto.getColor());
				ordDto.setSize(cartDto.getSize());
				ordDto.setPayment(payment);
				
				ordMgr.insertOrder(ordDto);
				
				proMgr.reduceProduct(Integer.parseInt(cartDto.getQuantity()), cartDto.getPro_no(), cartDto.getColor(), cartDto.getSize());	
			}
			
			cartMgr.deleteAllCart();
		} else if(before != null) { // 제품 1개만 구입한 경우 (productDetail)
			int pro_no = Integer.parseInt(request.getParameter("pro_no"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			String color = request.getParameter("color");
			String size = request.getParameter("size");
			
			ordDto = new OrderDTO();
			
			ordDto.setOrdno(ordno);
			ordDto.setPro_no(pro_no);
			ordDto.setQuantity(quantity);
			ordDto.setId(id);
			ordDto.setColor(color);
			ordDto.setSize(size);
			ordDto.setPayment(payment);
			
			ordMgr.insertOrder(ordDto);
			
			proMgr.reduceProduct(quantity, pro_no, color, size);
		}
		
		return "/user/buyPro.jsp";
	}
}
