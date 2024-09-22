package notice.action;

import javax.servlet.http.*;
import shop.action.*;
import notice.*;

public class WriteProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		NoticeDTO dto = new NoticeDTO();
		
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		
		NoticeDAO dao = NoticeDAO.getDao();
		
		dao.insertArticle(dto);
		
		return "/notice/writePro.jsp";
	}
}
