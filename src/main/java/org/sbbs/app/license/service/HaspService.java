package org.sbbs.app.license.service;

import java.io.UnsupportedEncodingException;

import org.sbbs.app.license.model.License;
import org.sbbs.app.license.model.LicenseInfo;

/**
 * @author Administrator
 *
 */
public interface HaspService {
    public int haspLogin()  throws HaspException;
    public String getHaspInfo () throws HaspException;
    public String decodeHaspInfo(String haspInfo) throws HaspException;
    public String genLicenseDefine(License license) throws HaspException;
    public String genNewLicense(String licenseDefine,String srcC2V) throws HaspException;
    public String updateLicenseToDog(String v2cString) throws HaspException;
    public License parseFromLicenseInfo(LicenseInfo lInfo) throws HaspException;
    public License parseFromDogInfo() throws HaspException, UnsupportedEncodingException;
    
    
}
