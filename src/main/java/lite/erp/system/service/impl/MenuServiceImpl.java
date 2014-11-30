package lite.erp.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lite.erp.system.entity.Menu;
import lite.erp.system.entity.User;
import lite.erp.system.repository.BaseDao;
import lite.erp.system.repository.MenuDao;
import lite.erp.system.service.IMenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("menuService")
@Transactional
public class MenuServiceImpl extends BaseServiceImpl<Menu,Long> implements IMenuService {

	@Autowired
	private MenuDao menuDao;


	public MenuDao getMenuDao() {
		return menuDao;
	}
	
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	
	@Override
	public List<Menu> findAll() {
		List<Menu> menus = Lists.newArrayList(menuDao.findAll());
		return this.filterMenus(menus);
	}
	/**
	 * 整理菜单,完成排序
	 * @param menus
	 * @return
	 */
	private List<Menu> filterMenus(List<Menu> menus){
		List<Menu> result = menus.stream().filter(menu->menu.isRoot())
				.sorted((menu1,menu2)->menu1.getPriority()-menu2.getPriority())
				.collect(Collectors.toCollection(ArrayList::new));
		//子排序
		result.forEach(menu->menu.sortChildren());
		return result;
	}

	@Override
	public List<Menu> findMenuByUser(User user) {
		List<Menu> menus = Lists.newArrayList();
		user.getRoles().forEach(role->{
			menus.addAll(role.getMenus());
		});
		return this.filterMenus(menus);
	}

	@Override
	public BaseDao<Menu, Long> getBaseDao() {
		return this.getMenuDao();
	}
	
}
