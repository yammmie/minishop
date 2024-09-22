package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import member.*;

public class ModifyAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String id = (String)request.getSession().getAttribute("id");
		MemberDAO memDao = MemberDAO.getInstance();
		MemberDTO memDto = memDao.getMember(id);
		
		String tel = memDto.getTel();

		String tel1 = tel.substring(0, 3); 
		String tel2 = tel.substring(4, 8);
		String tel3 = tel.substring(9, 13);
		
		request.setAttribute("memDto", memDto);
		request.setAttribute("tel1", new Integer(tel1));
		request.setAttribute("tel2", new Integer(tel2));
		request.setAttribute("tel3", new Integer(tel3));
		
		return "/admin/modify.jsp";
	}
}
