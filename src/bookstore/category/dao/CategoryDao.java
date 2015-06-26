package bookstore.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import bookstore.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();

	public List<Category> findAllCategory() {
		try{
			String sql = "select * from category";
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
		}catch(SQLException e){
			throw new RuntimeException("select * from category失败");
		}
		
	}

	public void modCategory(String cid) {
		
		
	}

	public void delCategory(String cid) {
		try{
			String sql = "delete from category where cid=?";
			System.out.println("delete from category where cid="+cid);
			qr.update(sql,cid);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}

	public void addCategory(String cname,String cid) {
		try{
			String sql2 = "insert into category values(?,?)";
			qr.update(sql2,cid,cname);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}

	public Category loadCategory(String cid) {
		try{
			String sql = "select * from category where cid=?";
			return qr.query(sql, new BeanHandler<Category>(Category.class),cid);
		}catch(SQLException e){
			throw new RuntimeException("select * from category where cid=?失败");
		}
		
	}

	public void edit(String cname, String cid) {
		try{
			String sql = "update category set cname=? where cid=?";
			qr.update(sql,cname,cid);
		}catch(SQLException e){
			throw new RuntimeException("select * from category where cid=?失败");
		}
		
	}
}
