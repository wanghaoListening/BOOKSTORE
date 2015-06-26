package bookstore.book.servlet.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import bookstore.book.domain.Book;
import bookstore.book.service.BookService;
import bookstore.category.domain.Category;
import bookstore.category.service.CategoryService;

public class AdminBookServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BookService bs = new BookService();
	private CategoryService cs = new CategoryService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("findAllBooks")){
			findAllBooks(request, response);
		}else if(method.equals("getBook")){
			getBook(request, response);
		}else if(method.equals("addBooks")){
			addBooks(request, response);
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("delete")){
			delete(request, response);
		}else if(method.equals("modify")){
			modify(request, response);
		}else if(method.equals("getBook")){
			getBook(request, response);
		}
		
	}
	
	public void findAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Book> books = bs.findAllBooks();
		request.setAttribute("books", books);
		request.getRequestDispatcher("/adminjsps/admin/book/list.jsp").forward(request, response);
	}
	
	public void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String bid = request.getParameter("bid");
		Book book = bs.findBookByBid(bid);
		List<Category> categies = cs.findAllCategory();
		request.setAttribute("book", book);
		request.setAttribute("categies", categies);
		request.getRequestDispatcher("/adminjsps/admin/book/desc.jsp").forward(request, response);
	}
	
	public void addBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Category> categies = cs.findAllCategory();
		request.setAttribute("categies", categies);
		request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String bid = request.getParameter("bid");
		bs.delete(bid);
		this.findAllBooks(request, response);
	}
	//对书籍进行编辑1.先删除后添加
	public void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Map<String,String[]> map = request.getParameterMap();
		Book book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class);
		String price = request.getParameter("price");
		double newPrice = Double.parseDouble(price);
		book.setCategory(category);
		book.setPrice(newPrice);
		bs.modify(book);
		this.findAllBooks(request, response);
	}
}
