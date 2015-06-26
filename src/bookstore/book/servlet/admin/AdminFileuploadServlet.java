package bookstore.book.servlet.admin;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;
import bookstore.book.domain.Book;
import bookstore.book.service.BookService;
import bookstore.category.domain.Category;

public class AdminFileuploadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BookService bs = new BookService();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*1创建工场
		 * 
		 * 2创建解析器
		 * 3使用解析器来解析request
		 * */
		DiskFileItemFactory dfif = new DiskFileItemFactory(40*1024,new File("E:/temp"));
		ServletFileUpload sfu = new ServletFileUpload(dfif);
		sfu.setHeaderEncoding("utf-8");
		sfu.setFileSizeMax(1024*35);//设置单个文件大小不超过35kb
		String realPath = this.getServletContext().getRealPath("/book_img");
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> fileitems = sfu.parseRequest(request);
			Map<String,String> map = new HashMap<String,String>();
			for(FileItem file : fileitems){
				if(file.isFormField()){
					map.put(file.getFieldName(), file.getString("UTF-8"));
				}
			}
			FileItem file = fileitems.get(1);
				String filename = file.getName();//得到文件名
				if(!filename.toLowerCase().endsWith("jpg")){
					request.setAttribute("ERROR", "文件必须为JPG格式");
					request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
					return;
				}
				int dex = filename.lastIndexOf("\\");
				if(dex!=-1){
					filename.substring(dex+1);
				}
				String savename = CommonUtils.uuid()+"_"+filename;// 防止重名
				int fileCode = filename.hashCode();// 得到文件的hashcode值
				System.out.println(fileCode);
				String hexname = Integer.toHexString(fileCode);//将其转化成16进制
				System.out.println(hexname);
				char oneDir = hexname.charAt(0);
				char twoDir = hexname.charAt(1);
				String savePath = realPath+"\\"+oneDir+"\\"+twoDir;
				//多级目录防止文件体系臃肿
				System.out.println(savePath);
				File filepath = new File(savePath);
				if(!filepath.exists())
					filepath.mkdirs();//如果此目录不存在那么就去创建
				File fiem = new File(filepath,savename);
				file.write(fiem);
				/*
				 * 校验图片尺寸大小
				 * */
				/*
				 * 校验图片的尺寸
				 */
				Image image = new ImageIcon(fiem.getAbsolutePath()).getImage();
				if(image.getWidth(null) > 250 || image.getHeight(null) > 250) {
					fiem.delete();//删除这个文件！
					request.setAttribute("msg", "您上传的图片尺寸超出了200 * 200！");
					request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
							.forward(request, response);
					return;
				}
				
				/*File fiem = new File(realPath,savename);
				file.write(fiem);*/
				Book book = CommonUtils.toBean(map, Book.class);
				book.setImage("book_img"+"/"+oneDir+"/"+twoDir+"/"+savename);
				Category category = CommonUtils.toBean(map, Category.class);
				book.setCategory(category);
				book.setBid(CommonUtils.uuid());
				
				bs.addBook(book);//向数据库添加book的信息
				response.sendRedirect("/BOOKSTORE/admin/AdminBookServlet?method=findAllBooks");
				//转发到AdminBookServlet的findAllBooks
				
		
			
			
			
		} catch (Exception e) {
			if(e instanceof FileUploadBase.FileSizeLimitExceededException){
				request.setAttribute("ERROR", "上传的单个文件大小不超过35kb");
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
				return;
			}
		}
	}

}
