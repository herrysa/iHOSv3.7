package com.huge.dao;

import java.io.Serializable;
import java.util.List;

import com.huge.model.BaseTreeNode;

/**
 * 约定: 根节点的level为0 根节点没有父亲为null 根节点的lft值为1
 */
public interface BaseTreeDao<T extends BaseTreeNode, PK extends Serializable> {
	T get(PK id);
	 T save(T node);
	List<T> getAll();

	/**
	 * 插入节点,要负责维护插入后的nested关系
	 */
	public T insertNode(T node);

	/**
	 * 删除节点,并负责维护节点删除操作后的nest关系
	 */
	public void delete(PK nodeId);
	/**
	 * 移动节点到指定节点下,并负责维护节点删除操作后的nest关系
	 */
	public void move(PK nodeId, PK targetParentId);
	/**
	 * 在控制台 文本输出以此节点为根的树结构,用于调试
	 */
	public void debugDisplayTree(PK id);

	/**
	 * 获取节点所在树的根节点
	 */
	public T getRoot(PK id);

	/**
	 * 获取所有的根节点
	 */
	public List<T> getAllRoots();

	/**
	 * 重建已id为根的数的nested set树型关系 lefterNumber为根节点的左侧加权值
	 */
	public int reBuildTree(PK id, int leftNmuber);

	/**
	 * 获得已id节点为根的树集合
	 */
	public List<T> getTreeByNodeId(PK rootId);

	/**
	 * 从本树 根节点到指定节点全路径节点列表，index=0的是根节点 includeCurrent:本节点是否包含在路径列表中
	 */
	public List<T> getPathToNode(PK nodeId, boolean includeCurrent);

	/**
	 * Ancestor 先辈 descendant 后裔 获取全部的叶子类型的后裔
	 */
	public List<T> getAllLeafDescendant(PK nodeId);

	/**
	 * 获得所有的后裔节点,不论是叶子节点与否
	 */
	public List<T> getAllDescendant(PK nodeId);

	/**
	 * 获取所有直接子节点
	 */
	public List<T> getImmediateDescendant(PK nodeId);

	/**
	 * 获取所有叶子类型的直接子节点
	 */

	public List<T> getImmediateLeafDescendant(PK nodeId);

	/**
	 * 获取所有的非叶子类型的直接子节点
	 */
	public List<T> getImmediateUnLeafDescendant(PK nodeId);
	/**
	 * 获取指定级别的所有节点
	 */
	public List<T> getAllNodeAtLevel(int level);
	/**
	 * 获取指定节点下指定级别的所有子节点
	 */
	public List<T> getAllNodeAtLevelUnderNode(PK nodeId, int level);

	/**
	 * 获取指定节点下指定级别的所有叶子类型子节点
	 */
	public List<T> getAllLeafNodeAtLevelUnderNode(PK nodeId, int level);

	/**
	 * 获取指定节点下指定级别的所有非叶子类型子节点
	 */
	public List<T> getAllUnLeafNodeAtLevelUnderNode(PK nodeId, int level);

	public int getChildrenCount(PK nodeId);

}
