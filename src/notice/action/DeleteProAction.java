package notice.action;

import javax.servlet.http.*;
import shop.action.*;
import notice.*;

public class DeleteProAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		NoticeDAO dao = NoticeDAO.getDao();
		dao.deleteArticle(num);
		
		request.setAttribute("pageNum", pageNum);
		
		return "/notice/deletePro.jsp";
	}
}
