package com.huge.service;

import org.junit.Test;

import com.huge.foundation.common.AppException;
import com.huge.ihos.hasp.service.impl.HaspDogManager;

public class HaspDogTest {
    HaspDogManager dogManager = null;

    @Test
    public void testHasp() {
        try {
            dogManager = new HaspDogManager();
            String[] allows = dogManager.getAllowedSubSystem();

            for ( int i = 0; i < allows.length; i++ ) {
                System.out.println( allows[i] );
            }

        }
        catch ( AppException e ) {
            e.printStackTrace();
        }
        //fail("Not yet implemented");
    }

}
