package bookstore.Cart.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bookstore.Cart.domain.Cart;
import bookstore.Cart.domain.CartItem;
import bookstore.book.domain.Book;
import bookstore.book.service.BookService;

public class CartServlet extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("clearItem")){
			clearItem(request, response);
		}else if(method.equals("deleteItem")){
			deleteItem(request, response);
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		
		if(method.equals("addItem")){
			addItem(request, response);
		}
	}
	//清空购物车
	public void clearItem(HttpServletRequest request, HttpServletResponse response){
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clearItem();
		try {
			request.getRequestDispatcher("/jsps/cart/list.jsp").forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException("/jsps/cart/list.jsp网络跳转异常");
		} 
	}
	//添加购物条目
	public void addItem(HttpServletRequest request, HttpServletResponse response){
		/*1,得到车
		 * 2.得到商品及其数量，
		 * 先得到图书的bid，然后我们需要通过bid到数据库中查询书
		 * 3,把条目添加到车中
		 * */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		Book book = new BookService().findBookByBid(bid);
		int count = Integer.parseInt(request.getParameter("count"));
		
		//得到条目
		CartItem item = new CartItem();
		item.setBook(book);
		item.setCount(count);
		cart.addItem(item);
		request.getSession().setAttribute("cart", cart);
		try {
			request.getRequestDispatcher("/jsps/cart/list.jsp").forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException("/jsps/cart/list.jsp网络跳转异常");
		} 
	}
	
	//删除指定的购物条目
	public void deleteItem(HttpServletRequest request, HttpServletResponse response){
		String bid = request.getParameter("bid");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.deleteItem(bid);
		try {
			request.getRequestDispatcher("/jsps/cart/list.jsp").forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException("/jsps/cart/list.jsp网络跳转异常");
		} 
	}
	
	
}
