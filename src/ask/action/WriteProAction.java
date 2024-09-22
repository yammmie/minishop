package ask.action;

import javax.servlet.http.*;
import ask.*;
import shop.action.*;

public class WriteProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");//post��û ���ڵ� ó��

		String pageNum=request.getParameter("pageNum");
		AskDTO dto=new AskDTO();
		AskDAO dao=AskDAO.getDao();//dao��ü ���
		
		dto.setNum(Integer.parseInt(request.getParameter("num")));
		dto.setName(request.getParameter("name"));
		dto.setSubject(request.getParameter("subject"));
		dto.setPasswd(request.getParameter("passwd"));
		dto.setRef(Integer.parseInt(request.getParameter("ref")));
		dto.setRe_step(Integer.parseInt(request.getParameter("re_step")));
		dto.setRe_level(Integer.parseInt(request.getParameter("re_level")));
		dto.setContent(request.getParameter("content"));
		
		dao.insertArticle(dto);//dao�޼ҵ� ȣ��
		
		request.setAttribute("pageNum", pageNum);	
		
		return "/ask/writePro.jsp";
	}
}
