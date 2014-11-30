package lite.erp.system.service.impl;


import java.util.List;
import java.util.Set;

import lite.erp.system.entity.Menu;
import lite.erp.system.entity.Role;
import lite.erp.system.entity.User;
import lite.erp.system.repository.BaseDao;
import lite.erp.system.repository.UserDao;
import lite.erp.system.service.IMenuService;
import lite.erp.system.service.IRoleService;
import lite.erp.system.service.IUserService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.google.common.collect.Lists;

@Component("userService")
@Transactional
//@Monitored
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements IUserService {
	

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IMenuService menuService;
	
	
	@Override
	public User findUserByName(String name) {
		logger.debug("work in findUserByName with name:"+name);
		return this.getUserDao().findUserByName(name);
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public void entryptPassword(User user) {
		byte[] salt = new byte[0];
		if(StringUtils.isEmpty(user.getSalt())){
			salt = Digests.generateSalt(SALT_SIZE);
			user.setSalt(Encodes.encodeHex(salt));
		}else{
			salt = user.getSalt().getBytes();
		}
		byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);//PlainPassword
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Override
	public User findUserByNameAndPassword(String name, String password) {
		User user = new User().setName(name).setPassword(password);
		this.entryptPassword(user);
		return this.userDao.findUserByNameAndPassword(name, user.getPassword());
	}

	@Override
	public List<Menu> findMenu(User user) {
		if(null==user){
			return Lists.newArrayList();
		}
		if(this.isSuperUser(user)){
			return this.getMenuService().findAll();
		}else{
			return this.getMenuService().findMenuByUser(user);
		}
	}

	@Override
	public boolean isSuperUser(User user) {
		Set<Role> roles = user.getRoles();
		boolean result = false;
		for(Role role : roles){
			if(role.isSuperRole()){
				result = true;
				break;
			}
		}
		return result;
	}
	
	
	public IRoleService getRoleService() {
		return roleService;
	}
	
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	
	public IMenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public BaseDao<User, Long> getBaseDao() {
		return this.getUserDao();
	}

}
