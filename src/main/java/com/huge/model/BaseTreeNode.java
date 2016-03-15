package com.huge.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author Administrator
 *
 * @param <T>
 * @param <PK>
 */
@MappedSuperclass
public  class BaseTreeNode<T extends BaseTreeNode, PK extends Serializable>
		extends
			BaseObject {
	/**
	 *
	 */
	private PK id;
	/**
	 *
	 */
	private T parentNode;
	/**
	 *
	 */
	private int lft;
	/**
	 *
	 */
	private int rgt;
	/**
	 *
	 */
	private int level;

	/**
	 * @return
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public  PK getId() {
		return id;
	}

	/**
	 * @param nodeId
	 */
	public  void setId( PK nodeId) {
		this.id = nodeId;
	}

	/**
	 * @return
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id", nullable = true)
	public  T getParentNode() {
		return parentNode;
	}

	/**
	 * @param parentNode
	 */
	public  void setParentNode( T parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * @return
	 */
	@Column(name = "lft", columnDefinition = "int default 1")
	public  int getLft() {
		return lft;
	}

	/**
	 * @param lft
	 */
	public  void setLft( int lft) {
		this.lft = lft;
	}

	/**
	 * @return
	 */
	@Column(name = "rgt", columnDefinition = "int default 1")
	public  int getRgt() {
		return rgt;
	}

	/**
	 * @param rgt
	 */
	public  void setRgt( int rgt) {
		this.rgt = rgt;
	}

	/**
	 * @return
	 */
	@Column(name = "level", columnDefinition = "int default 0")
	public  int getLevel() {
		return level;
	}

	/**
	 * @param level
	 */
	public  void setLevel( int level) {
		this.level = level;
	}

	/**
	 * @return
	 */
	@Transient
	public  String getParent() {
		if (this.getParentNode() != null) {
			return this.getParentNode().getId() + "";
		} else {
			return null;
		}
	}

	/**
	 * @return
	 */
	@Transient
	public  boolean getIsLeaf() {
		return (this.getRgt() == (this.getLft() + 1)) ? true : false;
	}

	/**
	 * @return
	 */
	@Transient
	public  boolean getIsParent() {
		/* return (this.getRgt() == (this.getLft() + 1)) ? false : true; */
		return !this.getIsLeaf();
	}

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals( Object o ) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }
}
