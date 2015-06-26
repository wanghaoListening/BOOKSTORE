package bookstore.Cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;


public class Cart {
	private Map<String,CartItem> map = new LinkedHashMap<String, CartItem>();
	//计算所有条目的小计之和
	public double getTotal(){
		
		BigDecimal total = new BigDecimal("0");
		for(CartItem item : map.values()){
			BigDecimal sum = new BigDecimal(item.getTatalPrice()+"");
			total=total.add(sum);
		}
		return total.doubleValue();
	}
	public void addItem(CartItem item){//添加条目
		String key = item.getBook().getBid();
		if(map.containsKey(key)){//判断车中是否存在该条目。
			CartItem olditem = map.get(key);
			int count = olditem.getCount()+item.getCount();//原条目数量加上新的条目数量。
			olditem.setCount(count);
			
			map.put(key, olditem);
		}else{
			map.put(key, item);
		}
	}
	public void clearItem(){
		map.clear();//清空所有条目
	}
	public void deleteItem(String bid){//删除指定的条目
		map.remove(bid);
	}
	public Collection<CartItem> getAllItem(){
		
		return map.values();//返回所有值。
	}

}
