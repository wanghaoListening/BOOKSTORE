package bookstore.user.service;

import bookstore.user.dao.UserDao;
import bookstore.user.domain.User;

public class UserService {
	private UserDao ud = new UserDao();
	
	public void regist(User form) throws UserException{
		
		User user = ud.findByUsername(form.getUsername());
		if(user!=null)
			throw new UserException("用户名已被注册");
		user = ud.findByEmail(form.getEmail());
		if(user!=null)
			throw new UserException("此邮箱已被注册");
		ud.addUser(form);
	}
	
	public void active(String code) throws UserException{
		User user = ud.findByCode(code);
		if(user==null)
			throw new UserException("激活码错误");
		if(user.isState())
			throw new UserException("你已经激活过了，不需要再激活了");
		
		ud.updateState(user.getUid(), true);
	}
	
	public User login(User form) throws UserException{
	
		User user = ud.findByUsername(form.getUsername());
		
		if(user==null)
			throw new UserException("1您还未注册，请先注册");
		if(!user.getPassword().equals(form.getPassword()))
			throw new UserException("2您密码输入有误，请校验");
		if(!user.isState())
			throw new UserException("1您还未激活请先激活");
		
		return user;
	}
	
	public User validate(User form) throws UserException{
		User user = ud.findByUsername(form.getUsername());
		if(user==null)
			throw new UserException("1您还未注册，请先注册");
		return user;
	}
	
}
