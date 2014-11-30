package lite.erp.system.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "sys_action")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Access(AccessType.FIELD)
public class Action extends Resource{

	private static final long serialVersionUID = 7608795612179110699L;
	/**
	 * 所属的页面
	 */
	@ManyToOne(fetch = FetchType.LAZY)  
	@JoinColumn(name = "menu_id")  
	private Menu menu;
	
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
}
