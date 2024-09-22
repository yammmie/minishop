package user.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;

public class CartProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String flag = request.getParameter("flag");
		String quantity = request.getParameter("quantity");
		int num = Integer.parseInt(request.getParameter("num"));
		String id = request.getParameter("id");
		
		CartMgr cartMgr=CartMgr.getDao();
		
		if(flag.equals("update")) {
			int pro_no = Integer.parseInt(request.getParameter("pro_no"));
			String color = request.getParameter("color");
			String size = request.getParameter("size");
			
			ProductMgr proMgr = ProductMgr.getDao();
			int cnt = proMgr.getMaxReduce(pro_no, color, size);
			
			if(Integer.parseInt(quantity) <= cnt)
				cartMgr.updateCart(num, quantity);//장바구니 수정 메서드 호출
			
			else
				flag = "false";				
		}
		
		else if(flag.equals("delete"))
			cartMgr.deleteCart(num);
		
		request.setAttribute("flag", flag);
		request.setAttribute("id", id);
		
		return "/user/cartPro.jsp";
	}
}