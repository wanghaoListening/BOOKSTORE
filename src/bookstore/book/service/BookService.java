package bookstore.book.service;

import java.util.List;

import bookstore.book.dao.BookDao;
import bookstore.book.domain.Book;

public class BookService {
	private BookDao bd = new BookDao();
	
	public List<Book> findAllBooks(){
		return bd.findAllBooks();
	}
	
	public List<Book> findBooksByCid(String cid){
		return bd.findBookByCid(cid);
	}
	
	public Book findBookByBid(String bid){
		return bd.findBookByBid(bid);
	}

	public void addBook(Book book) {
		
		 bd.addBook(book);
	}
	
	public void delete(String bid){
		bd.delete(bid);
	}
	
	public void modify(Book book){
		bd.modify(book);
	}
}
