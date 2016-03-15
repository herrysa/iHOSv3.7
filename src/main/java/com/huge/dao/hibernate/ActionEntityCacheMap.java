package com.huge.dao.hibernate;

import java.util.HashMap;
import java.util.Map;

public class ActionEntityCacheMap {

    public static Map<String, String> actionEntityMap = new HashMap<String, String>();
 
    public static void addAEMap(String actionName,String entityName){
        actionEntityMap.put( actionName, entityName );
    }
    
    public static String getEntityNameByActionName(String an){
        return actionEntityMap.get( an );
    }
}
