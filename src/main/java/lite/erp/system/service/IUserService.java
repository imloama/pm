package lite.erp.system.service;

import java.util.List;

import lite.erp.system.entity.Menu;
import lite.erp.system.entity.User;

public interface IUserService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	public User findUserByName(String name);
	
	public User findUserByNameAndPassword(String name,String password);
	
	public void entryptPassword(User user);
	
	/**
	 * 获取当前登陆用户的菜单
	 * @return
	 */
	public List<Menu> findMenu(User user);
	
	/**
	 * 判断当前用户是否为超级管理员(默认情况下,超级管理员的role的id为1)
	 * @param user
	 * @return
	 */
	public boolean isSuperUser(User user);

}
