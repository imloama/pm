package lite.erp.system.vo;

import lite.erp.system.entity.Origanization;

/**
 * Origanization类的前台配置接口类
 * @author mazhaoyong
 *
 */
public class OriganizationVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public OriganizationVO(){}
	
	public OriganizationVO(Origanization orig){
		this.setId(orig.getId()).setName(orig.getName())
			.setPriority(orig.getPriority())
			.setAvailable(orig.isAvailable());
		if(orig.getParent()!=null){
			this.setParentId(orig.getParent().getId());
		}
	}
	
	private Long id;
	
	private String name;
	
	private int priority;
	
	private boolean available;
	
	private Long parentId;

	public Long getId() {
		return id;
	}

	public OriganizationVO setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public OriganizationVO setName(String name) {
		this.name = name;
		return this;
	}

	public int getPriority() {
		return priority;
	}

	public OriganizationVO setPriority(int priority) {
		this.priority = priority;
		return this;
	}

	public boolean isAvailable() {
		return available;
	}

	public OriganizationVO setAvailable(boolean available) {
		this.available = available;
		return this;
	}

	public Long getParentId() {
		return parentId;
	}

	public OriganizationVO setParentId(Long parentId) {
		this.parentId = parentId;
		return this;
	}
	
	public Origanization toOriganization(){
		Origanization orig = new Origanization();
		if(null!=this.getId()){
			orig.setId(this.getId());
		}
		orig.setAvailable(isAvailable());
		orig.setName(getName());
		if(this.getParentId()!=0){
			Origanization parent = new Origanization();
			parent.setId(this.getParentId());
			orig.setParent(parent);
		}
		orig.setPriority(this.getPriority());
		return orig;
	}
	
}
