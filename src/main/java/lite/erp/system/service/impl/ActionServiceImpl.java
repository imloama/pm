package lite.erp.system.service.impl;

import javax.transaction.Transactional;

import lite.erp.system.entity.Action;
import lite.erp.system.repository.ActionDao;
import lite.erp.system.repository.BaseDao;
import lite.erp.system.service.IActionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("actionService")
@Transactional
public class ActionServiceImpl extends BaseServiceImpl<Action,Long> implements IActionService {

	
	
	@Autowired
	private ActionDao actionDao;
	
	public ActionDao getActionDao() {
		return actionDao;
	}
	
	public void setActionDao(ActionDao actionDao) {
		this.actionDao = actionDao;
	}
	
	@Override
	public BaseDao<Action, Long> getBaseDao() {
		return this.getActionDao();
	}

}
