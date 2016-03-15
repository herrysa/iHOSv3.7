package com.huge.dao.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import com.huge.dao.BaseTreeDao;
import com.huge.model.BaseTreeNode;

public abstract class BaseTreeDaoHibernate<T extends BaseTreeNode, PK extends Serializable>
    extends GenericDaoHibernate<T, PK>
    implements BaseTreeDao<T, PK> {

    public BaseTreeDaoHibernate( Class<T> persistentClass ) {
        super( persistentClass );
    }

    private void increaseleftAndRight( int span, int lft ) {

        String sur = "from " + this.getPersistentClass().getSimpleName() + " where rgt>?";
        String sul = "from " + this.getPersistentClass().getSimpleName() + " where lft>?";

        List rl = this.getHibernateTemplate().find( sur, lft );
        List ll = this.getHibernateTemplate().find( sul, lft );

        for ( Iterator iterator = ll.iterator(); iterator.hasNext(); ) {
            T t = (T) iterator.next();
            t.setLft( t.getLft() + span );
            this.save( t );
        }
        for ( Iterator iterator = rl.iterator(); iterator.hasNext(); ) {
            T t = (T) iterator.next();
            t.setRgt( t.getRgt() + span );
            this.save( t );
        }

    }

    private void decreaseleftAndRight( int span, int lft ) {

        String sur = "from " + this.getPersistentClass().getSimpleName() + " where rgt>?";
        String sul = "from " + this.getPersistentClass().getSimpleName() + " where lft>?";

        List rl = this.getHibernateTemplate().find( sur, lft );
        List ll = this.getHibernateTemplate().find( sul, lft );

        for ( Iterator iterator = ll.iterator(); iterator.hasNext(); ) {
            T t = (T) iterator.next();
            t.setLft( t.getLft() - span );
            this.save( t );
        }
        for ( Iterator iterator = rl.iterator(); iterator.hasNext(); ) {
            T t = (T) iterator.next();
            t.setRgt( t.getRgt() - span );
            this.save( t );
        }

    }

    @Override
    public T insertNode( T node ) {
        BaseTreeNode parent = node.getParentNode();
        if ( parent != null ) {
            parent = super.get( (PK) parent.getId() );
            this.increaseleftAndRight( 2, parent.getLft() );
            node.setLft( parent.getLft() + 1 );
            node.setRgt( parent.getLft() + 2 );
            node.setLevel( parent.getLevel() + 1 );
            return super.save( node );
        }
        else {
            return super.save( node );
        }
    }

    @Override
    public void delete( PK nodeId ) {

        String selChildren = " from " + this.getPersistentClass().getSimpleName() + " where (lft > ? and lft <?) and rgt=lft+1";
        BaseTreeNode node = this.get( nodeId );
        int lft = node.getLft(), rgt = node.getRgt(), span = rgt - lft + 1;

        int childrenCount = getChildrenCount( nodeId );
        while ( childrenCount > 0 ) {

            this.getHibernateTemplate().deleteAll( this.getHibernateTemplate().find( selChildren, node.getLft(), node.getRgt() ) );
            this.reBuildTree( nodeId, node.getLft() );
            node = this.get( nodeId );
            childrenCount = getChildrenCount( nodeId );
        }
        this.remove( nodeId );
        this.decreaseleftAndRight( span, lft );

    }

    @Override
    public int getChildrenCount( PK nodeId ) {
        BaseTreeNode node = this.get( nodeId );
        String hql = "select count(id) from " + this.getPersistentClass().getSimpleName() + " where (lft > ? and lft <?)";
        List l = this.getHibernateTemplate().find( hql, node.getLft(), node.getRgt() );
        int count = ( (Long) l.get( 0 ) ).intValue();
        return count;
    }

    private void moveIncreaseleftAndRight( int span, int lft, int rgt ) {

        // String sur = "from " +
        // this.getPersistentClass().getSimpleName() + " where rgt>?";
        // String sul = "from " +
        // this.getPersistentClass().getSimpleName() + " where lft>?";
        String hql = "from " + this.getPersistentClass().getSimpleName() + " WHERE lft > ? and lft<?";
        // List l = this.getHibernateTemplate().find(sur, lft);
        List ll = this.getHibernateTemplate().find( hql, lft, rgt );

        for ( Iterator iterator = ll.iterator(); iterator.hasNext(); ) {
            T t = (T) iterator.next();
            t.setLft( t.getLft() + span );
            t.setRgt( t.getRgt() + span );
            this.save( t );
        }

    }

    private void moveDecreaseleftAndRight( int span, int lft ) {
        String sur = "from " + this.getPersistentClass().getSimpleName() + " where rgt>?";
        String sul = "from " + this.getPersistentClass().getSimpleName() + " where lft>?";
        List rl = this.getHibernateTemplate().find( sur, lft );
        List ll = this.getHibernateTemplate().find( sul, lft );
        for ( Iterator iterator = ll.iterator(); iterator.hasNext(); ) {
            T t = (T) iterator.next();
            t.setLft( t.getLft() - span );
            this.save( t );
        }
        for ( Iterator iterator = rl.iterator(); iterator.hasNext(); ) {
            T t = (T) iterator.next();
            t.setRgt( t.getRgt() - span );
            this.save( t );
        }
    }

    @Override
    public void move( PK nodeId, PK targetParentId ) {
        /*
         * this.debugDisplayTree((PK) this.getAllRoots().get(0).getId()); // 获得节点跨度 T node = this.get(nodeId); T
         * targetParentNode = this.get(targetParentId); int lft = node.getLft(), rgt = node.getRgt(), span = rgt - lft +
         * 1; // 获得当前父节点右位置 int targetParentRight = targetParentNode.getRgt(); // 先空出位置 String ur =
         * "update DemoTreeNode b set b.rgt=b.rgt+? where b.rgt>=?"; String ul =
         * "update DemoTreeNode b set b.lft=b.lft+? where b.lft>=?"; int r1 = this.getHibernateTemplate().bulkUpdate(ur,
         * span, targetParentRight); int r2 = this.getHibernateTemplate().bulkUpdate(ul, span, targetParentRight);
         * targetParentNode.setRgt(targetParentNode.getRgt() + span); targetParentNode = super.save(targetParentNode);
         * this.increaseleftAndRight(span, targetParentRight); this.debugDisplayTree((PK)
         * this.getAllRoots().get(0).getId()); // 再调整自己 int offset = targetParentRight - lft; String hql =
         * "update DemoTreeNode b set b.lft=b.lft+?, b.rgt=b.rgt+? WHERE b.lft between ? and ?" ; int r3 =
         * this.getHibernateTemplate().bulkUpdate(hql, offset, offset, lft, rgt); node.setLft(node.getLft() + offset);
         * node.setRgt(node.getRgt() + offset); node.setParentNode(targetParentNode); super.save(node);
         * this.moveIncreaseleftAndRight(offset, lft, rgt); node.setParentNode(targetParentNode); super.save(node);
         * this.debugDisplayTree((PK) this.getAllRoots().get(0).getId()); // 最后删除（清空位置） String hql1 =
         * "update DemoTreeNode b set b.rgt = b.rgt- ? where b.rgt >= ?" ; String hql2 =
         * "update DemoTreeNode b set b.lft = b.lft - ? wher b.lft >= ?" ; int r4 =
         * this.getHibernateTemplate().bulkUpdate(ur, span, rgt); int r5 = this.getHibernateTemplate().bulkUpdate(ul,
         * span, rgt); this.decreaseleftAndRight(span, rgt); this.debugDisplayTree((PK)
         * this.getAllRoots().get(0).getId());
         */

        T node = this.get( nodeId );
        BaseTreeNode targetParentNode = this.get( targetParentId );
        node.setParentNode( targetParentNode );
        this.save( node );
        BaseTreeNode root = this.getRoot( targetParentId );
        this.reBuildTree( (PK) root.getId(), root.getLft() );

    }

    @Override
    public void debugDisplayTree( PK id ) {
        BaseTreeNode root = this.get( id );
        List children = this.getTreeByNodeId( id );
        Stack<Integer> stack = new Stack<Integer>();
        for ( Iterator iterator = children.iterator(); iterator.hasNext(); ) {
            BaseTreeNode node = (BaseTreeNode) iterator.next();
            if ( !stack.isEmpty() ) {
                while ( stack.lastElement() < node.getRgt() ) {
                    stack.pop();
                }
            }

            System.err.println( StringUtils.repeat( "    ", stack.size() ) + node.getId() + " " + node.getParent() + " " + node.getLft() + " "
                + node.getRgt() + " " + node.getLevel() );
            stack.push( node.getRgt() );
        }
    }

    @Override
    public T getRoot( PK id ) {
        List path = this.getPathToNode( id, true );
        return (T) path.get( 0 );
    }

    @Override
    public List<T> getAllRoots() {
        String hql = "from " + this.getPersistentClass().getSimpleName() + " where parentNode.id is null order by id desc";
        return this.getHibernateTemplate().find( hql );
    }

    @Override
    public int reBuildTree( PK id, int leftNmuber ) {
        int rightNumber = leftNmuber + 1;
        T curNode = this.get( id );
        List children = this.getImmediateDescendant( id );
        Iterator itr = children.iterator();
        while ( itr.hasNext() ) {
            T node = (T) itr.next();
            rightNumber = reBuildTree( (PK) node.getId(), rightNumber );
        }
        curNode.setLft( leftNmuber );
        curNode.setRgt( rightNumber );
        curNode.setLevel( getNodeInitLevel( (PK) curNode.getId() ) );
        this.save( curNode );
        return rightNumber + 1;
    }

    private int getNodeInitLevel( PK id ) {
        BaseTreeNode n = this.get( id );
        BaseTreeNode p = n.getParentNode();
        int l = 0;
        if ( p == null )
            return l;
        else {
            while ( p != null ) {
                l++;
                p = p.getParentNode();

            }
        }
        return l;
    }

    @Override
    public List<T> getTreeByNodeId( PK rootId ) {
        T node = this.get( rootId );
        return getTreeByNestNubmer( node.getLft(), node.getRgt() );
    }

    private List<T> getTreeByNestNubmer( int lft, int rgt ) {
        String hql = "from " + this.getPersistentClass().getSimpleName() + " where lft>=? AND lft<=? ORDER BY lft ASC";
        List<T> l = this.getHibernateTemplate().find( hql, lft, rgt );
        return l;
    }

    @Override
    public List<T> getPathToNode( PK nodeId, boolean includeCurrent ) {
        BaseTreeNode node = this.get( nodeId );
        String hql = null;
        if ( includeCurrent )
            hql = "from " + this.getPersistentClass().getSimpleName() + " where lft <= ? AND rgt>=? ORDER BY lft ASC";
        else
            hql = "from " + this.getPersistentClass().getSimpleName() + " where lft < ? AND rgt>? ORDER BY lft ASC";

        List<T> l = this.getHibernateTemplate().find( hql, node.getLft(), node.getRgt() );
        return l;
    }

    @Override
    public List<T> getAllLeafDescendant( PK nodeId ) {
        BaseTreeNode node = this.get( nodeId );
        String hql = "from " + this.getPersistentClass().getSimpleName() + " where (lft BETWEEN ? AND ?) and rgt=(lft+1) ORDER BY lft ASC";
        List<T> l = this.getHibernateTemplate().find( hql, node.getLft(), node.getRgt() );
        return l;
    }

    @Override
    public List<T> getAllDescendant( PK nodeId ) {
        BaseTreeNode node = this.get( nodeId );
        String hql = "from " + this.getPersistentClass().getSimpleName() + " where (lft>? AND lft<?) ORDER BY lft ASC";
        List<T> l = this.getHibernateTemplate().find( hql, node.getLft(), node.getRgt() );
        return l;
    }

    @Override
    public List<T> getImmediateDescendant( PK nodeId ) {
        String hql = "from  " + this.getPersistentClass().getSimpleName() + "  where parentNode.id=? ORDER BY lft ASC";
        List<T> l = this.getHibernateTemplate().find( hql, nodeId );
        return l;
    }

    @Override
    public List<T> getImmediateLeafDescendant( PK nodeId ) {
        BaseTreeNode node = this.get( nodeId );
        String hql = "from   " + this.getPersistentClass().getSimpleName() + "  where  (rgt=(lft+1)) and parentNode.id=? ORDER BY lft ASC";
        List<T> l = this.getHibernateTemplate().find( hql, nodeId );
        return l;
    }

    @Override
    public List<T> getImmediateUnLeafDescendant( PK nodeId ) {
        BaseTreeNode node = this.get( nodeId );
        String hql = "from   " + this.getPersistentClass().getSimpleName() + "  where (rgt!=(lft+1)) and parentNode.id=? ORDER BY lft ASC";
        List<T> l = this.getHibernateTemplate().find( hql, nodeId );
        return l;
    }

    @Override
    public List<T> getAllNodeAtLevel( int level ) {
        String hql = "from " + this.getPersistentClass().getSimpleName() + " where level=? ORDER BY lft ASC";
        List<T> l = this.getHibernateTemplate().find( hql, level );
        return l;
    }

    @Override
    public List<T> getAllNodeAtLevelUnderNode( PK nodeId, int level ) {
        BaseTreeNode node = this.get( nodeId );
        String hql = "from " + this.getPersistentClass().getSimpleName() + " where level=? and (lft BETWEEN ? AND ?)  ORDER BY lft ASC";
        List<T> l = this.getHibernateTemplate().find( hql, level, node.getLft(), node.getRgt() );
        return l;
    }

    @Override
    public List<T> getAllLeafNodeAtLevelUnderNode( PK nodeId, int level ) {
        BaseTreeNode node = this.get( nodeId );
        String hql =
            "from " + this.getPersistentClass().getSimpleName() + " where level=? and  (lft BETWEEN ? AND ?) and (rgt=(lft+1))  ORDER BY lft ASC";
        List<T> l = this.getHibernateTemplate().find( hql, level, node.getLft(), node.getRgt() );
        return l;
    }

    @Override
    public List<T> getAllUnLeafNodeAtLevelUnderNode( PK nodeId, int level ) {
        BaseTreeNode node = this.get( nodeId );
        String hql =
            "from " + this.getPersistentClass().getSimpleName() + " where level=? and  (lft BETWEEN ? AND ?) and (rgt!=(lft+1))  ORDER BY lft ASC";
        List<T> l = this.getHibernateTemplate().find( hql, level, node.getLft(), node.getRgt() );
        return l;
    }
}
