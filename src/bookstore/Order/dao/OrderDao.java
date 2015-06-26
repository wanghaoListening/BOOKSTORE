package bookstore.Order.dao;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bookstore.Order.domain.Order;
import bookstore.Order.domain.OrderItem;
import bookstore.book.domain.Book;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();
	
	public void addOrder(Order order){
		try{
			String sql = "insert into orders values(?,?,?,?,?,?)";
			long time = order.getOrderTime().getTime();
			Timestamp ordertime = new Timestamp(time);
			Object[] params={order.getOid(),ordertime,order.getTotal()
					,order.getState(),order.getOwner().getUid(),order.getAddress()}; 
			qr.update(sql, params);
			
		}catch(SQLException e){
			throw new RuntimeException("insert into orders values(?,?,?,?,?,?)出现异常");
		}
	}
	
	//查看所有的订单
	
	public List<Order> findAllOrders(){
		String sql = "select * from orders";
		try{
			return  qr.query(sql, new BeanListHandler<Order>(Order.class));
			
		}catch(SQLException e){
			throw new RuntimeException("select * from orders出现异常");
		}
	}
	
	public void addOrderItems(List<OrderItem> orderitems){
		try{
			
			String sql = "insert into Orderitem values(?,?,?,?,?)";
			Object[][] params =new  Object[orderitems.size()][];
			for(int i=0;i<orderitems.size();i++){
				OrderItem item = orderitems.get(i);
				params[i]=new Object[]{item.getIid(),item.getCount(),
						item.getTotal(),item.getOrder().getOid(),item.getBook().getBid()};
			}
			qr.batch(sql, params);//执行批处理
		}catch(SQLException e){
			
			throw new RuntimeException("insert into Orderitem values(?,?,?,?,?)出现异常");
		}
		
	}
	
	public List<Order> findOrdersByUid(String uid){
		List<Order> orders=null;
		try{
			String sql = "select * from orders where uid=?";
			orders= qr.query(sql, new BeanListHandler<Order>(Order.class),uid);
			/*
			 * 
			 * 
			 * */
			for(Order or : orders){
				toOrderItem(or);
				
			}
			
		}catch(SQLException e){
			//e.printStackTrace();
			throw new RuntimeException("select * from orders where uid=?出现异常");
		}
		return orders;
	}

	private void toOrderItem(Order or) throws SQLException {
		String sql = "select * from orderitem i, book b where i.bid=b.bid and oid=?";
		List<Map<String,Object>> maps = qr.query(sql, new MapListHandler(),or.getOid());
		List<OrderItem> orderitems = toOrderItems(maps);
		or.setOrderitems(orderitems);
		
	}

	private List<OrderItem> toOrderItems(List<Map<String, Object>> maps) {
		List<OrderItem> items = new ArrayList<OrderItem>();
		for(Map<String, Object> map : maps){
			OrderItem e = toOrderItem(map);
			items.add(e);
		}
		return items;
	}

	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem oi = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		oi.setBook(book);
		return oi;
	}

	public Order findOrdersByOid(String oid) {
		try{
			String sql = "select * from orders where oid=?";
			Order order= qr.query(sql, new BeanHandler<Order>(Order.class),oid);
			toOrderItem(order);
			return order;
		}catch(SQLException e){
			throw new RuntimeException("select * from orders where oid=?出现异常");
		}
		
		
	}
	
	//---------------------订单状态相关--------------------------------
	
	public int getStateByOid(String oid){
		Number num=0;
		try{
			String sql = "select state from orders where oid=?";
			 num = (Number)qr.query(sql, new ScalarHandler(),oid);
			
		}catch(SQLException e){
			e.printStackTrace();
			//throw new RuntimeException("select state from orders where oid=?");
		}
		return num.intValue();
	}
	
	public void updateState(String oid, int i){
		try{
			String sql = "update orders set state=? where oid=?";
			qr.update(sql,i,oid);
		}catch(SQLException e){
			throw new RuntimeException("update order set state=? where oid=?");
		}
	}

	
	//通过订单状态查询订单
	public List<Order> findOrdersByState(String state) {
		try{
			String sql = "select * from orders where state=?";
			int s = Integer.parseInt(state);
			return qr.query(sql, new BeanListHandler<Order>(Order.class),s);
		}catch(SQLException e){
			throw new RuntimeException("select * from orders where state=?");
		}
		
	}
}
