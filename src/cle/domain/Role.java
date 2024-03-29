package cle.domain;

// Generated 11-Mar-2011 20:55:57 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Role generated by hbm2java
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "role")
public class Role implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer roleid;
	private String rolename;
	private Set<User> users = new HashSet<User>(0);

	public Role() {
	}

	public Role(String rolename, Set<User> users) {
		this.rolename = rolename;
		this.users = users;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "roleid", unique = true, nullable = false)
	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	@Column(name = "rolename", length = 45)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_has_role", joinColumns = { @JoinColumn(name = "role_roleid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "user_userid", nullable = false, updatable = false) })
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@Transient
	public String getAuthority() {
		return this.rolename;
	}

	public void setAuthority(String authority) {
		this.rolename = authority;
	}
	@Override
	public boolean equals(Object compareObj)// Roles are equal when the role
											// names are the same, - don't care
											// if objects are equal
	{
		if (this == compareObj) // Are they exactly the same instance?
			return true;

		if (compareObj == null) // Is the object being compared null?
			return false;

		if (!(compareObj instanceof Role)) // Is the object being compared also
											// a Person?
			return false;

		Role compareRole = (Role) compareObj; // Convert the object to a
												// Person

		return this.rolename.equals(compareRole.rolename); // Are they equal?
	}
}
