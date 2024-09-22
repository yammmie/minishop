package notice.action;

import javax.servlet.http.*;
import shop.action.*;
import notice.*;

public class UpdateProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		int num = Integer.parseInt(request.getParameter("num"));
		
		NoticeDTO dto = new NoticeDTO();
		
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		dto.setNum(num);
		
		NoticeDAO dao = NoticeDAO.getDao();
		dao.updateArticle(dto);
		
		request.setAttribute("pageNum", pageNum);
		
		return "/notice/updatePro.jsp";
	}
}
