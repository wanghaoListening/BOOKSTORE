package bookstore.book.servlet;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.book.domain.Book;
import bookstore.book.service.BookService;

public class BookServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BookService bs = new BookService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equalsIgnoreCase("findAllBooks")){
			findAllBooks(request, response);
		}else if(method.equalsIgnoreCase("findBooksBycategory")){
			findBooksBycategory(request, response);
		}else if(method.equals("findBookByBid")){
			findBookByBid(request, response);
		}
		
	}

	public void findAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Book> books = bs.findAllBooks();
		request.setAttribute("books",books );
		request.getRequestDispatcher("/jsps/book/list.jsp").forward(request, response);
	}
	
	public void findBooksBycategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String category = request.getParameter("category");
		//System.out.println("category"+category);
		List<Book> books = bs.findBooksByCid(category);
		request.setAttribute("books",books );
		request.getRequestDispatcher("/jsps/book/list.jsp").forward(request, response);
	}
	
	public void findBookByBid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String bid = request.getParameter("bid");
		Book book = bs.findBookByBid(bid);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/jsps/book/desc.jsp").forward(request, response);
	}
}
