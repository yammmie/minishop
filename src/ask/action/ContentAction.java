package ask.action;

import javax.servlet.http.*;
import ask.*;
import shop.action.*;

public class ContentAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int num=Integer.parseInt(request.getParameter("num"));
		String pageNum=request.getParameter("pageNum");

		AskDAO dao=AskDAO.getDao();//dao객체 얻기
		AskDTO dto=dao.getArticle(num);//dao메소드 호출

		//글 내용
		String content=dto.getContent();
		content=content.replace("\n","<br>");
		
		request.setAttribute("content", content);
		
		int ref=dto.getRef();
		int re_step=dto.getRe_step();
		int re_level=dto.getRe_level();
		
		//해당 뷰에서 사용할 속성
		request.setAttribute("num", new Integer(num));
		request.setAttribute("pageNum", new Integer(pageNum));
		request.setAttribute("ref", ref);
		request.setAttribute("re_step", re_step);
		request.setAttribute("re_level", re_level);
		request.setAttribute("dto", dto);
		
		return "/ask/content.jsp";
	}
}
