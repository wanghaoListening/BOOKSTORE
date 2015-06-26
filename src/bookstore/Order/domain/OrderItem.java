package bookstore.Order.domain;

import bookstore.book.domain.Book;


//订单条目
public class OrderItem {
	private String iid;
	private int count;//数量
	
	private double total;//小计
	private Order order;// 所属订单
	private Book book;//所要购买的图书
	
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	@Override
	public String toString() {
		return "OrderItem [iid=" + iid + ", count=" + count + ", total="
				+ total + ", order=" + order + ", book=" + book + "]";
	}
	
	
}
