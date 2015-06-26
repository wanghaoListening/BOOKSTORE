package bookstore.Cart.domain;

import java.math.BigDecimal;

import bookstore.book.domain.Book;

//购物车条目类
public class CartItem {
	private Book book;//商品
	private int count;//数量 
	// 此方法处理了二进制运算误差问题
	public double getTatalPrice(){//小计方法但它没有对应的方法
		BigDecimal b1 = new BigDecimal(book.getPrice()+"");
		BigDecimal b2 = new BigDecimal(count+"");
		return b1.multiply(b2).doubleValue();
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "CartItem [book=" + book + ", count=" + count + "]";
	}
	
}
