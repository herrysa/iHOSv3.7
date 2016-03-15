/*
 *  $Id: HaspApiVersion.java,v 1.6 2010-06-07 04:26:15 pwang Exp $
 *
 *  Copyright (C) 2010, SafeNet, Inc. All rights reserved.
 *  Use is subject to license terms.
 */

package Aladdin;

import Aladdin.HaspStatus;

public class HaspApiVersion {
    private int major_version[] = { 0 };

    private int minor_version[] = { 0 };

    private int build_server[] = { 0 };

    private int build_number[] = { 0 };

    private int status;

    /**
     * private native functions
     *
     */

    private static native int GetVersion( int major_version[], int minor_version[], int build_server[], int build_number[], byte vendor_code[] );

    /**
     * IA 64 not considered yet
     */
    static {
        HaspStatus.Init();
    }

    /**
     * HaspApiVersion constructor
     *
     * @param      vendor_code   The Vendor Code.
     *
     */
    public HaspApiVersion( String vendor_code ) {
        status = GetVersion( major_version, minor_version, build_server, build_number, vendor_code.getBytes() );
    }

    /**
     *  Returns the error that occurred in the last function call.
     */
    public int getLastError() {
        return status;
    }

    /**
     *  Returns the HASP API major version. 
     */
    public int majorVersion() {
        return major_version[0];
    }

    /**
     *  Returns the HASP API minor version.
     *
     */
    public int minorVersion() {
        return minor_version[0];
    }

    /**
     *  Returns the HASP API build number.
     *
     */
    public int buildNumber() {
        return build_number[0];
    }
}
