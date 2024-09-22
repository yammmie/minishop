package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import java.util.*;
import user.*;

public class AllOrderListAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		OrderMgr ordMgr = OrderMgr.getDao();
		Vector<OrderDTO> ordList = ordMgr.getOrderList();
		ProductMgr proMgr = ProductMgr.getDao();
		
		if(ordList.size() > 0) {
			int totalPrice = 0;
			int cnt = 0;
			int cnt2 = 1;
			
			Vector<OrderDTO> cntvec = ordMgr.getAllOrdnoCount(); // ordno, orddate, count(*) ���� �ֹ���ȣ ����
			OrderDTO cntDto = (OrderDTO)cntvec.get(cnt); // �ֱٿ� ��Ų ��(get(0))�� �̸� ������
			
			request.setAttribute("proMgr", proMgr);
			request.setAttribute("cnt", cnt);
			request.setAttribute("cnt2", cnt2);
			request.setAttribute("cntvec", cntvec);
			request.setAttribute("cntDto", cntDto);
			request.setAttribute("totalPrice", totalPrice);
		}
		
		request.setAttribute("ordList", ordList);
		
		return "/admin/allOrderList.jsp";
	}
}
