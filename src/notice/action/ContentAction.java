package notice.action;

import javax.servlet.http.*;
import notice.*;
import shop.action.*;

public class ContentAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		NoticeDAO dao = NoticeDAO.getDao();
		NoticeDTO dto = dao.getArticle(num);
		
		String content = dto.getContent();
		content = content.replace("\n", "<br>");
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("content", content);
		
		request.setAttribute("dto", dto);
		
		return "/notice/content.jsp";
	}
}
