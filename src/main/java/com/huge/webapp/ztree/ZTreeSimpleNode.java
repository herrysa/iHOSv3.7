package com.huge.webapp.ztree;

public class ZTreeSimpleNode {
    private String id;

    private String pId;

    private String name;

    private boolean open;

    public boolean isParent;

    private String url;

    private String icon;

    private String subSysTem;
    
    private Boolean disCheckAble = false;

    public String getSubSysTem() {
        return subSysTem;
    }

    public void setSubSysTem( String subSysTem ) {
        this.subSysTem = subSysTem;
    }

    public String getActionUrl() {
        return url;
    }

    public void setActionUrl( String url ) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon( String icon ) {
        this.icon = icon;
    }

    public void setParent( boolean isParent ) {
        this.isParent = isParent;
    }

    public String getpId() {
        return pId;
    }

    public void setpId( String pId ) {
        this.pId = pId;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent( boolean isParent ) {
        this.isParent = isParent;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen( boolean open ) {
        this.open = open;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        result = prime * result + ( isParent ? 1231 : 1237 );
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        result = prime * result + ( open ? 1231 : 1237 );
        result = prime * result + ( ( pId == null ) ? 0 : pId.hashCode() );
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
        ZTreeSimpleNode other = (ZTreeSimpleNode) obj;
        if ( id == null ) {
            if ( other.id != null )
                return false;
        }
        else if ( !id.equals( other.id ) )
            return false;
        if ( isParent != other.isParent )
            return false;
        if ( name == null ) {
            if ( other.name != null )
                return false;
        }
        else if ( !name.equals( other.name ) )
            return false;
        if ( open != other.open )
            return false;
        if ( pId == null ) {
            if ( other.pId != null )
                return false;
        }
        else if ( !pId.equals( other.pId ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ZTreeSimpleNode [id=" + id + ", pId=" + pId + ", name=" + name + ", open=" + open + ", isParent=" + isParent + "]";
    }

	public Boolean getDisCheckAble() {
		return disCheckAble;
	}

	public void setDisCheckAble(Boolean disCheckAble) {
		this.disCheckAble = disCheckAble;
	}
}
