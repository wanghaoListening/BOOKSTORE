package bookstore.category.service;

import java.util.List;

import bookstore.category.dao.CategoryDao;
import bookstore.category.domain.Category;

public class CategoryService {
	private CategoryDao cd = new CategoryDao();

	public List<Category> findAllCategory() {
		
		return cd.findAllCategory();
	}

	public void modCategory(String cid) {
		
		cd.modCategory(cid);
		
	}

	public void delCategory(String cid) {
		cd.delCategory(cid);
	}

	public void addCategory(String cname,String cid) {
		
		cd.addCategory(cname,cid);
	}

	public Category load(String cid) {
		
		return cd.loadCategory(cid);
	}

	public void edit(String cname, String cid) {
		
		cd.edit(cname,cid);
		
	}
}
