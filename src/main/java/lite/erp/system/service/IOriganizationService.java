package lite.erp.system.service;

import java.util.List;

import lite.erp.system.entity.Origanization;
import lite.erp.system.vo.OriganizationVO;

public interface IOriganizationService extends IBaseService<Origanization,Long>{

	public List<OriganizationVO> findAllVOs();
	
	public OriganizationVO findVO(Long id);
	
	public OriganizationVO insert(OriganizationVO vo);
	
	public OriganizationVO update(OriganizationVO vo);
	
}
