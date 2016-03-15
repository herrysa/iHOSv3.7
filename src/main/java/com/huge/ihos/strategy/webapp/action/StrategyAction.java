package com.huge.ihos.strategy.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.huge.ihos.strategy.model.Strategy;
import com.huge.ihos.strategy.service.StrategyManager;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;




public class StrategyAction extends JqGridBaseAction  {

	private StrategyManager strategyManager;
	private List<Strategy> strategies;
	private Strategy strategy;
	private File strategyFile;
	private String fileName;
	private Integer strategyId;
	private String physicalPath;


	public String strategyGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = strategyManager
					.getStrategyCriteria(pagedRequests,filters);
			this.strategies = (List<Strategy>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		Strategy oldStrategy=null;
		try {
			if(!this.isEntityIsNew()){
				oldStrategy=strategyManager.get(strategy.getStrategyId());
			}
			if(OtherUtil.measureNotNull(fileName)){
				 fileName = fileName.substring( fileName.lastIndexOf( "\\" ) + 1 );
                String serverPath = "//home//strategy//";
                serverPath = this.getRequest().getRealPath( serverPath );
                OptFile.mkParent( serverPath + "\\" + fileName );
                File targetFile = new File( serverPath + "\\" + fileName );
                OptFile.copyFile( strategyFile, targetFile );
                strategy.setAttachment(fileName);
                strategy.setAttachmentUrl(serverPath);
			}else if( !this.isEntityIsNew() ){
				strategy.setAttachment(oldStrategy.getAttachment());
				strategy.setAttachmentUrl(oldStrategy.getAttachmentUrl());
			}
			strategyManager.save(strategy);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "strategy.added" : "strategy.updated";
		return ajaxForward(this.getText(key));
	}
	
	public String downStragey() {
        try {
        	Strategy s = strategyManager.get( strategyId );

            HttpServletResponse resp = ServletActionContext.getResponse();
            // resp.setContentType("APPLICATION/OCTET-STREAM");
            resp.setHeader( "Content-Disposition", "attachment; filename="+new String( s.getAttachment().getBytes( "GBK" ), "ISO8859-1" ) );

            java.io.OutputStream outp = null;
            java.io.FileInputStream in = null;
            outp = resp.getOutputStream();
            in = new FileInputStream( this.getBackupDirPath() + "\\" + s.getAttachment() );
            byte[] b = new byte[1024];
            int i = 0;
            while ( ( i = in.read( b ) ) > 0 ) {
                outp.write( b, 0, i );
            }
            outp.flush();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
	
	public String existFile() throws Exception{
		File file = new File(physicalPath);
		if(!file.exists()){
			throw new Exception("文件不存在");
		}
		physicalPath="asdf";
		return SUCCESS;
	}
	
	private String getBackupDirPath() {
        String str = "//home//strategy//";
        str = this.getRequest().getRealPath( str );
        if ( !( new File( str ) ).isDirectory() ) {
            new File( str ).mkdir();
        }
        return str;
    }
	
	
    public String edit() {
        if (strategyId != null) {
        	strategy = strategyManager.get(strategyId);
        	this.setEntityIsNew(false);
        } else {
        	strategy = new Strategy();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String strategyGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					Strategy strategy = strategyManager.get(new Integer(removeId));
					strategyManager.remove(new Integer(removeId));
					
				}
				gridOperationMessage = this.getText("strategy.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkStrategyGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (strategy == null) {
			return "Invalid strategy Data";
		}

		return SUCCESS;

	}
	public StrategyManager getStrategyManager() {
		return strategyManager;
	}

	public void setStrategyManager(StrategyManager strategyManager) {
		this.strategyManager = strategyManager;
	}

	public List<Strategy> getstrategies() {
		return strategies;
	}

	public void setStrategys(List<Strategy> strategies) {
		this.strategies = strategies;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public Integer getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public File getStrategyFile() {
		return strategyFile;
	}
	public void setStrategyFile(File strategyFile) {
		this.strategyFile = strategyFile;
	}
	public String getPhysicalPath() {
		return physicalPath;
	}
	public void setPhysicalPath(String physicalPath) {
		this.physicalPath = physicalPath;
	}
}

