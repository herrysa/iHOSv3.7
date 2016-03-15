package com.huge.ihos.system.systemManager.security;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtil {

    static private List<String> logoutedUser = new ArrayList<String>();

    public static void putLogoutedUser( String userName ) {
        logoutedUser.add( userName );
    }

    public static boolean getLogoutedUser( String userName ) {
        boolean flag = false;
        for ( String un : logoutedUser ) {
            if ( un.equals( userName ) ) {
                flag = true;
                logoutedUser.remove( un );
                break;
            }
        }
        return flag;
    }
}
