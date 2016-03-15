/*
 * 
 */
package com.huge.ihos.system.systemManager.security;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service( "AccessDecisionManager" )
public class AccessDecisionManager
    implements org.springframework.security.access.AccessDecisionManager {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger( AccessDecisionManager.class );

    // In this method, need to compare authentication with configAttributes.
    // 1, A object is a URL, a filter was find permission configuration by this
    // URL, and pass to here.
    // 2, Check authentication has attribute in permission configuration
    // (configAttributes)
    // 3, If not match corresponding authentication, throw a
    // AccessDeniedException.

    public void decide( Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes )
        throws AccessDeniedException, InsufficientAuthenticationException {
        if ( logger.isDebugEnabled() ) {
            logger.debug( "decide(Authentication, Object, Collection<ConfigAttribute>) - start" ); //$NON-NLS-1$
        }
        if ( configAttributes == null ) {
            if ( logger.isDebugEnabled() ) {
                logger.debug( "decide(Authentication, Object, Collection<ConfigAttribute>) - end" ); //$NON-NLS-1$
            }
            return;
        }
        if ( logger.isDebugEnabled() ) {
            logger.debug( "正在访问的url是：" + object.toString() );
        }
        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        while ( ite.hasNext() ) {
            ConfigAttribute ca = ite.next();
            logger.debug( "needRole is：" + ca.getAttribute() );
            String needRole = ( (SecurityConfig) ca ).getAttribute();
            for ( GrantedAuthority ga : authentication.getAuthorities() ) {
                logger.debug( "/t授权信息是：" + ga.getAuthority() );
                if ( ga.getAuthority().equals( "ROLE_ADMIN" ) || needRole.equals( ga.getAuthority() ) ) { // ga is user's role.
                    if ( logger.isDebugEnabled() ) {
                        logger.debug( "判断到，needRole 是" + needRole + ",用户的角色是:" + ga.getAuthority() + "，授权数据相匹配" );
                        logger.debug( "decide(Authentication, Object, Collection<ConfigAttribute>) - end" ); //$NON-NLS-1$
                    }
                    return;
                }
            }
        }
        throw new AccessDeniedException( "没有权限" );
    }

    public boolean supports( ConfigAttribute attribute ) {
        return true;
    }

    public boolean supports( Class<?> clazz ) {
        return true;
    }
}