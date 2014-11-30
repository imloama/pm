package lite.erp.system.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import lite.erp.system.entity.Origanization;
import lite.erp.system.repository.BaseDao;
import lite.erp.system.repository.OriganizationDao;
import lite.erp.system.service.IOriganizationService;
import lite.erp.system.vo.OriganizationVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;



@Component("origService")
@Transactional
public class OriganizationServiceImpl extends BaseServiceImpl<Origanization,Long> implements IOriganizationService{

	private static final Logger logger = LoggerFactory.getLogger(OriganizationServiceImpl.class);
	
	
	@Autowired
	private OriganizationDao origDao;
	
	public OriganizationDao getOrigDao() {
		return origDao;
	}
	
	public void setOrigDao(OriganizationDao origDao) {
		this.origDao = origDao;
	}
	
	@Override
	public BaseDao<Origanization, Long> getBaseDao() {
		return this.getOrigDao();
	}

	/**
	 * 查询所有组织内容，并转换成vo
	 */
	@Override
	public List<OriganizationVO> findAllVOs() {
		logger.debug("find all vos");
		List<Origanization> origs = this.findAll();
		List<OriganizationVO> result = Lists.newArrayList();
		origs.forEach(orig->result.add(new OriganizationVO(orig)));
		return result;
	}

	@Override
	public OriganizationVO findVO(Long id) {
		return new OriganizationVO(this.get(id));
	}

	@Override
	public OriganizationVO insert(OriganizationVO vo) {
		return new OriganizationVO(save(vo.toOriganization()));
	}

	@Override
	public OriganizationVO update(OriganizationVO vo) {
		return new OriganizationVO(save(vo.toOriganization()));
	}
	
	

}
