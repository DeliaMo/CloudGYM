package filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/protected_admin/*")
public class LoginFilterAdmin implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 【取得 session】
		HttpSession session = req.getSession();
		// 【從 session 判斷此user是否登入過】
	    Object adminVO = session.getAttribute("adminVO");                  // 從 session內取出 (key) account的值
		if (adminVO == null) {
			session.setAttribute("location", req.getRequestURI());       //*工作1 : 同時記下目前位置 , 以便於login.html登入成功後 , 能夠直接導至此網頁(須配合LoginHandler.java)
			res.sendRedirect(req.getContextPath() + "/html/login_admin.jsp");   //*工作2 : 請該user去登入網頁(login.html) , 進行登入
			return;
		} else {
			chain.doFilter(request, response);
		}
	}
}