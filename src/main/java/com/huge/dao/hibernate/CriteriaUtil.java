package com.huge.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.Subcriteria;

import com.huge.util.DateUtil;

public class CriteriaUtil {

    public static Criteria createAliasCriteria( Criteria prim, String field ) {
        /*	
        	String path = null;
        	try {
        		path = field.substring(0, field.lastIndexOf("."));
        	} catch (Exception e) {
        		path = null;
        	}
        	
        	if (path == null)
        		return prim;
        	else {
        		
        		String[] fields = field.split("\\.");
        		String[] alPathes = getAliasPath(field);
        		for (int i = 0; i < alPathes.length; i++) {
        			boolean aliasExist = false;
        			Iterator itr = ((CriteriaImpl) prim).iterateSubcriteria();
        			while (itr.hasNext()) {
        				Subcriteria ci = (Subcriteria) itr.next();
        				if (ci.getAlias().equals(alPathes[i]))
        				{
        					aliasExist = true;
        					break;
        				}
        			}
        			if (!aliasExist)
        				prim.createAlias(alPathes[i], fields[i]);
        		}
        	}
        	return prim;*/
        return createAliasCriteria( prim, field, -1 );
    }

    public static Criteria createAliasCriteria( Criteria prim, String field, int joinType ) {

        String path = null;
        try {
            path = field.substring( 0, field.lastIndexOf( "." ) );
        }
        catch ( Exception e ) {
            path = null;
        }

        if ( path == null )
            return prim;
        else {

            String[] fields = field.split( "\\." );
            String[] alPathes = getAliasPath( field );
            for ( int i = 0; i < alPathes.length; i++ ) {
                boolean aliasExist = false;
                Iterator itr = ( (CriteriaImpl) prim ).iterateSubcriteria();
                while ( itr.hasNext() ) {
                    Subcriteria ci = (Subcriteria) itr.next();
                    if ( ci.getAlias().equals( fields[i] ) ) {
                        aliasExist = true;
                        break;
                    }
                }
                if ( !aliasExist ) {
                    if ( joinType == CriteriaSpecification.LEFT_JOIN || joinType == CriteriaSpecification.FULL_JOIN
                        || joinType == CriteriaSpecification.INNER_JOIN ) {
                        prim.createAlias( alPathes[i], fields[i], joinType );
                    }
                    else {
                        prim.createAlias( alPathes[i], fields[i] );
                    }
                }
            }
        }
        return prim;
    }

    private static String[] getAliasPath( String field ) {
        String[] sf = field.split( "\\." );
        List list = new ArrayList();
        String path = null;
        for ( int i = 0; i < sf.length - 1; i++ ) {
            for ( int j = 0; j <= i; j++ ) {
                if ( j == 0 )
                    path = sf[j];
                else
                    path = path + "." + sf[j];
            }
            list.add( path );
        }

        String[] al = new String[list.size()];

        list.toArray( al );
        return al;
    }

    public static String getSearchAliasFieldname( String field ) {
        String[] sf = field.split( "\\." );
        if ( sf.length <= 1 )
            return field;
        else
            return sf[sf.length - 2] + "." + sf[sf.length - 1];
    }

    /**
     * ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
     * 
     * ['equal','not equal', 'less', 'less or equal','greater','greater or
     * equal', 'begins with','does not begin with','is in','is not in', 'ends
     * with','does not end with','contains','does not contain']
     * 
     * @throws Exception
     */
    public static Criterion createCriterion( Class clazz, String field, String oper, String data )
        throws Exception {
        try {

            Class fieldType = null;
            // fieldType = clazz.getDeclaredField(field).getType();
            // String fieldTypeName = fieldType.getName();
            // Object fieldobj = fieldType.newInstance();
            fieldType = getFieldTypeName( clazz, field );
            String fieldTypeName = fieldType.getName();
            if ( fieldTypeName.equals( "java.lang.String" ) ) {
                field = getSearchAliasFieldname( field );
                return createStringCriterion( field, oper, data );
            }
            if ( fieldTypeName.equals( "java.util.Date" ) ) {
                Object obj = DateUtil.convertStringToDate( data );
                // Object obj = new Date();
                field = getSearchAliasFieldname( field );
                return createDateCriterion( field, oper, obj );
            }
            if ( fieldTypeName.equals( "java.lang.Boolean" ) ) {
                Object obj = new BooleanConverter().convert( fieldType, data );
                field = getSearchAliasFieldname( field );
                return createBooleanCriterion( field, oper, obj );
            }
            if ( fieldTypeName.equals( "java.lang.Integer" ) ) {
                Object obj = new IntegerConverter().convert( fieldType, data );
                field = getSearchAliasFieldname( field );
                return createNumberCriterion( field, oper, obj );
            }
            if ( fieldTypeName.equals( "java.lang.Double" ) ) {
                Object obj = new DoubleConverter().convert( fieldType, data );
                field = getSearchAliasFieldname( field );
                return createNumberCriterion( field, oper, obj );
            }
            if ( fieldTypeName.equals( "java.lang.Float" ) ) {
                Object obj = new FloatConverter().convert( fieldType, data );
                field = getSearchAliasFieldname( field );
                return createNumberCriterion( field, oper, obj );
            }
            if ( fieldTypeName.equals( "java.lang.Long" ) ) {
                Object obj = new LongConverter().convert( fieldType, data );
                field = getSearchAliasFieldname( field );
                return createNumberCriterion( field, oper, obj );
            }

        }
        catch ( Exception e ) {
            throw e;
        }

        return null;
    }

