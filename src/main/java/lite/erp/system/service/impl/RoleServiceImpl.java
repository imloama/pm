package lite.erp.system.service.impl;

import java.util.Map;

import javax.transaction.Transactional;

import lite.erp.system.entity.Role;
import lite.erp.system.repository.BaseDao;
import lite.erp.system.repository.RoleDao;
import lite.erp.system.service.IRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component("roleService")
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements IRoleService {

	@Autowired
	private RoleDao roleDao;
	
	
	public RoleDao getRoleDao() {
		return roleDao;
	}
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public BaseDao<Role, Long> getBaseDao() {
		return this.getRoleDao();
	}

	@Override
	public Page<Role> page(int pageNum) {
		return this.page(Role.class, pageNum);
	}

	@Override
	public Page<Role> page(int pageNum, Map<String, Object> params) {
		return this.page(Role.class,pageNum,params);
	}

	
}
