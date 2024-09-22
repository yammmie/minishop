package notice.action;

import javax.servlet.http.*;
import shop.action.*;
import notice.*;

public class UpdateFormAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");

		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		NoticeDAO dao = NoticeDAO.getDao();
		NoticeDTO dto = dao.getUpdateNotice(num);
		
		request.setAttribute("num", num);
		request.setAttribute("pageNum", pageNum);
		
		request.setAttribute("dto", dto);
		
		return "/notice/updateForm.jsp";
	}
}
