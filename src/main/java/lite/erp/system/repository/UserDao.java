package lite.erp.system.repository;

import lite.erp.system.entity.User;

public interface UserDao extends BaseDao<User, Long> {

	public User findUserByName(String name);
	
	public User findUserByNameAndPassword(String name,String password);
	
}
