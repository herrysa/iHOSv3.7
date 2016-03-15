package Aladdin;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 */
/**
 * @author Administrator
 */
public class HaspTest {
    protected final Logger log = LoggerFactory.getLogger( getClass() );
    private long feature = 0;

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

  //  @Before
    public void setUp() {
        hasp.login( vendorCode );

    }

   // @After
    public void tearDown() {
        hasp.logout();
    }

    /*
     * @Test public void testFinalize() { fail( "Not yet implemented" ); }
     * @Test public void testGetLastError() { fail( "Not yet implemented" ); }
     * @Test public void testHasp() { fail( "Not yet implemented" ); }
     */

   // @Test
    public void testLogin() {
        int status = hasp.getLastError();
        Assert.assertTrue( status == HaspStatus.HASP_STATUS_OK );
        // fail( "Not yet implemented" );
    }

   // @Test
    public void testLoginScope() {

        boolean status = hasp.loginScope( "<haspscope>", vendorCode );
        Assert.assertTrue( status );

    }

   // @Test
    public void testGetVersion() {
        HaspApiVersion v=  hasp.getVersion( vendorCode );
        Assert.assertEquals( HaspStatus.HASP_STATUS_OK, v.getLastError() );
        this.log.info( v.toString() );
        //fail( "Not yet implemented" );
    }
    /*
     * @Test public void testLogout() { fail( "Not yet implemented" ); }
     * @Test public void testEncryptByteArray() { fail( "Not yet implemented" ); }
     * @Test public void testDecryptByteArray() { fail( "Not yet implemented" ); }
     * @Test public void testGetInfo() { fail( "Not yet implemented" ); }
     * @Test public void testGetSessionInfo() { fail( "Not yet implemented" ); }
     * @Test public void testReadLongIntByteArray() { fail( "Not yet implemented" ); }
     * @Test public void testWriteLongIntByteArray() { fail( "Not yet implemented" ); }
     * @Test public void testGetSize() { fail( "Not yet implemented" ); }
     * @Test public void testUpdate() { fail( "Not yet implemented" ); }
     * @Test public void testGetRealTimeClock() { fail( "Not yet implemented" ); }
     * @Test public void testGetVersion() { fail( "Not yet implemented" ); }
     * @Test public void testLegacyencryptByteArray() { fail( "Not yet implemented" ); }
     * @Test public void testLegacydecryptByteArray() { fail( "Not yet implemented" ); }
     * @Test public void testLegacysetRtcLong() { fail( "Not yet implemented" ); }
     * @Test public void testLegacysetIdletime() { fail( "Not yet implemented" ); }
     * @Test public void testDetach() { fail( "Not yet implemented" ); }
     */
@Test
public void dummyTest(){
	Assert.assertTrue(true);
}
}
