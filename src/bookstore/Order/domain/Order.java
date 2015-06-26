package bookstore.Order.domain;

import java.util.Date;
import java.util.List;

import bookstore.user.domain.User;

public class Order {
	private String oid;
	private Date orderTime;//下单时间
	private double total;
	private int state;//四中状态1.未付款2.已付款但未发货3.已发货但未确认收货4.确认交易成功
	private User owner;
	private List<OrderItem> orderitems;//当前订单下所有的条目
	private String address;
	
	public List<OrderItem> getOrderitems() {
		return orderitems;
	}
	public void setOrderitems(List<OrderItem> orderitems) {
		this.orderitems = orderitems;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", orderTime=" + orderTime + ", total="
				+ total + ", state=" + state + ", owner=" + owner
				+ ", orderitems=" + orderitems + ", address=" + address + "]";
	}
	

	
}
