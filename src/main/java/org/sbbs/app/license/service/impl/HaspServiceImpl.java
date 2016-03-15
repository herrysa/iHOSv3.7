package org.sbbs.app.license.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.sbbs.app.license.model.License;
import org.sbbs.app.license.model.LicenseInfo;
import org.sbbs.app.license.service.HaspException;
import org.sbbs.app.license.service.HaspService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Aladdin.Hasp;
import Aladdin.HaspStatus;

import com.sentinel.ldk.licgen.CharStringBuffer;
import com.sentinel.ldk.licgen.LicGenAPIHelper;

/*@Service( "haspService" )*/
/**
 * @author Administrator
 */
public class HaspServiceImpl
    implements HaspService {
    private Logger logger = LoggerFactory.getLogger( this.getClass() );

    private final String Encoding = "GBK";

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

    private LicGenAPIHelper licHelper = new LicGenAPIHelper();

    public String getHaspInfo()
        throws HaspException {
        this.haspLogin();
        String scope = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>        <haspscope>            <hasp type=\"HASP-HL\" />        </haspscope>";
        String format = "<haspformat format=\"updateinfo\"/>";
        String dogInfo = hasp.getInfo( scope, format, vendorCode );
        int status = hasp.getLastError();

        if ( HaspStatus.HASP_STATUS_OK != status ) {
            throw new HaspException( "软件锁信息读取失败！" );
        }
        this.hasp.logout();
        return dogInfo;
    }

    public int haspLogin()
        throws HaspException {
        hasp.login( vendorCode );

        int status = hasp.getLastError();

        if ( HaspStatus.HASP_STATUS_OK != status ) {
            /* 应该抛出异常，使应用系统程序终止 */
            throw new HaspException( "软件锁登录失败，请检查！" );
        }
        return status;
    }

    public String decodeHaspInfo( String haspInfo )
        throws HaspException {
        String xmlInfo = null;
        CharStringBuffer readable_state = new CharStringBuffer();

        CharStringBuffer error_msg = new CharStringBuffer();
        int status = licHelper.sntl_lg_initialize( xmlInfo );
        try {
            licHelper.sntl_lg_get_info( LicGenAPIHelper.SNTL_LG_INFO_LAST_ERROR_MESSAGE, error_msg );
            this.logger.info( error_msg.get() );
            if ( LicGenAPIHelper.SNTL_LG_STATUS_OK != status ) {
                throw new HaspException( "LicHelper初始化失败，请检查！" );
            }
            status = licHelper.sntl_lg_decode_current_state( vendorCode, haspInfo, readable_state );
            licHelper.sntl_lg_get_info( LicGenAPIHelper.SNTL_LG_INFO_LAST_ERROR_MESSAGE, error_msg );
            this.logger.info( error_msg.get() );
            if ( LicGenAPIHelper.SNTL_LG_STATUS_OK != status ) {
                throw new HaspException( "LicHelper对加密狗信息解密失败，请检查！" );
            }
            licHelper.sntl_lg_cleanup();
            return readable_state.get();

        }
        catch ( UnsupportedEncodingException e ) {
            throw new HaspException( e.getMessage() );
        }

    }

    /**
 * 
 */

  /*  public String genLicenseDefine( License license )
        throws HaspException {
        String licenseDefTemplate =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + " <sentinel_ldk:license schema_version=\"1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:sentinel_ldk=\"http://www.safenet-inc.com/sentinelldk\">"
                + "   <acknowledgement_request>Yes</acknowledgement_request>"

                + "   <enforcement_type>HL</enforcement_type>"

                + "   <product>" + "      <id>0</id>" + "      <name>iHOS</name>" + "      <feature>" + "         <id>1</id>"
                + "         <name>iHOS</name>" + "         <license_properties>" + "            <perpetual/>"
                + "            <remote_desktop_access>No</remote_desktop_access>" + "         </license_properties>" + "      </feature>"
                + "   </product>" + "   <memory>" + "      <ro_memory_segment>" + "         <offset>0</offset>"
                + "         <content>roMemory</content>" + "      </ro_memory_segment>" + "      <rw_memory_segment>" + "         <offset>0</offset>"
                + "         <content>rwMemory</content>" + "      </rw_memory_segment>" + "   </memory>" + " </sentinel_ldk:license>";
        byte[] rom = license.getRoMemoryInfo();
        byte[] rem = license.getRwMemoryInfo();
        String romString = base64_encode( rom );
        String rwmString = base64_encode( rem );
        licenseDefTemplate = licenseDefTemplate.replace( "roMemory", romString );
        licenseDefTemplate = licenseDefTemplate.replace( "rwMemory", rwmString );
        return licenseDefTemplate;
    }*/
    public String genLicenseDefine( License license )
                    throws HaspException {
                    String licenseDefTemplate =
                        "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                            + " <sentinel_ldk:license schema_version=\"1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:sentinel_ldk=\"http://www.safenet-inc.com/sentinelldk\">"
                            + "   <acknowledgement_request>Yes</acknowledgement_request>" + "   <enforcement_type>HL</enforcement_type>" + "   <product>"
                            + "      <id>0</id>" + "      <name>iHOS</name>" + "      <feature>" + "         <id>1</id>" + "         <name>iHOS</name>"
                            + "         <license_properties>" + "            <perpetual/>" + "            <remote_desktop_access>No</remote_desktop_access>"
                            + "         </license_properties>" + "      </feature>" + "   </product>" + "   <memory>" + "      <ro_memory_segment>"
                            + "         <offset>0</offset>" + "         <content>roMemory</content>" + "      </ro_memory_segment>" + "      <rw_memory_segment>"
                            + "         <offset>0</offset>" + "         <content>rwMemory</content>" + "      </rw_memory_segment>" + "   </memory>"
                            + " </sentinel_ldk:license>";
                    byte[] rom = license.getRoMemoryInfo();
                    byte[] rem = license.getRwMemoryInfo();
                    String romString = Sample.base64_encode( rom );
                    String rwmString = Sample.base64_encode( rem );
                    licenseDefTemplate = licenseDefTemplate.replace( "roMemory", romString );
                    licenseDefTemplate = licenseDefTemplate.replace( "rwMemory", rwmString );
                    return licenseDefTemplate;
                }
    public String genNewLicense( String licenseDefine, String srcC2V )
        throws HaspException {
        int status = 0;
        String szInitParamXML = null;
        String szStartParamXML = null;
        String szLicenseDefinitionXML = null;
        String szGenerationParamXML = null;

        CharStringBuffer license = new CharStringBuffer();
        CharStringBuffer resultant_state = new CharStringBuffer();

        // CharStringBuffer error_msg = new CharStringBuffer();
        LicGenAPIHelper oneinstance = new LicGenAPIHelper();
        // String szC2V = this.getHaspInfo();
        status = oneinstance.sntl_lg_initialize( szInitParamXML );
        if ( LicGenAPIHelper.SNTL_LG_STATUS_OK != status ) {
            throw new HaspException( "初始化失败，请检查！" );
        }
        status =
            oneinstance.sntl_lg_start( szStartParamXML, vendorCode, LicGenAPIHelper.SNTL_LG_LICENSE_TYPE_CLEAR_AND_UPDATE, licenseDefine, srcC2V );
        if ( LicGenAPIHelper.SNTL_LG_STATUS_OK != status ) {
            throw new HaspException( "License加密为V2C失败，请检查！" );
        }
        try {
            status = oneinstance.sntl_lg_generate_license( szGenerationParamXML, license, resultant_state );
        }
        catch ( UnsupportedEncodingException e ) {// oneinstanceoneinstanceoneinstance
            e.printStackTrace();
            throw new HaspException( "License加密为V2C失败，请检查！" + e.getMessage() );
        }
        if ( LicGenAPIHelper.SNTL_LG_STATUS_OK != status ) {
            throw new HaspException( "License加密为V2C失败，请检查！" );
        }

        status = oneinstance.sntl_lg_cleanup();

        return license.get();
    }

    private License parseFromXmlString( String xmlString, boolean fromDog )
        throws HaspException {
        String ro = null, rw = null;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( new ByteArrayInputStream( xmlString.getBytes() ) );
            doc.getDocumentElement().normalize();
            System.out.println( "Root element :" + doc.getDocumentElement().getNodeName() );
            NodeList nList = doc.getElementsByTagName( fromDog ? "ro_memory" : "ro_memory_segment" );
            for ( int temp = 0; temp < nList.getLength(); temp++ ) {
                Node nNode = nList.item( temp );
                if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eElement = (Element) nNode;
                    if ( fromDog )

                        ro = eElement.getTextContent();
                    else
                        ro = eElement.getElementsByTagName( "content" ).item( 0 ).getTextContent();
                }

            }
            nList = doc.getElementsByTagName( fromDog ? "rw_memory" : "rw_memory_segment" );
            for ( int temp = 0; temp < nList.getLength(); temp++ ) {
                Node nNode = nList.item( temp );
                if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eElement = (Element) nNode;
                    if ( fromDog )

                        rw = eElement.getTextContent();
                    else
                        rw = eElement.getElementsByTagName( "content" ).item( 0 ).getTextContent();
                }

            }

            byte[] rob = base64_decode( ro );
            byte[] rwb = base64_decode( rw );
            String roString = new String( rob, "GBK" );
            String rwString = new String( rwb, "GBK" );

            License lic = new License(rob,rwb);

         /*   VenderInfo vender = new VenderInfo();
            CustomerInfo customer = new CustomerInfo();

            customer.setCustomerName( new String( Arrays.copyOfRange( rob, 0, 39 ), "GBK" ) );
            vender.setProductVersion( new String( Arrays.copyOfRange( rob, 40, 49 ), "GBK" ) );
            vender.setCopyRightInfo( new String( Arrays.copyOfRange( rob, 50, 99 ), "GBK" ) );

            lic.setCustomer( customer );
            lic.setVender( vender );

            
            //TODO 处理需要
            
            List<Product> pl = new ArrayList<Product>();
            for ( int i = 0; i < ( rwb.length - ( rwb.length % 4 ) ); i = i + 4 ) {
                Product prod = new Product();
                prod.setProductId( new String( Arrays.copyOfRange( rwb, i, i + 2 ), "GBK" ) );
                prod.setAllowedUsers( new String( Arrays.copyOfRange( rwb, i + 2, i + 4 ), "GBK" ) );

                pl.add( prod );
            }
            lic.setProducts( pl );*/
            return lic;

        }
        catch ( SAXException e ) {
            e.printStackTrace();
            throw new HaspException( e.getMessage() );
        }
        catch ( IOException e ) {
            e.printStackTrace();
            throw new HaspException( e.getMessage() );
        }
        catch ( ParserConfigurationException e ) {
            e.printStackTrace();
            throw new HaspException( e.getMessage() );
        }

    }

    @Override
    public License parseFromLicenseInfo( LicenseInfo lInfo )
        throws HaspException {
        String ldef = lInfo.getLicenseDefineData();

        return this.parseFromXmlString( ldef, false );

        // return null;
    }

    @Override
    public License parseFromDogInfo( )
        throws HaspException {
        
         String info = this.getHaspInfo();
        String decodeInfo = this.decodeHaspInfo( info );
        return this.parseFromXmlString( decodeInfo, true );
    }

    @Override
    public String updateLicenseToDog( String v2cString )
        throws HaspException {
        String status;
        try {
            status = hasp.update( v2cString );
            // ack_data = hasp.update(updatedata);
            int is = hasp.getLastError();

            switch ( is ) {
                case HaspStatus.HASP_STATUS_OK:
                    System.out.println( "Key successfully updated" );
                    if ( status != null ) {
                        /* save acknowledge data in file */
                        // f1 = new RandomAccessFile("hasp_ack.c2v", "rw");
                        // f1.write(ack_data.getBytes());
                        System.out.println( "acknowledge data written to file hasp_ack.c2v\n" );
                    }
                    break;
                case HaspStatus.HASP_INV_HND:
                    System.out.println( "handle not active\n" );
                    throw new HaspException( "handle not active\n" );
                    // break;
                case HaspStatus.HASP_INV_FORMAT:
                    System.out.println( "unrecognized format\n" );
                    throw new HaspException( "unrecognized format\n" );
                    // break;
                case HaspStatus.HASP_INV_UPDATE_DATA:
                    System.out.println( "invalid update data\n" );
                    throw new HaspException( "invalid update data\n" );
                    // break;
                case HaspStatus.HASP_INV_UPDATE_NOTSUPP:
                    System.out.println( "key does not support updates\n" );
                    throw new HaspException( "key does not support updates\n" );
                    // break;
                case HaspStatus.HASP_INV_UPDATE_CNTR:
                    System.out.println( "invalid update counter\n" );
                    throw new HaspException( "invalid update counter\n" );
                    // break;
                default:
                    System.out.println( "hasp_update failed with status " + is + " " + status );
                    throw new HaspException( "hasp_update failed with status " + is + " " + status );
            }

            // if(is!=HaspStatus.HASP_STATUS_OK)

        }
        catch ( Exception e ) {
            e.printStackTrace();
            throw new HaspException( e.getMessage() );
        }
        return status;
    }

    private static final String base64code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static String base64_encode( byte[] input ) {
        int length = 0;
        int pad_count = 0;

        byte[] input_string;

        StringBuilder output;

        length = input.length;
        if ( length == 0 ) {
            return null;
        }

        pad_count = ( 3 - ( length % 3 ) ) % 3;

        output = new StringBuilder( ( length + pad_count ) / 3 * 4 );

        input_string = new byte[length + pad_count];
        System.arraycopy( input, 0, input_string, 0, length );

        int i = 0;
        for ( i = 0; i < length + pad_count; i += 3 ) {
            int idx = 0;

            idx = ( input_string[i] >> 2 ) & 0x3F;
            output.append( base64code.charAt( idx ) );

            idx = ( ( input_string[i] & 0x3 ) << 4 ) + ( ( input_string[i + 1] >> 4 ) & 0x0F );
            output.append( base64code.charAt( idx ) );

            idx = ( ( input_string[i + 1] & 0x0F ) << 2 ) + ( ( input_string[i + 2] >> 6 ) & 0x03 );
            output.append( base64code.charAt( idx ) );

            idx = ( input_string[i + 2] & 0x3F );
            output.append( base64code.charAt( idx ) );
        }

        if ( pad_count > 0 ) {
            output.setCharAt( output.length() - 1, '=' );
            pad_count--;

            if ( pad_count > 0 ) {
                output.setCharAt( output.length() - 2, '=' );
            }
        }

        return output.toString();
    }

    /**
     * use BASE64 map to map a char to byte
     * 
     * @param input char that is ready for convert
     * @return corresponding byte value of the input char
     */
    public static byte base64_decode_char( char input ) {
        if ( ( input >= 'A' ) && ( input <= 'Z' ) ) {
            return (byte) ( input - 'A' + 0 ); // 0 range starts at 'A'
        }

        if ( ( input >= 'a' ) && ( input <= 'z' ) ) {
            return (byte) ( input - 'a' + 26 ); // 26 range starts at 'a'
        }

        if ( ( input >= '0' ) && ( input <= '9' ) ) {
            return (byte) ( input - '0' + 52 ); // 52 range starts at '0'
        }

        if ( input == '+' ) {
            return 62;
        }

        if ( input == '/' ) {
            return 63;
        }

        if ( input == '=' ) {
            return 64;
        }

        return 65;
    }

    /**
     * use BASE64 to decode the input string
     * 
     * @param input string that is ready for decode
     * @return decoded string or null when error
     */
    public static byte[] base64_decode( String input ) {
        int length = 0;
        int pad_count = 0;
        int temp = 0;

        byte[] output;
        byte[] output_array;

        length = input.length();

        // verify if input is a legal BASE64 string
        if ( ( length == 0 ) || ( length % 4 != 0 ) ) {
            return null;
        }

        for ( int i = 0; i < length; i++ ) {
            temp = base64_decode_char( input.charAt( i ) );
            if ( temp == 64 ) {
                if ( ( i != ( length - 1 ) ) && ( i != ( length - 2 ) ) ) {
                    return null;
                }

                if ( i == ( length - 2 ) ) {
                    if ( base64_decode_char( input.charAt( i + 1 ) ) != 64 ) {
                        return null;
                    }
                    pad_count++;
                }
                else {
                    pad_count++;
                }
            }

            if ( temp == 65 ) {
                return null;
            }
        }

        output_array = new byte[length / 4 * 3];

        for ( int i = 0, j = 0; i < length; i += 4, j += 3 ) {
            int val = 0;
            for ( int k = 0; k < 4; k++ ) {
                byte index = base64_decode_char( input.charAt( i + k ) );
                val |= index & 0x3f;
                val <<= 6;
            }
            val <<= 2;

            for ( int l = 0; l < 3; l++ ) {
                output_array[j + l] = (byte) ( val >> 24 );
                val <<= 8;
            }
        }

        output = new byte[length / 4 * 3 - pad_count];
        System.arraycopy( output_array, 0, output, 0, length / 4 * 3 - pad_count );

        return output;
    }

}
