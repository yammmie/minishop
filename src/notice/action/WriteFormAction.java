package notice.action;

import javax.servlet.http.*;
import shop.action.*;

public class WriteFormAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		
		return "/notice/writeForm.jsp";
	}
}