    private static Class getFieldTypeName( Class clazz, String field )
        throws SecurityException, NoSuchFieldException {
        Class fieldType = null;
        String fieldTypeName;
        String[] sf = field.split( "\\." );
        for ( int i = 0; i < sf.length; i++ ) {
            fieldType = clazz.getDeclaredField( sf[i] ).getType();
            clazz = fieldType;
            // fieldTypeName = fieldType.getName();
        }
        fieldTypeName = fieldType.getName();
        return fieldType;
    }

    /**
     * ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bw', 'bn', 'in', 'ni', 'ew', 'en',
     * 'cn', 'nc'] [等于, 不等于, 小于, 小于等于,大于, 大于等于,开始于, 不开始于,在内, 不在内, 结束于, 不结束于,包含,
     * 不包含]
     * 
     */
    private static Criterion createStringCriterion( String field, String oper, Object data ) {
        if ( oper.equalsIgnoreCase( "eq" ) ) {
            return Restrictions.eq( field, data ).ignoreCase();
        }
        else if ( oper.equalsIgnoreCase( "ne" ) ) {
            return Restrictions.ne( field, data ).ignoreCase();
        }
        else if ( oper.equalsIgnoreCase( "bw" ) ) {
            data = data + "%";
            return Restrictions.like( field, data ).ignoreCase();
        }
        else if ( oper.equalsIgnoreCase( "ew" ) ) {
            data = "%" + data;
            return Restrictions.like( field, data ).ignoreCase();
        }
        else if ( oper.equalsIgnoreCase( "cn" ) ) {
            data = "%" + data + "%";
            return Restrictions.like( field, data ).ignoreCase();
        }

        return null;
    }

    /**
     * ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bw', 'bn', 'in', 'ni', 'ew',
     * 'en','cn', 'nc'] [等于, 不等于, 小于, 小于等于,大于, 大于等于,开始于, 不开始于,在内, 不在内, 结束于,
     * 不结束于,包含, 不包含]
     * 
     */
    private static Criterion createDateCriterion( String field, String oper, Object data ) {
        if ( oper.equalsIgnoreCase( "eq" ) ) {
            return Restrictions.eq( field, data );
        }
        else if ( oper.equalsIgnoreCase( "ne" ) ) {
            return Restrictions.ne( field, data );
        }
        else if ( oper.equalsIgnoreCase( "lt" ) ) {
            return Restrictions.lt( field, data );
        }
        else if ( oper.equalsIgnoreCase( "le" ) ) {
            return Restrictions.le( field, data );
        }
        else if ( oper.equalsIgnoreCase( "gt" ) ) {
            return Restrictions.gt( field, data );
        }
        else if ( oper.equalsIgnoreCase( "ge" ) ) {
            return Restrictions.ge( field, data );
        }

        return null;
    }

    /**
     * ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'bw', 'bn', 'in', 'ni', 'ew',
     * 'en','cn', 'nc'] [等于, 不等于, 小于, 小于等于,大于, 大于等于,开始于, 不开始于,在内, 不在内, 结束于,
     * 不结束于,包含, 不包含]
     * 
     */
    private static Criterion createBooleanCriterion( String field, String oper, Object data ) {
        if ( oper.equalsIgnoreCase( "eq" ) ) {
            return Restrictions.eq( field, data );
        }
        else if ( oper.equalsIgnoreCase( "ne" ) ) {
            return Restrictions.ne( field, data );
        }
        return null;
    }

    private static Criterion createNumberCriterion( String field, String oper, Object data ) {
        if ( oper.equalsIgnoreCase( "eq" ) ) {
            return Restrictions.eq( field, data );
        }
        else if ( oper.equalsIgnoreCase( "ne" ) ) {
            return Restrictions.ne( field, data );
        }
        else if ( oper.equalsIgnoreCase( "lt" ) ) {
            return Restrictions.lt( field, data );
        }
        else if ( oper.equalsIgnoreCase( "le" ) ) {
            return Restrictions.le( field, data );
        }
        else if ( oper.equalsIgnoreCase( "gt" ) ) {
            return Restrictions.gt( field, data );
        }
        else if ( oper.equalsIgnoreCase( "ge" ) ) {
            return Restrictions.ge( field, data );
        }

        return null;
    }
}
