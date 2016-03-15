package com.huge.ihos.system.datacollection.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.huge.Constants;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskManager;
import com.huge.webapp.action.BaseAction;

public class DataCollectionTaskUpLoadAction
    extends BaseAction
{

    /**
     * 
     */
    private static final long serialVersionUID = -3841078497145445707L;

    private DataCollectionTaskManager dataCollectionTaskManager;

    private Long dataCollectionTaskId;

    private DataCollectionTask dataCollectionTask;

    private File file;

    private String fileContentType;

    private String fileFileName;

    private String name;

    @Override
    public void prepare() throws Exception {
        super.prepare();
        String id = this.getRequest().getParameter( "dataCollectionTaskId" );
        this.dataCollectionTask = this.dataCollectionTaskManager.get( new Long( id ) );
    }

    public String upload()
        throws Exception {
        try {

            String id = this.getRequest().getParameter( "dataCollectionTaskId" );
            this.dataCollectionTask = this.dataCollectionTaskManager.get( new Long( id ) );

            String uploadDir =
                Constants.USER_HOME + "dataCollectionUpLoad" + Constants.FILE_SEP + dataCollectionTask.getInterperiod() + Constants.FILE_SEP;
            File dirPath = new File( uploadDir );

            if ( !dirPath.exists() ) {
                dirPath.mkdirs();
            }

            InputStream stream = new FileInputStream( file );
            if(stream.available()>1024 * 1024 * 10){
            	 return ajaxForward(false, "上传文件最大为10M！" ,false );
            }
            String outFile = uploadDir + fileFileName;
            OutputStream bos = new FileOutputStream( outFile );
            int bytesRead;
            byte[] buffer = new byte[8192];

            while ( ( bytesRead = stream.read( buffer, 0, 8192 ) ) != -1 ) {
                bos.write( buffer, 0, bytesRead );
            }

            bos.close();
            stream.close();
            dataCollectionTask.setDataFile( outFile );
            this.dataCollectionTaskManager.save( dataCollectionTask );

            return ajaxForward( "上传成功！" );
        }
        catch ( Exception e ) {
            return ajaxForward( false,"上传失败！" );
        }
    }

    /**
     * Default method - returns "input"
     *
     * @return "input"
     */
    public String execute() {
        return INPUT;
    }

    public void validate() {
        String fileType = this.dataCollectionTask.getDataCollectionTaskDefine().getDataSourceDefine().getDataSourceType().getFileType();

        if ( getRequest().getMethod().equalsIgnoreCase( "post" ) ) {
            getFieldErrors().clear();
            if ( "".equals( fileFileName ) || file == null ) {
                super.addFieldError( "file", getText( "errors.requiredField", new String[] { getText( "uploadForm.file" ) } ) );
            }
            else if ( fileFileName.toLowerCase().indexOf( fileType.toLowerCase() ) == -1 ) {
                super.addFieldError( "file", getText( "errors.fileTypeRequired", new String[] { fileType } ) );
            }

            else if ( file.length() > 2097152 ) {
                addActionError( getText( "maxLengthExceeded" ) );
            }
        }
    }

    public String edit()
        throws Exception {
        //String id =this.getRequest().getParameter("dataCollectionTaskId");
        this.dataCollectionTask = this.dataCollectionTaskManager.get( dataCollectionTaskId );
        return SUCCESS;
    }

    public DataCollectionTask getDataCollectionTask() {
        return dataCollectionTask;
    }

    public void setDataCollectionTask( DataCollectionTask dataCollectionTask ) {
        this.dataCollectionTask = dataCollectionTask;
    }

    public DataCollectionTaskManager getDataCollectionTaskManager() {
        return dataCollectionTaskManager;
    }

    public void setDataCollectionTaskManager( DataCollectionTaskManager dataCollectionTaskManager ) {
        this.dataCollectionTaskManager = dataCollectionTaskManager;
    }

    public Long getDataCollectionTaskId() {
        return dataCollectionTaskId;
    }

    public void setDataCollectionTaskId( Long dataCollectionTaskId ) {
        this.dataCollectionTaskId = dataCollectionTaskId;
    }

    public File getFile() {
        return file;
    }

    public void setFile( File file ) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType( String fileContentType ) {
        this.fileContentType = fileContentType;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName( String fileFileName ) {
        this.fileFileName = fileFileName;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

}
