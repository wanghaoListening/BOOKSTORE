package bookstore.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.Cart.domain.Cart;
import bookstore.user.domain.User;
import bookstore.user.service.UserException;
import bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService us = new UserService();
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession hs = request.getSession();
		Integer count = (Integer) hs.getAttribute("count");
		if(count==null){
			count=1;
			System.out.println(count+"null");
			hs.setAttribute("count", count);
			
		}
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		PrintWriter pw = response.getWriter();
		List<String> errors = new ArrayList<String>();
		if(count==1){
			if(form.getUsername()==null||form.getUsername().trim().isEmpty()){
				System.out.println("ppp");
				pw.print("1用户名不能为空");
				return;

			}

			try{
				pw.print("1 ");
				@SuppressWarnings("unused")
				User user = us.validate(form);


			}catch(UserException e){
				System.out.println(e.getMessage());
				pw.print(e.getMessage());
				return;
			}
		}
		String flag = request.getParameter("flag");
		if(flag!=null){
			if(form.getPassword()==null||form.getPassword().trim().isEmpty()){
				if(errors.size()==0)
					pw.print("2密码不能为空");

			}
		}
		String vf = request.getParameter("vf");
		if(vf!=null){
			String Verify = request.getParameter("Verify");
			String random = (String) request.getSession().getAttribute("verifycode");
			hs.removeAttribute("count");
			if(Verify==null||Verify.trim().isEmpty()){
				if(errors.size()==0)
					pw.print("3验证码不能为空");

			}else if(!Verify.equalsIgnoreCase(random)){
				if(errors.size()==0)
					pw.print("3验证码填写错误");
			}

			try {

				User user = us.login(form);
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("cart", new Cart());
				//当用户登录成功后即可获得购物的权限。
				request.getRequestDispatcher("/index.jsp").forward(request, response);

			} catch (UserException e) {

				pw.print(e.getMessage());
			}
			request.setAttribute("form", form);
			request.getRequestDispatcher("/jsps/user/login.jsp").forward(request, response);
		}
	}





	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//注销用户
		request.getSession().invalidate();
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}


}
