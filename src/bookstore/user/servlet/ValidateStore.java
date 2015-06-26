package bookstore.user.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

public class ValidateStore {
	public static boolean emaliValidate(String email){
		String regex="\\w+@\\w+(\\.\\w+)+";
		return email.matches(regex);
	}
	
	public static void sendEmail(String receive,String code){
		
		try {
			Properties prop = new Properties();
			InputStream in = ValidateStore.class.getResourceAsStream("/email.properties");
			prop.load(in);
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			String host = prop.getProperty("host");
			String from = prop.getProperty("from");
			String subject = prop.getProperty("subject");
			String content = prop.getProperty("content");
			//此方法可以把content中的占位符替换成code
			content = MessageFormat.format(content, code);
			/*
			 * 1. 得到session
			 */
			Session session = MailUtils.createSession(host, 
					username, password);
			/*
			 * 2. 创建邮件对象
			 */
			Mail mail = new Mail(from,
					receive,
					subject, content
					);
			
			/*
			 * 创建两个附件对象
			 */
			//AttachBean ab1 = new AttachBean(sendfile, filename);
			
			
			// 添加到mail中
			//mail.addAttach(ab1);
			
			
			/*
			 * 3. 发送
			 */
	
				MailUtils.send(session, mail);
		
		} catch (IOException e) {
			
			throw new RuntimeException("文件加载失败");
		}catch(MessagingException e){
			throw new RuntimeException("邮件发送失败");
		}
	}
}
