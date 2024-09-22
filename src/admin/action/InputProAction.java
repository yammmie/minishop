package admin.action;

import javax.servlet.http.*;
import shop.action.*;
import member.*;

public class InputProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		MemberDTO memDto = new MemberDTO();
		MemberDAO memDao = MemberDAO.getInstance();

		String tel1=request.getParameter("tel1");
		String tel2=request.getParameter("tel2");
		String tel3=request.getParameter("tel3");
		String tel=tel1+"-"+tel2+"-"+tel3;
		
		String id = request.getParameter("id");
		
		memDto.setId(id);
		memDto.setName(request.getParameter("name"));
		memDto.setPasswd(request.getParameter("passwd"));
		memDto.setAddr1(request.getParameter("addr1"));
		memDto.setAddr2(request.getParameter("addr2"));
		memDto.setEmail1(request.getParameter("email1"));
		memDto.setEmail2(request.getParameter("email2"));
		memDto.setZipcode(request.getParameter("zipcode"));
		memDto.setTel(tel);
		
		memDao.insertMember(memDto);
		
		request.setAttribute("id", id);
		
		return "/admin/inputPro.jsp";
	}
}
