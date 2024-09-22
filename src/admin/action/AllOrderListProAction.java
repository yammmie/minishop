package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import user.OrderMgr;

public class AllOrderListProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String state = request.getParameter("sel_state");
		int ordno = Integer.parseInt(request.getParameter("ordno"));
		
		OrderMgr ordMgr = OrderMgr.getDao();
		ordMgr.updateState(ordno, state);

		return "/admin/allOrderListPro.jsp";
	}
}
