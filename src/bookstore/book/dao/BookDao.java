package bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import bookstore.book.domain.Book;
import bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	
	public List<Book> findAllBooks(){
		try{//查询所有的书籍
			String sql = "select * from book where del=false";
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		}catch(SQLException e){
			throw new RuntimeException("select * from book 出现错误");
		}
	}
	
	public List<Book> findBookByCid(String cid){
		try{//通过分类查询所有的书籍
			String sql = "SELECT * FROM book where cid=? and del=false";
			return qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
		}catch(SQLException e){
			throw new RuntimeException("select * from book where cid=? 出现错误");
		}
	}
	public Book findBookByBid(String bid){
		try{//通过书号查询相应的书
			String sql = "select * from book where bid=?";
			Map<String,Object> map = qr.query(sql, new MapHandler(), bid);
			Category category = CommonUtils.toBean(map, Category.class);
			Book book = CommonUtils.toBean(map, Book.class);
			book.setCategory(category);
			return book;
		}catch(SQLException e){
			throw new RuntimeException("select * from book where bid=? 出现错误");
		}
	}

	public void addBook(Book book) {
		
		try{
			String sql = "insert into book values(?,?,?,?,?,?,?)";
			Object[] params =new Object[]{book.getBid(),
					book.getBname(),book.getPrice(),book.getAuthor(),
					book.getImage(),book.getCategory().getCid(),false}; 
			qr.update(sql, params);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void delete(String bid){
		try{
			String sql = "update book set del=true where bid=?";
			qr.update(sql,bid);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void modify(Book book){
		try{
			String sql = "update book set bname=?,price=?,author=?,cid=? where bid=?";
			Object[] obj = new Object[]{book.getBname(),book.getPrice(),book.getAuthor(),
					book.getCategory().getCid(),book.getBid()};
			qr.update(sql, obj);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
