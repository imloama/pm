package lite.erp.system.service.impl;

import javax.transaction.Transactional;

import lite.erp.system.entity.Group;
import lite.erp.system.repository.BaseDao;
import lite.erp.system.repository.GroupDao;
import lite.erp.system.service.IGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("groupService")
@Transactional
public class GroupServiceImpl extends BaseServiceImpl<Group,Long> implements IGroupService {

	
	@Autowired
	private GroupDao groupDao;
	
	public GroupDao getGroupDao() {
		return groupDao;
	}
	
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	@Override
	public BaseDao<Group, Long> getBaseDao() {
		return this.getGroupDao();
	}

}
