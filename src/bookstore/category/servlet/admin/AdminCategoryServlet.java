package bookstore.category.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import bookstore.category.domain.Category;
import bookstore.category.service.CategoryService;

public class AdminCategoryServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private CategoryService cs = new CategoryService();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("findAll")){
			findAll(request, response);
		}else if(method.equals("mod")){
			mod(request, response);
		}else if(method.equals("del")){
			del(request, response);
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("add")){
			add(request, response);
		}else if(method.equals("edit")){
			edit(request, response);
		}
		
	}
	//查找所有分类
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Category> categies = cs.findAllCategory();
		request.setAttribute("categies", categies);
		request.getRequestDispatcher("/adminjsps/admin/category/list.jsp").forward(request, response);
	}
	//对分类进行修改
	public void mod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String cid = request.getParameter("cid");
		Category category = cs.load(cid);
		request.setAttribute("category", category);
		request.getRequestDispatcher("/adminjsps/admin/category/mod.jsp").forward(request, response);
		
	}
	//对指定的分类进行删除
	
	public void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String cid = request.getParameter("cid");
		cs.delCategory(cid);
		this.findAll(request, response);
		//request.getRequestDispatcher("/adminjsps/admin/category/list.jsp").forward(request, response);
	}
	//添加分类
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String cname = request.getParameter("cname");
		String cid = CommonUtils.uuid();
		cs.addCategory(cname,cid);
		this.findAll(request, response);
	}
	
	public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String cname = request.getParameter("cname");
		String cid = request.getParameter("cid");
		cs.edit(cname,cid);
		this.findAll(request, response);
	}
	
}
