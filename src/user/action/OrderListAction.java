package user.action;

import javax.servlet.http.*;

import shop.action.*;
import user.*;

import java.util.*;
import java.text.SimpleDateFormat;

public class OrderListAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String id = (String)request.getSession().getAttribute("id");
		ProductMgr proMgr = ProductMgr.getDao();
		OrderMgr ordMgr = OrderMgr.getDao();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String state = request.getParameter("state");
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		
		if(state == null || start_date == null || end_date == null) {
			state = "0";
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -3);
			
			start_date = sdf.format(cal.getTime());
			end_date = sdf.format(new Date());
		}
		
		Vector<OrderDTO> vec = ordMgr.getUserOrder(id, state, start_date, end_date);
		
		if(vec.size() > 0) {		
			int cnt = 0;
			int cnt2 = 1;
			
			Vector<OrderDTO> cntvec = ordMgr.getOrdnoCount(id);
			OrderDTO cntDto = (OrderDTO)cntvec.get(cnt);
					
			request.setAttribute("id", id);
			request.setAttribute("state", state);
			request.setAttribute("start_date", start_date);
			request.setAttribute("end_date", end_date);
			request.setAttribute("cnt", cnt);
			request.setAttribute("cnt2", cnt2);
			request.setAttribute("proMgr", proMgr);
			request.setAttribute("cntvec", cntvec);
			request.setAttribute("cntDto", cntDto);
		}

		request.setAttribute("vec", vec);
		
		return "/user/orderList.jsp";
	}
}
