package admin.action;

import javax.servlet.http.*;
import shop.action.*;

public class ConfirmPwAction implements CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
				
		return "/admin/confirmPw.jsp";
	}
}
