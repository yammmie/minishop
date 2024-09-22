package notice.action;

import javax.servlet.http.*;
import shop.action.*;
import notice.*;
import java.util.*;

public class ListAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String pageNum = request.getParameter("pageNum");
		
		if(pageNum == null)
			pageNum = "1";
		
		int currentPage = Integer.parseInt(pageNum);
		int pageSize = 10;
		int startRow = (currentPage-1)*pageSize+1;
		int endRow = currentPage*pageSize;
		int count = 0;
		int number = 0;
		int pageBlock = 10;
		List noticeList = null;
		
		NoticeDAO dao = NoticeDAO.getDao();
		count = dao.getArticleCount();
		
		if(count > 0)
			noticeList = dao.getList(startRow, pageSize);
		
		number = count-(currentPage-1)*pageSize;
		int pageCount = count/pageSize+(count%pageSize==0?0:1);
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("startRow", startRow);
		request.setAttribute("endRow", endRow);
		request.setAttribute("count", count);
		request.setAttribute("number", number);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("pageCount", pageCount);
		
		request.setAttribute("noticeList", noticeList);
		
		return "/notice/list.jsp";
	}
}
