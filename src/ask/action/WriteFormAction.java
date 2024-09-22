package ask.action;

import javax.servlet.http.*;
import shop.action.*;

public class WriteFormAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String pageNum="1";	
		int num=0;
		int ref=1;
		int re_step=0;
		int re_level=0;
		
		try{
			if(request.getParameter("num") != null){//답글이면
				pageNum=request.getParameter("pageNum");
				num=Integer.parseInt(request.getParameter("num"));
				ref=Integer.parseInt(request.getParameter("ref"));
				re_step=Integer.parseInt(request.getParameter("re_step"));
				re_level=Integer.parseInt(request.getParameter("re_level"));
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			System.out.println("AskAction 예외 :"+ex);
		}
		
		//해당 뷰에서 사용할 속성
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("num", num);
		request.setAttribute("ref", ref);
		request.setAttribute("re_step", re_step);
		request.setAttribute("re_level", re_level);
		
		return "/ask/writeForm.jsp";
	}
}
