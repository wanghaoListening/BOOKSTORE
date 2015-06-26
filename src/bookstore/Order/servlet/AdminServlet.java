package bookstore.Order.servlet;


import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bookstore.Order.domain.Order;
import bookstore.Order.service.OrderService;

public class AdminServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private OrderService os = new OrderService();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("findAllOrders")){
			findAllOrders(request, response);
		}else if(method.equals("findOrdersByState")){
			
			findOrdersByState(request, response);
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}
	
	public void findAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Order> orders = os.findAllOrders();
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/adminjsps/admin/order/list.jsp").forward(request, response);
	}
	
	public void findOrdersByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String state = request.getParameter("state");
		List<Order> orders = os.findOrdersByState(state);
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/adminjsps/admin/order/list.jsp").forward(request, response);
		
	}

}
