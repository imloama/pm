package lite.erp.system.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

@Entity
@Table(name = "sys_user")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Access(AccessType.FIELD)
public class User extends IdEntity {

	private static final long serialVersionUID = 6389185993084899308L;

	private String name;
	
	private String password;
	
	private String email;
	
	private String nickname;//显示名称
	
	private String salt;
	
	private boolean locked;
	/**
	 * 头像
	 */
	private String avatar;
	
	@ManyToMany(cascade={CascadeType.PERSIST})
	@JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "origanization_id")
	private Origanization origanization;//组织

	@ManyToMany(cascade={CascadeType.PERSIST}, fetch = FetchType.LAZY, targetEntity = Group.class)
	@JoinTable(name = "sys_user_group", joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "group_id"))
	private List<Group> groups = new ArrayList<Group>();
	
	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getNickname() {
		return nickname;
	}
	
	public User setNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}
	
	
	public boolean isLocked() {
		return locked;
	}
	
	public User setLocked(boolean locked) {
		this.locked = locked;
		return this;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public User setRoles(Set<Role> roles) {
		this.roles = roles;
		return this;
	}
	
	public Set<String> getRolesCode(){
		Set<String> result = new HashSet<String>();
		this.getRoles().forEach(role->result.add(role.getCode()));
		return result;
	}
	
	/**
	 * 获取当前用户的所有权限数据
	 * @return
	 */
	public Set<String> getPermissions(){
		Set<String> result = new HashSet<>();
		this.getRoles().forEach(role->{
			role.getMenus().forEach(menu->{
				result.add(menu.getCode());
				menu.getActions().forEach(action->{
					result.add(action.getCode());
				});
			});
		});
		return result;
	} 
	
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public User setGroups(List<Group> groups) {
		this.groups = groups;
		return this;
	}
	
	public Origanization getOriganization() {
		return origanization;
	}
	
	public User setOriganization(Origanization origanization) {
		this.origanization = origanization;
		return this;
	}
	
	public String getSalt() {
		return salt;
	}
	public User setSalt(String salt) {
		this.salt = salt;
		return this;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
	
	
}
