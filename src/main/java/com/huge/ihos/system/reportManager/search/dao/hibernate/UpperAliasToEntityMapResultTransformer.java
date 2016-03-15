package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.transform.BasicTransformerAdapter;

public class UpperAliasToEntityMapResultTransformer
    extends BasicTransformerAdapter
    implements Serializable {
    public static final UpperAliasToEntityMapResultTransformer INSTANCE = new UpperAliasToEntityMapResultTransformer();

    /**
     * Disallow instantiation of AliasToEntityMapResultTransformer.
     */
    private UpperAliasToEntityMapResultTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public Object transformTuple( Object[] tuple, String[] aliases ) {
        Map result = new HashMap( tuple.length );
        for ( int i = 0; i < tuple.length; i++ ) {
            String alias = aliases[i];
            if ( alias != null ) {
                result.put( alias.toUpperCase(), tuple[i] );
            }
        }
        return result;
    }

    /**
     * Serialization hook for ensuring singleton uniqueing.
     *
     * @return The singleton instance : {@link #INSTANCE}
     */
    private Object readResolve() {
        return INSTANCE;
    }
}
