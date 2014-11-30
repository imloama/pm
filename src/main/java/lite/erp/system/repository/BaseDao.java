package lite.erp.system.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseDao<M,PK extends java.io.Serializable> extends 
	PagingAndSortingRepository<M,PK>,JpaSpecificationExecutor<M>{

}
