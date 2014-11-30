package lite.erp.system.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 组织机构
 * @author mazhyb
 *
 */
@Entity
@Table(name = "sys_origanization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Access(AccessType.FIELD)
public class Origanization extends IdEntity {

	private static final long serialVersionUID = -6652709283355635246L;
	
	@Column(nullable=false,unique=true)
	private String name;
	/**
	 * 显示顺序
	 */
	@Column(columnDefinition="int default 0")
	private int priority;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Origanization parent;
	//是否可用
	private boolean available = true;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Origanization getParent() {
		return parent;
	}
	public void setParent(Origanization parent) {
		this.parent = parent;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}

	
	
	
}
