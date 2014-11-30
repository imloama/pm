package lite.erp.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

public interface IBaseService<M,PK> {

	public M get(PK pk);
	
	public List<M> findAll();
	
	public M update(M m);
	
	public M save(M m);
	
	public Iterable<M> save(Iterable<M> models);
	
	public void delete(PK pk);
	
	public Page<M> page(Class<M> clasz,int pageId);
	
	public Page<M> page(Class<M> clasz,int pageId,Map<String,Object> searchParams);
	
	/**
	 * 分页查询
	 * @param clasz 要查询的class
	 * @param pageId 当前页码
	 * @param pageSize 每页显示的记录数
	 * @param searchParams 查询条件
	 * @param sortCol 排序列
	 * @param direction 排序方向
	 * @return
	 */
	public Page<M> page(Class<M> clasz,int pageId,int pageSize,Map<String,Object> searchParams,String sortCol,Direction direction);
	
}
