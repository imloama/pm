package lite.erp.system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lite.erp.system.consts.SystemConsts;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "sys_role")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Access(AccessType.FIELD)
public class Role extends IdEntity {

	private static final long serialVersionUID = -3682530665331546646L;

	/**
	 * 编码,标识用的,全局惟一
	 */
	@Column(unique=true,nullable=false,length=20)
	private String code;
	
	@Column(length=20)
	private String name;

	@Column(length=100)
	private String description;

	private boolean available;

	@ManyToMany
	@JoinTable(name = "sys_role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	private List<Menu> menus = new ArrayList<>();

	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	/**
	 * 判断当前角色是否代表超级管理员,标识符为super
	 * @return
	 */
	public boolean isSuperRole(){
		return SystemConsts.ROLE_SUPER.equals(this.getCode());
	}

}
