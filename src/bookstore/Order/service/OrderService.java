package bookstore.Order.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.jdbc.JdbcUtils;
import bookstore.Order.dao.OrderDao;
import bookstore.Order.domain.Order;

public class OrderService {
	private OrderDao od = new OrderDao();
	/*
	 * 添加订单需要处理事务
	 * */
	public void createOrder(Order order){
		try{
			//1开启事务
			JdbcUtils.beginTransaction();
			od.addOrder(order);
			od.addOrderItems(order.getOrderitems());
			
			//2提交事务
			JdbcUtils.commitTransaction();
		}catch(Exception e){
			//如果发生异常再回滚事务
			try{
				JdbcUtils.rollbackTransaction();
			}catch(SQLException e2){
				throw new RuntimeException("对订单存储进行事物回滚");
			}
			throw new RuntimeException("存储订单异常");
		}
	}
	
	public List<Order> findOrdersByUid(String uid){
		
		return od.findOrdersByUid(uid);
	}

	public Order findOrderByOid(String oid) {
		
		return od.findOrdersByOid(oid);
	}
	//此方法用于验证并修改订单的状态
	public void ConfirmState(String oid)throws OrderException{
		int state = od.getStateByOid(oid);
		if(state!=3){
			throw new OrderException("无法确认你已经收到货");
		}else{
			od.updateState(oid,4);
		}
	}
	//此方法用于支付钱才时对订单的状态进行更改。
	
	public void pay(String oid){
		int state = od.getStateByOid(oid);
		if(state==1){
			//修改订单状态为2
			od.updateState(oid, 2);
		}
	}
	//查询所有订单
	public List<Order> findAllOrders(){
		return od.findAllOrders();
	}
	//通过订单状态查询订单
	public List<Order> findOrdersByState(String state){
		
		return od.findOrdersByState(state);
	}
}
