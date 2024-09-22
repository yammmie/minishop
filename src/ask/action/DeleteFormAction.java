package ask.action;

import javax.servlet.http.*;
import shop.action.*;

public class DeleteFormAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String pageNum=request.getParameter("pageNum");
		int num=Integer.parseInt(request.getParameter("num"));
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("num",new Integer(num));
		
		return "/ask/deleteForm.jsp";
	}
}
