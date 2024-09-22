package ask.action;

import javax.servlet.http.*;
import ask.*;
import shop.action.*;

public class DeleteProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int num=Integer.parseInt(request.getParameter("num"));
		String pageNum=request.getParameter("pageNum");
		String passwd=request.getParameter("passwd");
		
		AskDAO dao=AskDAO.getDao();//dao°´Ã¼ ¾ò±â
		int check=dao.deleteArticle(num, passwd);
		
		request.setAttribute("pageNum", pageNum);		
		request.setAttribute("check", check);
		
		return "/ask/deletePro.jsp";
	}
}
