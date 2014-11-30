package lite.erp.system.entity;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 用户组
 * 
 * @author mazhyb
 */

@Entity
@Table(name = "sys_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Access(AccessType.FIELD)
public class Group extends IdEntity {

	private static final long serialVersionUID = 8491563021218860329L;

//	@NotEmpty(message = "集团名称不能为空")
	private String vname;// 集团名称

	@ManyToOne(cascade = { CascadeType.ALL})
	@JoinColumn(name = "parent_id")
	private Group parent;
	
	
	public String getVname() {
		return vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}

	public Group getParent() {
		return parent;
	}
	
	public void setParent(Group parent) {
		this.parent = parent;
	}

/*	@ManyToOne(cascade = { CascadeType.ALL})
	@JoinColumn(name = "leader_id")
	private User leader;// 责任人

	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}*/
	
	@ManyToMany(cascade={CascadeType.PERSIST},fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinTable(name = "sys_user_group", joinColumns = @JoinColumn(name = "group_id"), 
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}


	
}
