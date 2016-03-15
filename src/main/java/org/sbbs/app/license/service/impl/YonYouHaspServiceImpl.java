package org.sbbs.app.license.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;

import org.sbbs.app.license.model.License;
import org.sbbs.app.license.model.LicenseInfo;
import org.sbbs.app.license.service.HaspException;
import org.sbbs.app.license.service.HaspService;

public class YonYouHaspServiceImpl
    implements HaspService {

    @Override
    // used
    public int haspLogin()
        throws HaspException {
        return 0;
    }

    @Override
    public String getHaspInfo()
        throws HaspException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String decodeHaspInfo( String haspInfo )
        throws HaspException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String genLicenseDefine( License license )
        throws HaspException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream( baos ).writeObject( license );
        }
        catch ( IOException e ) {
            e.printStackTrace();
            throw new HaspException( e.getMessage() );
        }

        String ls = ( new sun.misc.BASE64Encoder() ).encode( baos.toByteArray() );
        return ls;
    }

    @Override
    public String genNewLicense( String licenseDefine, String srcC2V )
        throws HaspException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String updateLicenseToDog( String v2cString )
        throws HaspException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public License parseFromLicenseInfo( LicenseInfo lInfo )
        throws HaspException {

        return this.parseFromStringInfo( lInfo.getLicenseData() );
    }

    @Override
    public License parseFromDogInfo()
        throws HaspException {
        FileInputStream stream;
        License ol = null;
        try {
            stream = new FileInputStream( "c:/windows/license.v2c" );

            Reader reader = new BufferedReader( new InputStreamReader( stream, "UTF-8" ) );
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ( ( read = reader.read( buffer, 0, buffer.length ) ) > 0 ) {
                builder.append( buffer, 0, read );
            }
            String ls = builder.toString();
            stream.close();
            ol = this.parseFromStringInfo( ls );
        }

        catch ( Exception e ) {
            e.printStackTrace();
            throw new HaspException( e.getMessage() );
        }

        return ol;

    }

    private License parseFromStringInfo( String dinfo )
        throws HaspException {
        byte[] lbd = null;
        License ol = null;
        try {
            lbd = ( new sun.misc.BASE64Decoder() ).decodeBuffer( dinfo );

            ByteArrayInputStream bais = new ByteArrayInputStream( lbd );
            ObjectInputStream ois = null;

            ois = new ObjectInputStream( bais );

            ol = (License) ois.readObject();
        }

        catch ( Exception e ) {
            e.printStackTrace();
            throw new HaspException( e.getMessage() );
        }
        return ol;
    }
}
