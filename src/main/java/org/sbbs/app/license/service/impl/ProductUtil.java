package org.sbbs.app.license.service.impl;

public class ProductUtil {
    private final static String[] productIdArray = { "00", "01", "0201", "0202", "03", "04", "05", "0601", "0602", "0603", "0701", "0702", "0703",
        "0704", "0705", "0706", "0707", "0708", "0709", "0801", "0802", "0803", "0901", "0902", "0903", "0904", "0905", "0906", "0907", "0908",
        "0909", "10", "11", "12", "20", "30", "40", "50", "60","02","06","07","08","09","0920" };

    public static String getProductIdByIndex( int index ) {
        if ( index >= 0 && index < productIdArray.length )
            return productIdArray[index];
        else
            return null;
    }

    public static int getIndexByProductId( String pid ) {
        for ( int i = 0; i < productIdArray.length; i++ ) {
            if ( productIdArray[i].equalsIgnoreCase( pid ) )
                return i;
        }
        return -1;
    }
}
