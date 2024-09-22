package ask.action;

import javax.servlet.http.*;
import ask.*;
import shop.action.*;

public class UpdateProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		int num=Integer.parseInt(request.getParameter("num"));
		String pageNum=request.getParameter("pageNum");
		String passwd_in=request.getParameter("passwd");
		
		AskDAO dao=AskDAO.getDao();//dao��ü ���
		AskDTO dto=dao.getUpdateArticle(num);//��ü.�޼ҵ�
		
		dto.setName(request.getParameter("name"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		dto.setPasswd(passwd_in);
		
		int check=dao.updateArticle(dto);//dao�޼ҵ� ȣ��
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("check", check);
		
		return "/ask/updatePro.jsp";
	}
}
