package com.huge.webapp.pagers;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class JsonTest {

    /* 
     * [1,2,3] 
     */
    public void testArr() {
        int ar[] = { 1, 2, 3 };
        JSONArray ja;
        try {
            ja = new JSONArray();
            ja.add( ar );
            System.out.println( ja.toString() );
        }
        catch ( JSONException e ) {
            e.printStackTrace();
        }

    }

    /**
     * @param args
     */
    public static void main( String[] args ) {

        JsonTest jt = new JsonTest();
        jt.testArr();
        //String jstr="{\"groupOp\":\"AND\",\"rules\":\"[{\"field\":\"name\", \"op\":\"eq\", \"data\":\"11\"}]\"}";
        String jstr = "[{\"field\": \"name\", \"op\": \"eq\", \"data\": \"11\"}]";
        String jstr1 = "{\"groupOp\":\"AND\",\"rules\":\"[{\\\"field\\\": \\\"name\\\", \\\"op\\\": \\\"eq\\\", \\\"data\\\": \\\"11\\\"}]\"}";
        JSONObject jsonFilter1 = (JSONObject) JSONSerializer.toJSON( jstr1 );
        JSONArray jsonFilter = (JSONArray) JSONSerializer.toJSON( jstr );
        //   String groupOp = jsonFilter.getString("groupOp");
        // String objf = jsonFilter.getString("field");

        int rulesCount = JSONArray.getDimensions( jsonFilter )[0];
        String[] fields = new String[rulesCount];
        String[] ops = new String[rulesCount];
        String[] datas = new String[rulesCount];
        for ( int i = 0; i < rulesCount; i++ ) {
            JSONObject rule = jsonFilter.getJSONObject( i );
            fields[i] = rule.getString( "field" );
            ops[i] = rule.getString( "op" );
            datas[i] = rule.getString( "data" );
        }

        Object obj = jsonFilter1.get( "rules" );
        System.out.println( obj );
        //         try {
        //        	 JSONObject jobj = jsonFilter.getJSONObject("rules");
        //        	 
        //		} catch (Exception e) {
        //			System.out.println(e.getStackTrace());
        //		}
        //	     try {
        //	    	 JSONArray rules1 = jsonFilter.optJSONArray("rules");
        //		} catch (Exception e) {
        //			System.out.println(e.getStackTrace());
        //		}
        //		  try {
        //			  JSONArray rules = jsonFilter.getJSONArray("rules");
        //			} catch (Exception e) {
        //				System.out.println(e.getMessage());
        //			}

        //	String jstr1="{\"rules\":\"[{\"field\": \"name\", \"op\": \"eq\", \"data\": \"11\"}]\"}";

        //{"groupOp":"AND","rules":"[{\"field\": \"name\", \"op\": \"eq\", \"data\": \"11\"}]"}
    }
}
