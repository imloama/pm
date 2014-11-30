package lite.erp.system.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "sys_menu")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Access(AccessType.FIELD)
public class Menu extends Resource{
	
	private static final long serialVersionUID = -1735901240007917864L;

	@ManyToOne(fetch = FetchType.LAZY)  
	@JoinColumn(name = "parent_id")  
	private Menu parent;

	/**
	 * 子菜单
	 */
	@OneToMany(fetch=FetchType.LAZY,targetEntity=Menu.class,
			cascade={CascadeType.ALL},mappedBy="parent")
	@OrderBy("priority")
	private Set<Menu> children = new HashSet<>();
	
	@OneToMany(fetch=FetchType.LAZY,targetEntity=Action.class,
			cascade={CascadeType.ALL},mappedBy="menu")
	@OrderBy("priority")
	private Set<Action> actions = new HashSet<>();

	private boolean isShow = true;//是否为显示菜单   主要是需要配置一些子链接,不在菜单上显示,但是需要对应的模板,如显示某个单据等
	
	/**
	 * 页面js控制器
	 */
	private String jsController;
	/**
	 * 菜单的图标class
	 */
	private String icoCls;
	/**
	 * 引用的模板文件地址
	 */
	private String templateUrl;
	
	public Resource getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Set<Menu> getChildren() {
		return children;
	}
	
	public void setChildren(Set<Menu> children) {
		this.children = children;
	}

	public Set<Action> getActions() {
		return actions;
	}
	
	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}
	
	public String getJsController() {
		return jsController;
	}
	public void setJsController(String jsController) {
		this.jsController = jsController;
	}
	
	public String getIcoCls() {
		return icoCls;
	}
	
	public void setIcoCls(String icoCls) {
		this.icoCls = icoCls;
	}
	
	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Menu menu = (Menu) o;
		if (this.getId()!=menu.getId())
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return this.getId().intValue();
	}

	public boolean isLastMenu(){
		return this.getChildren().size()==0;
	}
	
	public boolean isRoot(){
		return this.getParent()==null;
	}
	
	
	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public void sortChildren(){
		this.children = getChildren().stream()
				.sorted((menu1,menu2)->menu1.getPriority()-menu2.getPriority())
				.collect(Collectors.toCollection(HashSet::new));
		this.getChildren().forEach(menu->menu.sortChildren());
	}
	
}
