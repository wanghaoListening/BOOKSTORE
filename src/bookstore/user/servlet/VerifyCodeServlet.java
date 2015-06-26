package bookstore.user.servlet;


import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.itcast.vcode.utils.VerifyCode;
public class VerifyCodeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		VerifyCode vc = new VerifyCode();
		BufferedImage bi = vc.getImage();
		String randomCode = vc.getText();
		request.getSession().setAttribute("verifycode", randomCode);
		VerifyCode.output(bi, response.getOutputStream());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
