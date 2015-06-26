package bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	private QueryRunner qr = new TxQueryRunner();
	
	public User findByUsername(String username){//按用户名查询
		try{
			String sql = "select * from tb_user where username=?";
			return qr.query(sql, new BeanHandler<User>(User.class), username);
		}catch(SQLException e){
			throw new RuntimeException();
		}
	}
	
	public User findByEmail(String email){//按Email查询
		try{
			String sql = "select * from tb_user where email=?";
			return qr.query(sql, new BeanHandler<User>(User.class), email);
		}catch(SQLException e){
			throw new RuntimeException();
		}
	}
	
	public void addUser(User user){//插入user
		try{
			String sql = 
					"insert into tb_user(uid,username,password,email,code,state) values(?,?,?,?,?,?)";
			Object[] obj = {user.getUid(),user.getUsername(),user.getPassword(),user.getEmail(),user.getCode(),user.isState()};
			qr.update(sql, obj);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	/*8888888888888888888888以下为激活码相关代码8888888888888888888888888888*/
	
	public User findByCode(String code){
		try{
			String sql = "select * from tb_user where code=?";
			return qr.query(sql, new BeanHandler<User>(User.class), code);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public void updateState(String uid,boolean state){
		try{
			String sql = "update tb_user set state=? where uid=?";
			qr.update(sql, state,uid);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
}
