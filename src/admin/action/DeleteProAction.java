package admin.action;

import javax.servlet.http.*;
import member.*;
import shop.action.*;

public class DeleteProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");

		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		
		MemberDAO memDao = MemberDAO.getInstance();
		int check = memDao.deleteMember(id, passwd);
		
		request.setAttribute("check", new Integer(check));
		request.setAttribute("id", id);
		
		return "/admin/deletePro.jsp";
	}
}
