package lite.erp.system.service;

import java.util.Map;

import lite.erp.system.entity.Role;

import org.springframework.data.domain.Page;


public interface IRoleService extends IBaseService<Role,Long>{

	public Page<Role> page(int pageNum);
	
	public Page<Role> page(int pageNum,Map<String,Object> params);
	
}
