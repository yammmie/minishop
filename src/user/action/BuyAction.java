package user.action;

import javax.servlet.http.*;

import shop.action.*;
import user.*;
import member.*;

import java.util.*;

public class BuyAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String before = request.getParameter("before");
		List<CartDTO> cartList = new ArrayList<CartDTO>();
		String id = request.getParameter("id");
		ProductMgr proMgr = ProductMgr.getDao();
		ProductDTO proDto = null;
		
		if(before == null) { // cartList에서 넘어온 경우
			CartMgr cartMgr = CartMgr.getDao();
			cartList = cartMgr.getCartList(id);
			
			request.setAttribute("cartList", cartList);
		} else if(before != null) { // productDetail에서 넘어온 경우
			int pro_no = Integer.parseInt(request.getParameter("pro_no"));
			String color = request.getParameter("sel_color");
			String size = request.getParameter("sel_size");
			int quantity = Integer.parseInt(request.getParameter("quantity"));
						
			proDto = proMgr.getProduct(pro_no, color, size);
	
			int stock = proMgr.getMaxReduce(pro_no, color, size);
		
			request.setAttribute("pro_no", pro_no);
			request.setAttribute("color", color);
			request.setAttribute("size", size);
			request.setAttribute("quantity", quantity);
			request.setAttribute("proDto", proDto);
			request.setAttribute("stock", stock);
		}

		MemberDAO memDao = MemberDAO.getInstance();
		MemberDTO memDto = memDao.getMember(id);
		
		request.setAttribute("memDto", memDto);
		request.setAttribute("before", before);
		request.setAttribute("id", id);
		request.setAttribute("proMgr", proMgr);
		
		return "/user/buy.jsp";
	}
}
