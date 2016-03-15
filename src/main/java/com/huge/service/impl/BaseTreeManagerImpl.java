package com.huge.service.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.huge.dao.BaseTreeDao;
import com.huge.dao.GenericDao;
import com.huge.model.BaseTreeNode;
import com.huge.service.BaseTreeManager;

public abstract class BaseTreeManagerImpl<T extends BaseTreeNode, PK extends Serializable> extends GenericManagerImpl<T,PK>
		implements
			BaseTreeManager<T, PK> {
	BaseTreeDao treeDao;

	public BaseTreeManagerImpl(BaseTreeDao<T, PK> genericDao) {
	    super((GenericDao<T, PK>) genericDao);
		this.treeDao = genericDao;
	}

	@Override
	public T insertNode(T node) {

		/*
		 * BaseTreeNode parent = node.getParentNode(); if (parent !=
		 * null) { parent = this.get((PK) parent.getId());
		 * treeDao.increaseleftAndRight(parent.getLft());
		 * parent.setRgt(parent.getRgt() + 2);
		 * node.setLft(parent.getLft() + 1); node.setRgt(parent.getLft()
		 * + 2); node.setLevel(parent.getLevel() + 1); return (T)
		 * treeDao.insertNode(node); } else { // do nothing
		 * node.setRgt(node.getLft() + 1); return (T)
		 * treeDao.insertNode(node); }
		 */

		return (T) this.treeDao.insertNode(node);
	}

	@Override
	public void delete(PK nodeId) {
		this.treeDao.delete(nodeId);
	}

	@Override
	public void move(PK nodeId, PK targetParentId) {
		this.treeDao.move(nodeId, targetParentId);
	}

	@Override
	public void debugDisplayTree(PK id) {
		this.treeDao.debugDisplayTree(id);
	}

	@Override
	public T getRoot(PK id) {
		return (T) this.treeDao.get(id);
	}

	@Override
	public List<T> getAllRoots() {
		return this.treeDao.getAllRoots();
	}

	@Override
	public int reBuildTree(PK id, int leftNmuber) {
		return this.treeDao.reBuildTree(id, leftNmuber);
	}

	@Override
	public List<T> getTreeByNodeId(PK rootId) {
		return this.treeDao.getTreeByNodeId(rootId);
	}

	@Override
	public List<T> getPathToNode(PK nodeId, boolean includeCurrent) {
		return this.treeDao.getPathToNode(nodeId, includeCurrent);
	}

	@Override
	public List<T> getAllLeafDescendant(PK nodeId) {
		return this.treeDao.getAllLeafDescendant(nodeId);
	}

	@Override
	public List<T> getAllDescendant(PK nodeId) {
		return this.treeDao.getAllDescendant(nodeId);
	}

	@Override
	public List<T> getImmediateDescendant(PK nodeId) {
		return this.treeDao.getImmediateDescendant(nodeId);
	}

	@Override
	public List<T> getImmediateLeafDescendant(PK nodeId) {
		return this.treeDao.getImmediateLeafDescendant(nodeId);
	}

	@Override
	public List<T> getImmediateUnLeafDescendant(PK nodeId) {
		return this.treeDao.getImmediateUnLeafDescendant(nodeId);
	}

	@Override
	public List<T> getAllNodeAtLevel(int level) {
		return this.treeDao.getAllNodeAtLevel(level);
	}

	@Override
	public List<T> getAllNodeAtLevelUnderNode(PK nodeId, int level) {
		return this.treeDao.getAllNodeAtLevelUnderNode(nodeId, level);
	}

	@Override
	public List<T> getAllLeafNodeAtLevelUnderNode(PK nodeId, int level) {
		return this.treeDao.getAllLeafNodeAtLevelUnderNode(nodeId, level);
	}

	@Override
	public List<T> getAllUnLeafNodeAtLevelUnderNode(PK nodeId, int level) {
		return this.treeDao.getAllUnLeafNodeAtLevelUnderNode(nodeId, level);
	}

	@Override
	public int getChildrenCount(PK nodeId) {
		return this.treeDao.getChildrenCount(nodeId);
	}

	@Override
	public T get(PK id) {
		return (T) this.treeDao.get(id);
	}

	@Override
	public List<T> getAll() {
		return this.treeDao.getAll();
	}

	@Override
	public void reBuildAllTree() {
		List rs = this.treeDao.getAllRoots();
		for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
			T object = (T) iterator.next();
			this.treeDao.reBuildTree(object.getId(), object.getLft());
		}

	}





}
