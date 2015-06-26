package bookstore.Order.servlet;

import java.io.IOException;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.Cart.domain.Cart;
import bookstore.Cart.domain.CartItem;
import bookstore.Order.domain.Order;
import bookstore.Order.domain.OrderItem;
import bookstore.Order.service.OrderException;
import bookstore.Order.service.OrderService;
import bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;

public class OrderServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderService os = new OrderService();
	/*1.添加订单
	 * 2.把session中的车用来生产order对象
	 * 
	 * */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("createOrder")){
			createOrder(request, response);
		}else if(method.equals("userOrder")){
			userOrder(request, response);
		}else if(method.equals("loadOrder")){
			loadOrder(request, response);
		}else if(method.equals("ConfirmOrder")){
			ConfirmOrder(request, response);
		}else if(method.equals("back")){
			
		}
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("pay")){
			pay(request, response);
		}
		
	}
	
	public void createOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		//1.从session中把车取出
				Cart cart = (Cart) request.getSession().getAttribute("cart");
				//2.创建订单
				Order or = new Order();
				or.setOid(CommonUtils.uuid());//uuid生成订单编号
				or.setOrderTime(new Date());//系统时间为当前下单时间
				or.setState(1);//设置单子的状态为未付款
				or.setTotal(cart.getTotal());//从购物车中得到所有商品的价格总和
				
				User user = (User) request.getSession().getAttribute("user");
				//当前登陆的用户即为下单的用户
				or.setOwner(user);
				List<OrderItem> orderitems = new ArrayList<OrderItem>();
				for(CartItem cartitem : cart.getAllItem()){
					OrderItem orderitem = new OrderItem();//创建订单条目对象
					
					orderitem.setIid(CommonUtils.uuid());
					orderitem.setCount(cartitem.getCount());
					orderitem.setTotal(cartitem.getTatalPrice());
					orderitem.setBook(cartitem.getBook());
					orderitem.setOrder(or);
					orderitems.add(orderitem);
				}
				or.setOrderitems(orderitems);//把所有订单条目添加到订单中
				cart.clearItem();//清空购物车里的订单
				//存储订单到数据库
				  os.createOrder(or);
				
				//保存order到request域中，转发到order、desc.jsp
				  request.setAttribute("order", or);
				  request.getRequestDispatcher("/jsps/order/desc.jsp").forward(request, response);;
	}
	
	public void userOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		User user = (User) request.getSession().getAttribute("user");
		String uid = user.getUid();
		List<Order> orders = os.findOrdersByUid(uid);
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/jsps/order/list.jsp").forward(request, response);
	}
	
	public void loadOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String oid = request.getParameter("oid");
		Order order = os.findOrderByOid(oid);
		request.setAttribute("order", order);
		request.getRequestDispatcher("/jsps/order/desc.jsp").forward(request, response);
	}
	
	public void ConfirmOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		try {
			String oid = request.getParameter("oid");
			os.ConfirmState(oid);
			request.setAttribute("msg", "确认成功，交易完成");
		} catch (OrderException e) {
			request.setAttribute("msg", e.getMessage());
			
		}finally{
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
		}
	}
	//易宝支付相关操作
	public void pay(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Properties props = new Properties();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("merchantInfo.properties");
		props.load(input);
		/*
		 * 准备13参数
		 */
		String p0_Cmd = "Buy";
		String p1_MerId = props.getProperty("p1_MerId"); //商户编号
		String p2_Order = request.getParameter("oid");// 商户订单号
		String p3_Amt = "0.01";// 支付金额
		String p4_Cur = "CNY";//交易币种
		String p5_Pid = "";//商品名称
		String p6_Pcat = "";// 商品种类
		String p7_Pdesc = "";//商品描述
		String p8_Url = props.getProperty("p8_Url");// 商户接收支付成功数据的地址
		String p9_SAF = "";//送货地址
		String pa_MP = "";//商户扩展信息
		String pd_FrpId = request.getParameter("pd_FrpId");// 支付通道编码
		String pr_NeedResponse = "1";//应答机制

		/*
		 * 计算hmac
		 */
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);

		/*
		 * 连接易宝的网址和13+1个参数
		 */
		StringBuilder url = new StringBuilder(props.getProperty("url"));
		url.append("?p0_Cmd=").append(p0_Cmd);
		url.append("&p1_MerId=").append(p1_MerId);
		url.append("&p2_Order=").append(p2_Order);
		url.append("&p3_Amt=").append(p3_Amt);
		url.append("&p4_Cur=").append(p4_Cur);
		url.append("&p5_Pid=").append(p5_Pid);
		url.append("&p6_Pcat=").append(p6_Pcat);
		url.append("&p7_Pdesc=").append(p7_Pdesc);
		url.append("&p8_Url=").append(p8_Url);
		url.append("&p9_SAF=").append(p9_SAF);
		url.append("&pa_MP=").append(pa_MP);
		url.append("&pd_FrpId=").append(pd_FrpId);
		url.append("&pr_NeedResponse=").append(pr_NeedResponse);
		url.append("&hmac=").append(hmac);

		System.out.println(url);

		/*
		 * 重定向到易宝
		 */
		response.sendRedirect(url.toString());
	}
	
	public void back(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		/*
		 * 1. 获取11 + 1
		 */
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");

		String hmac = request.getParameter("hmac");

		/*
		 * 2. 校验访问者是否为易宝！
		 */
		Properties props = new Properties();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("merchantInfo.properties");
		props.load(input);
		String keyValue = props.getProperty("keyValue");

		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		
		if(!bool) {//如果校验失败
			request.setAttribute("msg", " 休想钻空子！");
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
		}
		
		os.pay(r6_Order);//有可能对数据库进行操作也可能不进行操作
		/*
		 * 判断当前浏览器的回调方式
		 * */
		if(r9_BType.equals("2")){
			response.getWriter().write("success");
		}
		
		/*
		 * 保存成功信息转发到msg。JSP
		 * 
		 * */
		
		request.setAttribute("msg","支付成功,请等待卖家发货");
		request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
	}

}
