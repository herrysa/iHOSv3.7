package org.sbbs.app.license.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.sbbs.app.license.service.impl.ProductUtil;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class License implements Serializable {
   /**
     * 
     */
    private static final long serialVersionUID = -9124620161239196591L;

/* public String getCustomer() {
        return customer;
    }

    public void setCustomer( String customer ) {
        this.customer = customer;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber( String versionNumber ) {
        this.versionNumber = versionNumber;
    }

    public String getCopyrightInfo() {
        return copyrightInfo;
    }

    public void setCopyrightInfo( String copyrightInfo ) {
        this.copyrightInfo = copyrightInfo;
    }*/

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts( List<Product> products ) {
        this.products = products;
    }

 /*   public String getOemFlag() {
        return oemFlag;
    }

    public void setOemFlag( String oemFlag ) {
        this.oemFlag = oemFlag;
    }*/

   // private String customer;// 40

    //private String versionNumber;// 10

   // private String copyrightInfo;// 50
    
 //   private String oemFlag="0";//0:不是oem版本,1:是oem版本

    public VenderInfo getVender() {
        return vender;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate( String expirationDate ) {
        this.expirationDate = expirationDate;
    }

    public void setVender( VenderInfo vender ) {
        this.vender = vender;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public void setCustomer( CustomerInfo customer ) {
        this.customer = customer;
    }
    private VenderInfo vender=null;
  

    private CustomerInfo customer=null;
    
    private List<Product> products;

    
    private String expirationDate = "2099-12-31";
    
    private String haspType = "HL";
    public String getHaspType() {
		return haspType;
	}

	public void setHaspType(String haspType) {
		this.haspType = haspType;
	}

	public License(){
        
    }
    
    public License(byte[] rob,byte[] rwb) throws UnsupportedEncodingException{
       this.vender = new VenderInfo();
        this.customer = new CustomerInfo();

        String customerName = new String( Arrays.copyOfRange( rob, 0, 40 ), "GBK" );
        customerName = customerName.trim();
        customer.setCustomerName( customerName );
        
        String productVersion = new String( Arrays.copyOfRange( rob, 40, 50 ), "GBK" );
        productVersion = productVersion.trim();
        vender.setProductVersion( productVersion );
        
        String orgType = new String( Arrays.copyOfRange( rob, 50, 52 ), "GBK" );
        orgType = orgType.trim();
        customer.setOrgType( orgType );
        
        String orgNum = new String( Arrays.copyOfRange( rob, 52, 54 ), "GBK" );
        orgNum = orgNum.trim();
        try {
        	customer.setOrgNum( Integer.parseInt( orgNum ));
		} catch (Exception e) {
		}
        
        String copyRightInfo = new String( Arrays.copyOfRange( rob, 54, 100 ), "GBK" );
        copyRightInfo = copyRightInfo.trim();
        vender.setCopyRightInfo( copyRightInfo );
        
        this.expirationDate = new String( Arrays.copyOfRange( rob, 100, 112 ), "GBK" ).trim();
       // lic.setCustomer( customer );
      //  lic.setVender( vender );

        
        //TODO 处理需要
        
        List<Product> pl = new ArrayList<Product>();
        for ( int i = 0; i < ( rwb.length - ( rwb.length % 2 ) ); i = i + 2 ) {
            
            String pid = ProductUtil.getProductIdByIndex( rwb[i]);
            int au = (int)rwb[i+1];
            if(au!=0 && rwb[i]>=0){
                
                Product prod = new Product();
                prod.setProductId( ProductUtil.getProductIdByIndex( rwb[i]) );
                prod.setAllowedUsers( (int)rwb[i+1] +"" );
                
                
                
                pl.add( prod );
            }
        }
        this.products = pl;
    }
    
    
    
    /**
     * @return
     */
    public byte[] getRoMemoryInfo() {
        try {
            byte[] roMem = new byte[112];
            byte[] cusbyte = this.customer.getCustomerName().getBytes( "GBK" );
            byte[] verbyte = this.vender.getProductVersion().getBytes( "GBK" );
            byte[] copybyte = this.vender.getCopyRightInfo().getBytes( "GBK" );
            byte[] expirbyte = this.expirationDate.getBytes( "GBK" );
            
            byte[] orgTypebyte = this.customer.getOrgType().getBytes( "GBK" );
            byte[] orgNumbyte = null; 
            Integer orgNum = this.customer.getOrgNum();
            if(orgNum!=null){
            	orgNumbyte = orgNum.toString().getBytes( "GBK" );
            }
            
            roMem = this.mergeByteArray( cusbyte, roMem, 0, cusbyte.length );
            roMem = this.mergeByteArray( verbyte, roMem, 40, verbyte.length );
            roMem = this.mergeByteArray( orgTypebyte, roMem, 50, orgTypebyte.length );
            
            roMem = this.mergeByteArray( orgNumbyte, roMem, 52, orgNumbyte.length );
            roMem = this.mergeByteArray( copybyte, roMem, 54, copybyte.length );
            
            roMem = this.mergeByteArray( expirbyte, roMem, 100, expirbyte.length );
            
            
            return roMem;
        }
        catch ( UnsupportedEncodingException e ) {
            e.printStackTrace();
            return null;
        }

    }

    public byte[] getRwMemoryInfo() {
        byte[] rwMem = new byte[products.size() * 2];
        for ( int i = 0; i < products.size(); i++ ) {
            Product pd = (Product) products.get( i );
            
            
            
            byte pid = (byte)ProductUtil.getIndexByProductId( pd.getProductId());
            byte un = (byte)(Integer.parseInt( pd.getAllowedUsers()));
            
            byte[] prob = new byte[2];
            prob[0] = pid;
            prob[1] = un;
            
            this.mergeByteArray( prob, rwMem, 2*i, 2 );
            
            

        }
        return rwMem;

    }

    private byte[] mergeByteArray( byte[] src, byte[] tar, int offset, int length ) {

        System.arraycopy( src, 0, tar, offset, length );

        return tar;
    }
    public String[] getAllProductId() {
        String[] ids = new String[this.products.size()];
        List<String> l = new ArrayList<String>();
        for ( Iterator iterator = this.products.iterator(); iterator.hasNext(); ) {
            Product type = (Product) iterator.next();
            l.add( type.getProductId().trim() );
        }
        return l.toArray( ids );
    }

    public int getAllowedProductUser( String pid ) {
        Product find = null;
       // int u = 0;
        for ( Iterator iterator = this.products.iterator(); iterator.hasNext(); ) {
            Product type = (Product) iterator.next();
            if ( type.getProductId().trim().equalsIgnoreCase( pid ) ) {
                find = type;
                break;
            }

        }
        if ( find != null )
            return Integer.parseInt( find.getAllowedUsers() );
        else
            return 0;
    }
    

}
