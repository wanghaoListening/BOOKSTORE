package textDemo;



import bookstore.user.dao.UserDao;
import bookstore.user.domain.User;

public class JunitDemo {
	
	public static void main(String[] args) {
		UserDao ud = new UserDao();
		User u = ud.findByUsername("wanghao");
		System.out.println(u.toString());
	}

	public static void Demo_1() {
		UserDao ud = new UserDao();
		User u = new User();
		u.setCode("code");
		u.setEmail("email");
		u.setPassword("Password");
		u.setState(false);
		u.setUid("uid");
		u.setUsername("username");
		ud.addUser(u);
	}
	
}
