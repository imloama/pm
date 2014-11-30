package lite.erp.system.service.impl;

import java.util.List;
import java.util.Map;

import lite.erp.system.consts.SystemConsts;
import lite.erp.system.repository.BaseDao;
import lite.erp.system.service.IBaseService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class BaseServiceImpl<M,PK extends java.io.Serializable> implements IBaseService<M,PK>{

	
	public abstract BaseDao<M, PK> getBaseDao();
	
	
	@Override
	public M get(PK pk) {
		return (M) this.getBaseDao().findOne(pk);
	}

	@Override
	public List<M> findAll() {
		Iterable<M> result = getBaseDao().findAll();
		return Lists.newArrayList(result);
	}

	@Override
	public M update(M m) {
		return this.getBaseDao().save(m);
	}
	
	@Override
	public M save(M m) {
		return this.getBaseDao().save(m);
	}
	
	@Override
	public Iterable<M> save(Iterable<M> models) {
		 return this.getBaseDao().save(models);
	}

	@Override
	public void delete(PK pk) {
		this.getBaseDao().delete(pk);
	}
	
	@Override
	public Page<M> page(Class<M> clasz,int pageId) {
		return this.page(clasz, pageId,SystemConsts.PAGE_SIZE,Maps.newHashMap(),"id",Direction.DESC);
	}

	
	@Override
	public Page<M> page(Class<M> clasz, int pageId,
			Map<String, Object> searchParams) {
		return this.page(clasz, pageId,SystemConsts.PAGE_SIZE,searchParams,"id",Direction.DESC);
	}
	
	@Override
	public Page<M> page(Class<M> clasz, int pageId, int pageSize,
			Map<String, Object> searchParams, String sortCol,
			Direction direction) {
		 PageRequest pageRequest = buildPageRequest(pageId, SystemConsts.PAGE_SIZE, sortCol,direction);
	     Specification<M> spec = buildSpecification(searchParams,clasz);
		return this.getBaseDao().findAll(spec,pageRequest);
	}
	
	
	 /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortCol,Direction direction) {
        Sort sort = null;
        if(StringUtils.isEmpty(sortCol)){
        	sortCol = "id";
        }
        sort = new Sort(direction, sortCol);
        return new PageRequest(pageNumber - 1, pagzSize, sort);
    }
 
    /**
     * 创建动态查询条件组合.
     */
    private Specification<M> buildSpecification(Map<String, Object> searchParams,Class clasz) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//        filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
        Specification<M> spec = DynamicSpecifications.bySearchFilter(filters.values(),clasz);
        return spec;
    }
	

}
