package com.huge.dao.hibernate;


public class ActionEntityThreadLocalHolder {
    // public static final String ACTION_ENTITY_KEY = "action_entity";

    // public static Map<String, String> actionEntityMap = new HashMap<String, String>();

    private static ThreadLocal<String> actionEntityName = new ThreadLocal<String>() {
    };

    private static ThreadLocal<String> execActionName = new ThreadLocal<String>() {
    };

    public static String getActionEntityName() {
        return actionEntityName.get();
    }

    public static void setActionEntityName( String userdsn ) {
        actionEntityName.set( userdsn );
    }

    public static String getExecActionName() {
        return execActionName.get();
    }

    public static void setExecActionName( String an ) {
        execActionName.set( an );
    }

    /*
     * public static void addAEMap(String actionName,String entityName){ actionEntityMap.put( actionName, entityName );
     * } public static String getEntityNameByActionName(String an){ return actionEntityMap.get( an ); }
     */
}
