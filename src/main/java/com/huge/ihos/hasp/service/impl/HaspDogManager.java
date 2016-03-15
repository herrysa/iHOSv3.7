package com.huge.ihos.hasp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sbbs.app.license.model.License;
import org.sbbs.app.license.service.HaspException;
import org.sbbs.app.license.service.HaspService;
import org.sbbs.app.license.service.impl.HaspServiceImpl;

import Aladdin.Hasp;
import Aladdin.HaspStatus;

import com.huge.foundation.common.AppException;
import com.huge.foundation.common.GeneralAppException;
import com.huge.ihos.hasp.service.DogService;

public class HaspDogManager
    implements DogService {
    private static Log logger = LogFactory.getLog( HaspDogManager.class );

    private final String Encoding = "GBK";

    private long feature = 1;
    

    private final String vendorCode = "sfpJ7/tuhHMAz8DiFloZ7w48S+2avLdAqmz2oAjJZIPQUBIeuWOA4I20VEXptiNcXT1ijsyg/5tMOeQk"
        + "GLPyOUEsZoMZDuo2QVR87DeSmUioQ66rNfZO+pHHSPKHYXExFaBLH1xoRKAJLfciQWO4aFAKJIYO6i0B"
        + "mwhjl829TtieLaGBzRyq7QvLycFPItmkWPg9LO2iqU1Bxhz53Age7E1UqxHfgao9RQJU5GJm74MZRT4V"
        + "5b0Le1uNksXQXZbngvO+/L8tqxt1M+CSQRJWoKGgkiFOXL6Bwz7nLuuzmP9tCXjVz7iTJkhAv8N6Jzd4"
        + "ND4sv20bc3LENUFwEfsC5ElZr9ll5k6qdstda2Mnjn871umHOslE/eRvZKT1qAfG/NMus+Gs24OZwPRm"
        + "2pi2CSFtJ1xatXXjCdXH1yOBSYZ3T5xRnqvfw+GvNRCEueeb8DwBurT9CnR/FS6h8zXNFodMdiB425Ho"
        + "41F3Znqg/1b462K4C6DJA/9m1pdZpcV8bcsIM0A7pkhbWtPKvYAH/RYS1zb3VDhK1BaqtswlOKUnEotE"
        + "Fpx4x/7/CinU4dF2+G9i9Q5lTB223NKNpMrXHbM3lRnJHgVmfL/BMpbliqdvrEb65O37CFPS2oP3YYUp"
        + "gpoKTwzIm5zxtGutsdpOLM7h9BqMVjqotKjATuLfAfmlctuP9E7Mxj+0tMCKg/U2+ZhundmGILYNct9E"
        + "odFTaaZejJs5zovMj1xwZ3jDhjBeRvGD7s/qiI08JeBdNmboU1WrPbvzzetaV0R3eLHEQp+UoNd5M9Ck"
        + "OmeYj7aZDAZ4imNVchKylFyiOcDNjBvRFh9GGSAnrox8avBFVl+k3y8C94yDUiIvNpIMuFDaB3whU6vO"
        + "xTWHYNjh7ZPTt9bdQ+KZwY1zNRiIP4JxLIXsaoXAkIObSUi6uD4F76iRHOU=";

    private Hasp hasp = new Hasp( feature );

    private HaspService haspService = new HaspServiceImpl();

    public HaspDogManager()
        throws AppException {
        login();
        timer = new Timer();
        // timer.schedule(new DogTask(), 1000, 2000);
        timer.scheduleAtFixedRate( new DogTask(), 1000, 60000 );// 1分钟
    }

    public int login()
        throws AppException {
        hasp.login( vendorCode );

        int status = hasp.getLastError();

        if ( HaspStatus.HASP_STATUS_OK != status ) {
            /* 应该抛出异常，使应用系统程序终止 */
            throw new AppException( "软件锁登录失败,状态码为:"+status+" 请检查！" );
        }
        if(this.license==null){
            this.loadDogMsgs();
       
        
        }
        this.checkExpiration();
        return status;
    }

    public int logout()
        throws AppException {
        hasp.logout();

        int status = hasp.getLastError();

        if ( HaspStatus.HASP_STATUS_OK != status ) {
            /* 应该抛出异常，使应用系统程序终止 */
            throw new AppException( "软件锁LogOut失败,状态码为:"+status+" 请检查！" );
        }
      //  if(this.license!=null)
     //   this.checkExpiration();
        return status;
    }

    private byte[] readDogMemory( int offset, int length, boolean isRo )
        throws AppException {
        byte[] data = new byte[length];
        if ( isRo ) {
            hasp.read( Hasp.HASP_FILEID_RO, offset, data );
        }
        else {
            hasp.read( Hasp.HASP_FILEID_RW, offset, data );

        }

        int status = hasp.getLastError();
        if ( HaspStatus.HASP_STATUS_OK != status && HaspStatus.HASP_INV_HND != status && HaspStatus.HASP_BROKEN_SESSION != status ) {
            logger.warn( "加密锁返回状态为：" + status );
            throw new AppException( "加密锁内容读取失败,状态码为:"+status+" 请联系开发商。" );
        }
        return data;
    }

    /*
     * private String readHospitalName() throws AppException { this.login(); int offset = 0; int length = 40; byte[] hn
     * = readDogMemory( offset, length ); this.logout(); String bstr = null; try { bstr = new String( hn, Encoding ); }
     * catch ( UnsupportedEncodingException e ) { logger.warn( e.getStackTrace() ); throw new AppException(
     * "医院名称为不支持的编码格式，请联系开发商。" ); } String str = bstr.trim(); return str; }
     */

    /*
     * private String readSoftWareVersion() throws AppException { this.login(); int offset = 40; int length = 10; byte[]
     * hn = readDogMemory( offset, length ); this.logout(); String bstr = null; try { bstr = new String( hn, Encoding );
     * } catch ( UnsupportedEncodingException e ) { logger.warn( e.getStackTrace() ); throw new AppException(
     * "软件版本号为不支持的编码格式，请联系开发商。" ); } String str = bstr.trim(); return str; }
     */

    /*
     * private int readLicenseUserNumber() throws AppException { this.login(); int offset = 50; int length = 10; byte[]
     * hn = readDogMemory( offset, length ); this.logout(); String bstr = null; try { bstr = new String( hn, Encoding );
     * } catch ( UnsupportedEncodingException e ) { logger.warn( e.getStackTrace() ); throw new AppException(
     * "用户数为不支持的编码格式，请联系开发商。" ); } String str = bstr.trim(); return Integer.parseInt( str ); }
     */

    /*
     * private String readAllowedSubSystemCode() throws AppException { this.login(); int offset = 60; int length = 50;
     * byte[] hn = readDogMemory( offset, length ); this.logout(); String bstr = null; try { bstr = new String( hn,
     * Encoding ); } catch ( UnsupportedEncodingException e ) { logger.warn( e.getStackTrace() ); throw new
     * AppException( "授权模块为不支持的编码格式，请联系开发商。" ); } String str = bstr.trim(); return str; }
     */

    Timer timer = null;

    /*
     * private String hospitalName = null; private String softVersion = null; private Integer licenseUserNumber = null;
     * private String allowedSubSystemCode = null;
     */

    private License license = null;

    /**
     * @throws AppException
     */
    private synchronized void loadDogMsgs()
        throws AppException {
        try {
            // String dogInfo = this.haspService.getHaspInfo();
            // String dogDecodeInfo = this.haspService.decodeHaspInfo( dogInfo );

            /*license = new License();// = this.haspService.parseFromDogInfo( dogDecodeInfo );
            license.setCustomer( new String( this.readDogMemory( 0, 40, true ), this.Encoding ) );
            license.setVersionNumber( new String( this.readDogMemory( 40, 10, true ), this.Encoding ) );
            license.setCopyrightInfo(  new String( this.readDogMemory( 50, 50, true ), this.Encoding ) );
            
            
            List<Product> pl = new ArrayList<Product>();
            int count =0;
            while(true){
                
                byte[] pb = this.readDogMemory( count*4, 4, false );
               String pid= new String( Arrays.copyOfRange( pb, 0, 2 ), this.Encoding  ); 
                String puser = new String( Arrays.copyOfRange( pb,  2, 4), this.Encoding  );
                
                if(pid.trim().length()==0 && puser.trim().length()==0){
                    break;
                }else{
                
                Product prod = new Product();
                prod.setProductId( pid );
                prod.setAllowedUsers( puser );
                //prod.setProductId( new String( Arrays.copyOfRange( rwb, i, i + 7 ), "GBK" ) );
                //prod.setAllowedUsers( new String( Arrays.copyOfRange( rwb, i + 8, i + 11 ), "GBK" ) );

                pl.add( prod );
                count++;
                }
                
                
            }
            license.setProducts( pl );
            */
            
            //this.license = this.haspService.parseFromDogInfo(  );
            byte[] ro = this.readDogMemory( 0, 112, true );
            byte[] rw = this.readDogMemory( 0, 112, false );

            this.license = new License(ro,rw);
            //TODO 需要更新代码，然后提取定义的过期时间
           if(this.license!=null)
         this.checkExpiration();
        }
       
        catch ( Exception e ) {
           // e.printStackTrace();

            throw new AppException( e.getMessage() );

        }

        /*
         * this.hospitalName = this.readHospitalName(); this.softVersion = this.readSoftWareVersion();
         * this.licenseUserNumber = this.readLicenseUserNumber(); this.allowedSubSystemCode =
         * this.readAllowedSubSystemCode();
         */
    }

    private void checkExpiration() throws AppException{
        String expDS =license.getExpirationDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expDate;
        try {
            expDate = sdf.parse(expDS.trim());
            Date now = new Date();
            String nowS =  sdf.format( now );
            now = sdf.parse(nowS.trim());
            if(now.compareTo( expDate )>0)
                throw new AppException( "授权过期，当前系统时间为："+ nowS + " 有效期为：" + expDS );
        }
        catch ( Exception e ) {
           // e.printStackTrace();
           // throw new AppException( e.getMessage() );
        }
    }
    
    
    private synchronized void cleanDogMsgs() {
        /*
         * this.hospitalName = null; this.softVersion = null; this.licenseUserNumber = null; this.allowedSubSystemCode =
         * null; allowedSubSystem = null;
         */
        this.license = null;
    }

    class DogTask
        extends java.util.TimerTask {

        @Override
        public void run() {
            try {
                loadDogMsgs();
                
                
            }
            catch ( AppException e ) {
                e.printStackTrace();

                 throw new GeneralAppException(e.getMessage());
                //throw new AppException( e.getMessage() );
            }finally{
            	cleanDogMsgs();
            	hasp.logout();
            }

        }

    }

    /*
     * public String getHospitalName() throws AppException { // checkTimer(); if ( this.hospitalName == null )
     * this.loadDogMsgs(); return hospitalName; } public String getSoftWareVersion() throws AppException { //
     * checkTimer(); if ( this.softVersion == null ) this.loadDogMsgs(); return softVersion; }
     */

    /*
     * public int getLicenseUserNumber() throws AppException { // checkTimer(); if ( this.licenseUserNumber == null )
     * this.loadDogMsgs(); return licenseUserNumber.intValue(); }
     */

    // String[] allowedSubSystem = null;

    public String[] getAllowedSubSystem()
        throws AppException {
        /*
         * if ( allowedSubSystem == null ) { if ( this.allowedSubSystemCode == null ) this.loadDogMsgs();
         * allowedSubSystem = new String[this.allowedSubSystemCode.length() / 2]; List list = new ArrayList(); for ( int
         * i = 0; i < allowedSubSystemCode.length(); i = i + 2 ) { list.add( allowedSubSystemCode.substring( i, i + 2 )
         * ); } list.toArray( allowedSubSystem ); return allowedSubSystem; } else { return allowedSubSystem; }
         */
        this.logger.error( "real dog service: getAllowedSubSystem" );
        if ( this.license == null )
            this.loadDogMsgs();
        return license.getAllProductId();
    }

    @Override
    public String getSoftWareCopyRight()
        throws AppException {
        if ( this.license == null )
            this.loadDogMsgs();
        return license.getVender().getCopyRightInfo();
    }

    @Override
    public int getSubSystemAllowUser( String subSysMenuId )
        throws AppException {
        if ( this.license == null )
            this.loadDogMsgs();
        return license.getAllowedProductUser( subSysMenuId );
    }

    @Override
    public String updateDogLicenseFromV2C( String v2cString )
        throws AppException {
        try {
            return this.haspService.updateLicenseToDog( v2cString );
        }
        catch ( HaspException e ) {
            e.printStackTrace();
            throw new AppException( e.getMessage() );
        }
    }

    @Override
    public String getDogLincenseInfo()
        throws AppException {
        try {
            return this.haspService.getHaspInfo();
        }
        catch ( HaspException e ) {
            e.printStackTrace();
            throw new AppException( e.getMessage() );
        }
    }

    @Override
    public String getHospitalName()
        throws AppException {
        if ( this.license == null )
            this.loadDogMsgs();
        return license.getCustomer().getCustomerName();
    }

    @Override
    public String getSoftWareVersion()
        throws AppException {
        if ( this.license == null )
            this.loadDogMsgs();
        return license.getVender().getProductVersion();
    }

    @Override
    public License getLicense()
        throws AppException {
        if ( this.license == null )
            this.loadDogMsgs();
        return license;
    }

	@Override
	public String getOrgType() throws AppException {
		if ( this.license == null )
            this.loadDogMsgs();
		return license.getCustomer().getOrgType();
	}

	@Override
	public Integer getOrgNum() throws AppException {
		if ( this.license == null )
            this.loadDogMsgs();
		return license.getCustomer().getOrgNum();
	}

}
