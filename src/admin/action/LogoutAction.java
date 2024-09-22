package admin.action;

import javax.servlet.http.*;
import shop.action.*;

public class LogoutAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
				
		return "/admin/logout.jsp";
	}
}
