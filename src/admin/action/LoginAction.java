package admin.action;

import javax.servlet.http.*;
import shop.action.*;

public class LoginAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
				
		return "/admin/login.jsp";
	}
}
