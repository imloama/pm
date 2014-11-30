package lite.erp.system.service;

import java.util.List;

import lite.erp.system.entity.Menu;
import lite.erp.system.entity.User;

public interface IMenuService extends IBaseService<Menu,Long> {

	/**
	 * 获取所有的权限功能,按照从上到下的分类处理
	 * @return
	 */
	public List<Menu> findAll();
	/**
	 * 根据当前用户信息,查询其可以操作的菜单
	 * @param user
	 * @return
	 */
	public List<Menu> findMenuByUser(User user);
	
}
