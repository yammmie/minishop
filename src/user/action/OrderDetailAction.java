package user.action;

import javax.servlet.http.*;
import shop.action.*;
import user.*;
import member.*;
import java.util.*;

public class OrderDetailAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		int ordno = Integer.parseInt(request.getParameter("ordno"));
		ProductMgr proMgr = ProductMgr.getDao();
		MemberDAO memDao = MemberDAO.getInstance();
		OrderMgr ordMgr = new OrderMgr();
		
		Vector<OrderDTO> vec = ordMgr.getOrderDetail(ordno);
		OrderDTO ordDto = (OrderDTO)vec.get(0);
		MemberDTO memDto = memDao.getMember(ordDto.getId());
		
		int totalPrice = 0;
		
		request.setAttribute("proMgr", proMgr);
		request.setAttribute("ordDto", ordDto);
		request.setAttribute("memDto", memDto);
		request.setAttribute("vec", vec);
		request.setAttribute("totalPrice", totalPrice);
		
		return "/user/orderDetail.jsp";
	}
}
