package com.huge.ihos.hasp.webapp.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.sbbs.app.license.model.License;
import org.sbbs.app.license.model.Product;

import com.huge.foundation.common.AppException;
import com.huge.ihos.hasp.service.DogService;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.util.HaspDogHandler;
import com.huge.webapp.action.JqGridBaseAction;

public class HaspDogLicenseAction
    extends JqGridBaseAction {
    static DogService dogService = null;
    static {
        try {
            dogService = HaspDogHandler.getInstance().getDogService();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    MenuManager menuManager;

    private File v2cFile;

    public String updateLicenseV2C() {
        String line;
        StringBuilder sb = new StringBuilder();// "";
        BufferedReader reader;
        try {
            reader = new BufferedReader( new FileReader( this.getV2cFile() ) );
            while ( ( line = reader.readLine() ) != null ) {
                sb.append( line );
            }
            reader.close();
            String rn = this.dogService.updateDogLicenseFromV2C( sb.toString() );
            return ajaxForward( true, "License更新成功,请重新启动系统.", false );
        }
        catch ( Exception e ) {
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, e.getMessage(), false );
        }

    }

    public String exportC2V() {
        try {

            String c2vString = this.dogService.getDogLincenseInfo();
            // LicenseInfo li = this.LicenseInfoManager.find( licenseInfoId );
            SimpleDateFormat df = new SimpleDateFormat( "yyyy_MM_dd" );
            String returnValue = df.format( new Date() );

            String title = dogService.getHospitalName() + "_" + returnValue + ".c2v";
            HttpServletResponse resp = this.getResponse();
            resp.setContentType( "text/xml" );
            title = URLEncoder.encode(title, "UTF-8");   
            title = title.replaceAll("%00", "");
            title = new String( title.getBytes( "UTF-8" ));
            //title = new String( title.getBytes( "UTF-8" ), "ISO8859-1" );
            resp.setHeader( "Content-Disposition", "attachment;charset=UTF-8;filename=" + title );// "ISO8859-1"
            resp.setCharacterEncoding("UTF-8");

            OutputStream outs;
            outs = resp.getOutputStream();
            outs.write( c2vString.getBytes() );

            outs.flush();
            outs.close();
        }

        catch ( Exception e ) {
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, e.getMessage(), false );
        }
        return null;
    }

    License license = null;

    public String displayDogDetail() {

        try {
            license = this.dogService.getLicense();
            if(license!=null){
            	
            	license.setProducts( this.fillProductFromMenu( license.getProducts() ) );
            }

        }
        catch ( AppException e ) {
            e.printStackTrace();
        }

        return SUCCESS;
    }

    private List<Product> fillProductFromMenu( List<Product> pl ) {
        for ( Iterator iterator = pl.iterator(); iterator.hasNext(); ) {
            Product product = (Product) iterator.next();
            if(this.menuManager.exists(product.getProductId())){
            	Menu m = this.menuManager.get( product.getProductId() );
            	product.setProductName( m.getMenuName() );
            }

        }

        return pl;
    }

    public DogService getDogService() {
        return dogService;
    }

    public void setDogService( DogService dogService ) {
        this.dogService = dogService;
    }

    /*
     * public File getV2cFile() { return v2cFile; } public void setV2cFile( File v2cFile ) { v2cFile = v2cFile; }
     */

    public License getLicense() {
        return license;
    }

    public File getV2cFile() {
        return v2cFile;
    }

    public void setV2cFile( File v2cFile ) {
        this.v2cFile = v2cFile;
    }

    public void setLicense( License license ) {
        this.license = license;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void setMenuManager( MenuManager menuManager ) {
        this.menuManager = menuManager;
    }

}
