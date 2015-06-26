package bookstore.user.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.user.service.UserException;
import bookstore.user.service.UserService;

public class ActiveServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService us = new UserService();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String code = request.getParameter("code");
		try {
			us.active(code);
			request.setAttribute("msg", "恭喜你激活成功!!请登录");
			
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			
		}
		request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
		
	}

}
