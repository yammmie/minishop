package ask.action;

import javax.servlet.http.*;
import ask.*;
import shop.action.*;
import java.util.*;

public class ListAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String pageNum=request.getParameter("pageNum");
		
		if(pageNum==null)
			pageNum="1";
		
		int pageSize=10;
		int currentPage=Integer.parseInt(pageNum);
		
		int startRow=(currentPage-1)*pageSize+1;
		int endRow=currentPage*pageSize;
		
		int count=0;
		int number=0;
		int pageBlock=10;
		
		List<AskDTO> articleList=null;
		AskDAO dao=AskDAO.getDao();
		count=dao.getArticleCount();
		
		if(count>0)
			articleList=dao.getList(startRow, pageSize);
		else
			articleList=Collections.EMPTY_LIST;
		
		number=count-(currentPage-1)*pageSize;//글목록에 표시
		int pageCount=count/pageSize+(count%pageSize==0?0:1);
		
		request.setAttribute("currentPage", new Integer(currentPage));
		request.setAttribute("startRow", new Integer(startRow));
		request.setAttribute("endRow", new Integer(endRow));
		request.setAttribute("pageBlock", new Integer(pageBlock));
		request.setAttribute("pageCount", new Integer(pageCount));
		request.setAttribute("count", new Integer(count));
		request.setAttribute("pageSize", new Integer(pageSize));
		request.setAttribute("number", new Integer(number));
		request.setAttribute("articleList", articleList);
		
		return "/ask/list.jsp";
	}
}
