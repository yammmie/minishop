package ask.action;

import javax.servlet.http.*;
import ask.*;
import shop.action.*;

public class UpdateFormAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
request.setCharacterEncoding("utf-8");
		
		int num=Integer.parseInt(request.getParameter("num"));
		String pageNum=request.getParameter("pageNum");
		
		AskDAO dao=AskDAO.getDao();//dao°´Ã¼ ¾ò±â
		AskDTO dto=dao.getUpdateArticle(num);//°´Ã¼.¸Þ¼Òµå
		
		request.setAttribute("num", new Integer(num));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("dto", dto);
		
		return "/ask/updateForm.jsp";
	}
}
