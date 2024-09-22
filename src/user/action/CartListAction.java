package user.action;

import javax.servlet.http.*;

import shop.action.*;
import user.*;

import java.util.*;

public class CartListAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		CartMgr cartMgr = CartMgr.getDao();
		String id = (String)request.getSession().getAttribute("id");
		List<CartDTO> cartList = new ArrayList<CartDTO>(); 
		cartList = cartMgr.getCartList(id);
		
		request.setAttribute("cartList", cartList);
		request.setAttribute("id", id);
		
		return "/user/cartList.jsp";
	}
}