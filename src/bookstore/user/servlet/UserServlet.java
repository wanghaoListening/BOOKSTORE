package bookstore.user.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.user.domain.User;
import bookstore.user.service.UserException;
import bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;

public class UserServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService us = new UserService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
		//创建map用于保存错误信息
		Map<String,String> errors = new HashMap<String,String>();
		String username = form.getUsername();
		if(username==null||username.trim().isEmpty()){
			errors.put("username", "用户名不能为空");
		}else if(username.length()<3||username.length()>10){
			errors.put("username", "用户名的长度在3-10为之间");
		}
		String password = form.getPassword();
		if(password==null||password.trim().isEmpty()){
			errors.put("password", "密码不能为空");
		}else if(password.length()<5||password.length()>15){
			errors.put("password", "密码的长度在5-15为之间");
		}
		String email = form.getEmail();
		if(email==null||email.trim().isEmpty()){
			errors.put("email", "邮箱不能为空");
		}else if(!ValidateStore.emaliValidate(email)){
			errors.put("email", "邮箱的格式不正确！！请确认邮箱 ");
		}
		String Verify = request.getParameter("Verify");
		String random = (String) request.getSession().getAttribute("verifycode");
		if(email==null||email.trim().isEmpty()){
			errors.put("Verify", "验证码不能为空");
		}else if(!Verify.equalsIgnoreCase(random)){
			errors.put("Verify", "验证码输入不正确 ");
		}
		if(errors.size()>0){
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			String address= "/jsps/user/regist.jsp";
			requestForword(request, response, address);
			//request.getRequestDispatcher("/jsps/user/regist.jsp").forward(request, response);
			return;
		}
		
		try{
			us.regist(form);
			
			request.setAttribute("msg","恭喜你注册成功！！请尽快去邮箱激活");
			
		}catch(UserException e){
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			String address= "/jsps/msg.jsp";
			requestForword(request, response, address);
			return;
		}
		/*
		 * 发邮件
		 * 
		 * */
		ValidateStore.sendEmail(form.getEmail(),form.getCode());
		String address= "/jsps/msg.jsp";
		requestForword(request, response, address);
	}
	
	

	public void requestForword(HttpServletRequest request,HttpServletResponse response,String address) throws ServletException, IOException{
		request.getRequestDispatcher(address).forward(request, response);

	}

}
