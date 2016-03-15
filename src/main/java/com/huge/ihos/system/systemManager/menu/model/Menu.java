package com.huge.ihos.system.systemManager.menu.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.model.BaseObject;

@Entity
@Table( name = "t_menu" )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class Menu
    extends BaseObject
    implements Serializable,Comparable<Menu> {
    /**
     * 
     */
    private static final long serialVersionUID = -8898179103527817691L;

    private String menuId;

    private String menuName;

    private String menuAction;

    private boolean leaf;

    private Long menuLevel;

    private Long displayOrder;

    private String entityName;

    private Menu parent;

    private Menu subSystem;

    private String iconName;

    private boolean disabled = false;

    private Set<Role> roles = new HashSet<Role>();

    private Set<User> users = new HashSet<User>();
    
    private String parentName;
    
    private String subSystemName;
    
    private boolean flag = false;
    
    private Menu flagNodeInPath = null;
    
    //private String dataPrivilege;
    
    private Integer systemType;
    
    //private Integer needOrgAndCopy;
    
    //private String idAndNeedFlag ;

	@Column( name = "iconName", nullable = true )
    public String getIconName() {
        return iconName;
    }

    public void setIconName( String iconName ) {
        this.iconName = iconName;
    }

    @Column( name = "disabled", nullable = false )
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled( boolean disabled ) {
        this.disabled = disabled;
    }

    @JSON(serialize = false)
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "rootId", nullable = true )
    public Menu getSubSystem() {
        return subSystem;
    }

    public void setSubSystem( Menu subSystem ) {
        this.subSystem = subSystem;
    }
    @JSON(serialize = false)
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "parentId", nullable = true )
    public Menu getParent() {
        return parent;
    }

    public void setParent( Menu parent ) {
        this.parent = parent;
    }

    @Id
    @Column( name = "menuId", length = 30 )
    public String getMenuId() {
        return this.menuId;
    }

    public void setMenuId( String menuId ) {
        this.menuId = menuId;
    }

    @Column( name = "menuName", nullable = false, length = 50 )
    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName( String menuName ) {
        this.menuName = menuName;
    }

    @Column( name = "menuAction", length = 5000 )
    public String getMenuAction() {
        return this.menuAction;
    }

    public void setMenuAction( String menuAction ) {
        this.menuAction = menuAction;
    }

    @Column( name = "leaf", nullable = false )
    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf( boolean leaf ) {
        this.leaf = leaf;
    }

    @Column( name = "menuLevel", nullable = false )
    public Long getMenuLevel() {
        return this.menuLevel;
    }

    public void setMenuLevel( Long menuLevel ) {
        this.menuLevel = menuLevel;
    }

    @Column( name = "displayOrder" )
    public Long getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder( Long displayOrder ) {
        this.displayOrder = displayOrder;
    }

    @Column( name = "entityName", length = 50 )
    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName( String entityName ) {
        this.entityName = entityName;
    }

    @JSON( serialize = false )
    @ManyToMany( fetch = FetchType.LAZY ,mappedBy="menus")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles( Set<Role> roles ) {
        this.roles = roles;
    }

    @JSON( serialize = false )
    @ManyToMany( fetch = FetchType.LAZY ,mappedBy="menus")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers( Set<User> users ) {
        this.users = users;
    }
    @Transient
    public String getParentName() {
    	try {
    		if(parent!=null){
        		parentName = parent.getMenuName();
        		
        	}else{
        		parentName = "";
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
    @Transient
	public String getSubSystemName() {
    	try {
			if(subSystem!=null){
				subSystemName = subSystem.getMenuName();
			}else{
				subSystemName = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subSystemName;
	}
    
    public void setSubSystemName(String subSystemName) {
		this.subSystemName = subSystemName;
	}
    
    @Transient
    public boolean getFlag() {
		return flag;
	}

    
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	@Transient
	public Menu getFlagNodeInPath() {
		if(this.getFlag()){
			flagNodeInPath = this;
		}else{
			Menu tempMenu = this.getParent();
			while(tempMenu!=null&&!tempMenu.getFlag()){
				tempMenu = tempMenu.getParent();
			}
			flagNodeInPath = tempMenu;
		}
		if(flagNodeInPath==null){
			flagNodeInPath = new Menu();
			flagNodeInPath.setMenuId("i am wrong menu");
		}
		return flagNodeInPath;
	}

	public void setFlagNodeInPath(Menu flagNodeInPath) {
		this.flagNodeInPath = flagNodeInPath;
	}

	/*@Column(name="dataPrivilege")
	public String getDataPrivilege() {
		return dataPrivilege;
	}

	public void setDataPrivilege(String dataPrivilege) {
		this.dataPrivilege = dataPrivilege;
	}*/
	
	@Column(name="systemType")
	public Integer getSystemType() {
		return systemType;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

	/*@Column(name="needOrgAndCopy")
	public Integer getNeedOrgAndCopy() {
		return needOrgAndCopy;
	}

	public void setNeedOrgAndCopy(Integer needOrgAndCopy) {
		this.needOrgAndCopy = needOrgAndCopy;
	}
	
	  @Transient
		public String getIdAndNeedFlag() {
			idAndNeedFlag = menuId+"_"+needOrgAndCopy;
			return idAndNeedFlag;
		}

		public void setIdAndNeedFlag(String idAndNeedFlag) {
			this.idAndNeedFlag = idAndNeedFlag;
		}*/
    @Override
    public String toString() {
        return "Menu [menuId=" + menuId + ", menuName=" + menuName + ", menuAction=" + menuAction + ", leaf=" + leaf + ", menuLevel=" + menuLevel
            + ", displayOrder=" + displayOrder + ", entityName=" + entityName + ", iconName=" + iconName + ", disabled=" + disabled + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( displayOrder == null ) ? 0 : displayOrder.hashCode() );
        result = prime * result + ( ( entityName == null ) ? 0 : entityName.hashCode() );
        result = prime * result + ( leaf ? 1231 : 1237 );
        result = prime * result + ( ( menuAction == null ) ? 0 : menuAction.hashCode() );
        result = prime * result + ( ( menuId == null ) ? 0 : menuId.hashCode() );
        result = prime * result + ( ( menuLevel == null ) ? 0 : menuLevel.hashCode() );
        result = prime * result + ( ( menuName == null ) ? 0 : menuName.hashCode() );
        result = prime * result + ( ( parent == null ) ? 0 : parent.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Menu other = (Menu) obj;
        if ( displayOrder == null ) {
            if ( other.displayOrder != null )
                return false;
        }
        else if ( !displayOrder.equals( other.displayOrder ) )
            return false;
        if ( entityName == null ) {
            if ( other.entityName != null )
                return false;
        }
        else if ( !entityName.equals( other.entityName ) )
            return false;
        if ( leaf != other.leaf )
            return false;
        if ( menuAction == null ) {
            if ( other.menuAction != null )
                return false;
        }
        else if ( !menuAction.equals( other.menuAction ) )
            return false;
        if ( menuId == null ) {
            if ( other.menuId != null )
                return false;
        }
        else if ( !menuId.equals( other.menuId ) )
            return false;
        if ( menuLevel == null ) {
            if ( other.menuLevel != null )
                return false;
        }
        else if ( !menuLevel.equals( other.menuLevel ) )
            return false;
        if ( menuName == null ) {
            if ( other.menuName != null )
                return false;
        }
        else if ( !menuName.equals( other.menuName ) )
            return false;
        if ( parent == null ) {
            if ( other.parent != null )
                return false;
        }
        else if ( !parent.equals( other.parent ) )
            return false;
        return true;
    }

	@Override
	public int compareTo(Menu arg0) {
		if(arg0.menuId==null){
			return 0;
		}
		String[] twoMenuId = {menuId,arg0.menuId};
		Arrays.sort(twoMenuId);
		if(menuId.equals(arg0.menuId))
			return 0;
		else if(!twoMenuId[0].equals(menuId))
            return 1;
		else
	        return -1;
	}
}
