package com.huge.ihos.system.systemManager.user.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.huge.ihos.multidatasource.DynamicSessionFactoryHolder;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.model.BaseObject;
import com.huge.model.LabelValue;

/**
 * This class represents the basic "user" object in AppFuse that allows for
 * authentication and user management. It implements Acegi Security's
 * UserDetails interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Updated by
 *         Dan Kibler (dan@getrolling.com) Extended to implement Acegi
 *         UserDetails interface by David Carter david@carter.net
 */
@Entity
@Table( name = "app_user" )
public class User
    extends BaseObject
    implements Serializable, UserDetails {
    private static final long serialVersionUID = 3832626162173359411L;

    private Long id;

    private String username; // required

    private String password; // required

    private String confirmPassword;

    private Set<Role> roles = new HashSet<Role>();

    private boolean enabled = true;

    private boolean accountExpired;

    private boolean accountLocked;

    private boolean credentialsExpired;

    private Person person;

    private Set<Menu> menus = new HashSet<Menu>();
    
    private Set<Menu> rootMenus = new HashSet<Menu>();

	private Date loginTime;

    private String loginIp;
    
    private String personName;
    
    private String deptName;
    
    private String subSystem;
    
    private SystemVariable systemVariable;


	@OneToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "person_id", nullable = true )
    public Person getPerson() {
        return person;
    }

    public void setPerson( Person person ) {
        this.person = person;
    }

    /**
     * Default constructor - creates a new instance with no values set.
     */
    public User() {
    }

    /**
     * Create a new instance and set the username.
     * 
     * @param username
     *            login name for user.
     */
    public User( final String username ) {
        this.username = username;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getId() {
        return id;
    }

    @Column( nullable = false, length = 50, unique = true )
    public String getUsername() {
        return username;
    }

    @Column( nullable = false )
    @XmlTransient
    public String getPassword() {
        return password;
    }

    @Transient
    @XmlTransient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /*
     * @Column(name = "first_name", nullable = false, length = 50)
     * 
     *  public String getFirstName() { return firstName; }
     * 
     * @Column(name = "last_name", nullable = false, length = 50)
     * 
     *  public String getLastName() { return lastName; }
     */

    /*
     * @Column(name = "phone_number")
     * 
     *  public String getPhoneNumber() { return phoneNumber;
     * }
     * 
     *  public String getWebsite() { return website; }
     */

    /**
     * Returns the full name.
     * 
     * @return firstName + ' ' + lastName
     */
    /*
     * @Transient public String getFullName() { return firstName + ' ' +
     * lastName; }
     * 
     * @Embedded
     * 
     * @SearchableComponent public Address getAddress() { return address; }
     */
    @JSON( serialize = false )
    @ManyToMany
    @JoinTable( name = "user_role", joinColumns = { @JoinColumn( name = "user_id" ) }, inverseJoinColumns = @JoinColumn( name = "role_id" ) )
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Convert user roles to LabelValue objects for convenience.
     * 
     * @return a list of LabelValue objects with role information
     */
    @JSON(serialize=false)
    @Transient
    public List<LabelValue> getRoleList() {
        List<LabelValue> userRoles = new ArrayList<LabelValue>();

        if ( this.roles != null ) {
            for ( Role role : roles ) {
                // convert the user's roles to LabelValue Objects
                userRoles.add( new LabelValue( role.getName(), role.getName() ) );
            }
        }

        return userRoles;
    }

    /**
     * Adds a role for the user
     * 
     * @param role
     *            the fully instantiated role
     */
    public void addRole( Role role ) {
        getRoles().add( role );
    }

    public void addMenu( Menu menu ) {
        getMenus().add( menu );
    }

    /**
     * @return GrantedAuthority[] an array of roles.
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @JSON(serialize=false)
    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
        authorities.addAll( roles );
        GrantedAuthorityImpl grantedAuthorityImplUser = new GrantedAuthorityImpl( this.getUsername() );
        authorities.add( grantedAuthorityImplUser );
        return authorities;
    }

    @Column( name = "account_enabled" )
    public boolean isEnabled() {
        return enabled;
    }

    @Column( name = "account_expired", nullable = false )
    public boolean isAccountExpired() {
        return accountExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     * @return true if account is still active
     */
    @Transient
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Column( name = "account_locked", nullable = false )
    public boolean isAccountLocked() {
        return accountLocked;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     * @return false if account is locked
     */
    @Transient
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Column( name = "credentials_expired", nullable = false )
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     * @return true if credentials haven't expired
     */
    @Transient
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public void setConfirmPassword( String confirmPassword ) {
        this.confirmPassword = confirmPassword;
    }

    public void setRoles( Set<Role> roles ) {
        this.roles = roles;
    }

    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }

    public void setAccountExpired( boolean accountExpired ) {
        this.accountExpired = accountExpired;
    }

    public void setAccountLocked( boolean accountLocked ) {
        this.accountLocked = accountLocked;
    }

    @Transient
    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime( Date loginTime ) {
        this.loginTime = loginTime;
    }

    @Transient
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp( String loginIp ) {
        this.loginIp = loginIp;
    }

    public void setCredentialsExpired( boolean credentialsExpired ) {
        this.credentialsExpired = credentialsExpired;
    }

    @JSON( serialize = false )
    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "t_privilege_user", joinColumns = { @JoinColumn( name = "user_id" ) }, inverseJoinColumns = @JoinColumn( name = "menu_id" ) )
    @OrderBy("menuId asc")
    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus( Set<Menu> menus ) {
        this.menus = menus;
    }

    @JSON(serialize=false)
    @Transient
    public Set<Menu> getRootMenus() {
    	rootMenus = new HashSet<Menu>();
    	for(Role role : roles){
    		Set<Menu> roleRootMenus = new HashSet<Menu>();
    		roleRootMenus = role.getRootMenus();
    		for(Menu menu : roleRootMenus){
    			rootMenus.add(menu);
    		}
    	}
		for(Menu menu : this.menus){
			if(menu.getMenuLevel()==0){
				rootMenus.add(menu);
			}
		}
		return rootMenus;
	}

	public void setRootMenus(Set<Menu> rootMenus) {
		this.rootMenus = rootMenus;
	}

	@Transient
	public String getPersonName() {
		if(person!=null){
			personName = person.getName();
		}
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	@Transient
	public String getDeptName() {
		if(person!=null&&person.getDepartment()!=null){
			deptName = person.getDepartment().getName();
		}
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	@Column( name = "subSystem",length=20)
	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}
	
	@Transient
	public SystemVariable getSystemVariable() {
		return systemVariable;
	}

	public void setSystemVariable(SystemVariable systemVariable) {
		this.systemVariable = systemVariable;
	}
	
	
    /**
     * {@inheritDoc}
     */
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof User ) ) {
            return false;
        }

        final User user = (User) o;

//        return !( username != null ? !username.equals( user.getUsername() ) : user.getUsername() != null );
        return this.hashCode()==user.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
//        return ( username != null ? username.hashCode() : 0 );
        int result;
    	result = ( username != null ? username.hashCode() : 0 );
    	result = 29*result +DynamicSessionFactoryHolder.getSessionFactoryName().hashCode();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        ToStringBuilder sb =
            new ToStringBuilder( this, ToStringStyle.DEFAULT_STYLE ).append( "username", this.username ).append( "enabled", this.enabled ).append(
                                                                                                                                                   "accountExpired",
                                                                                                                                                   this.accountExpired ).append(
                                                                                                                                                                                 "credentialsExpired",
                                                                                                                                                                                 this.credentialsExpired ).append(
                                                                                                                                                                                                                   "accountLocked",
                                                                                                                                                                                                                   this.accountLocked );

        if ( roles != null ) {
            sb.append( "Granted Authorities: " );

            int i = 0;
            for ( Role role : roles ) {
                if ( i > 0 ) {
                    sb.append( ", " );
                }
                sb.append( role.toString() );
                i++;
            }
        }
        else {
            sb.append( "No Granted Authorities" );
        }
        return sb.toString();
    }
}
