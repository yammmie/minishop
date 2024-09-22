package admin.action;

import javax.servlet.http.*;
import member.*;
import shop.action.*;

public class ModifyProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String tel1 = request.getParameter("tel1");
		String tel2 = request.getParameter("tel2");
		String tel3 = request.getParameter("tel3");
		String tel = tel1+"-"+tel2+"-"+tel3;
		
		MemberDTO memDto = new MemberDTO();
		MemberDAO memDao = MemberDAO.getInstance();
		
		memDto.setId(request.getParameter("id"));
		memDto.setName(request.getParameter("name"));
		memDto.setPasswd(request.getParameter("passwd"));
		memDto.setAddr1(request.getParameter("addr1"));
		memDto.setAddr2(request.getParameter("addr2"));
		memDto.setEmail1(request.getParameter("email1"));
		memDto.setEmail2(request.getParameter("email2"));
		memDto.setZipcode(request.getParameter("zipcode"));
		memDto.setTel(tel);
		
		memDao.updateMember(memDto);
		
		return "/admin/modifyPro.jsp";
	}
}
