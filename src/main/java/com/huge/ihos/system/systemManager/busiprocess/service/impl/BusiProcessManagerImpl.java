package com.huge.ihos.system.systemManager.busiprocess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.dao.GenericSPDao;
import com.huge.ihos.system.systemManager.busiprocess.dao.BusiProcessDao;
import com.huge.ihos.system.systemManager.busiprocess.dao.BusiProcessLogDao;
import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcess;
import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcessLog;
import com.huge.ihos.system.systemManager.busiprocess.service.BusiProcessManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("busiProcessManager")
public class BusiProcessManagerImpl extends GenericManagerImpl<BusiProcess, Long> implements BusiProcessManager {
    private BusiProcessDao businessProcessDao;
    private BusiProcessLogDao businessProcessLogDao;
    private GenericSPDao spDao;
    

    @Autowired
    public BusiProcessManagerImpl(BusiProcessDao businessProcessDao) {
        super(businessProcessDao);
        this.businessProcessDao = businessProcessDao;
    }
    
    @Autowired
    public void setBusinessProcessLogDao(BusiProcessLogDao businessProcessLogDao) {
		this.businessProcessLogDao = businessProcessLogDao;
	}

	@Autowired
    public void setSpDao(GenericSPDao spDao) {
		this.spDao = spDao;
	}

	public JQueryPager getBusinessProcessCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return businessProcessDao.getBusinessProcessCriteria(paginatedList,filters);
	}

	@Override
	public void execBusinessProcess(final String  code,final Object... args) {
		ExecutorService exec = Executors.newSingleThreadExecutor();
		try{
			exec.submit(new Runnable(){
				public void run(){
					
					/* 1.检查上次执行的结果是否有错，若没错，继续下一步；若有错，进行错误处理
					 * 	错误处理：循环5次，如果还是失败则忽略
					 * 2.执行该业务对应的存储过程
					 */
					checkLastExecStatus(code,args);
					execBusinessProcess1(code,args);
				}
			});
			exec.shutdown();
		}catch(Exception e){
			if(!exec.isShutdown()){
				log.error("task not finished");
			}
		}
	}
	
	private void checkLastExecStatus(String code,Object... args){
		/*
		 * 获取入库的实时业务配置BusinessProcess列表[不包含忽略执行结果的]
		 * 获取上一次入库的单据号docNo----从log表取
		 * 获取日志表中docNo上一次的执行情况
		 * 检查执行情况并判断是否需要reRun
		 */
		List<BusiProcess> busProcList = businessProcessDao.getBusinessProcessByCode(code,false);
		if(busProcList==null){
			log.info("no execResult sensitive BusinessProcess with code:"+code);
			return;		//没有对结果敏感的存储过程，直接返回
		}
		String ids = "";
		for (BusiProcess bp : busProcList) {
			ids += "'" + bp.getId() + "',";
		}
		ids = OtherUtil.subStrEnd(ids, ",");
		
		String lastExecId = businessProcessLogDao.getlastExecDetailId(ids);// 0
		if(lastExecId==null){
			log.info("no last record in BusinessProcessLog with code:"+code);
			return;		//没有上次执行的记录,直接返回，实际意义为第一次执行该业务或者日志记录被清除
		}
		
		List<BusiProcessLog> busProcLogList = businessProcessLogDao.getLastExecStatus(ids,lastExecId);//
		boolean needReRun = false;
		for(BusiProcessLog bpl:busProcLogList){
			if(!bpl.getExecStatus().equals(BusiProcessLog.EXEC_SUCCESS)){
				needReRun = true;
				break;
			}
		}
		if(!needReRun){
			log.info("last BusinessProcess executed successfully with code:"+code+"");
			return;		//上次执行全部成功
		}else{
			log.info("reRun BusinessProcess [ code:"+code+",execId:"+lastExecId+" ]");
			Object[] objects = new Object[args.length];
			for(int i=0;i<objects.length;i++){
				objects[i] = args[i];
			}
			objects[objects.length-1] = lastExecId;
			int loopCount = 1;
			boolean execResult = false;
			do{
				execResult = execBusinessProcess1(code,objects);
				loopCount++;
			}while((loopCount<=5) && (!execResult));
		}
	}
	/**
	 * 
	 * @param code
	 * @param args
	 * @return true:业务执行成功；false：业务执行失败
	 */
	private boolean execBusinessProcess1(String code,Object... args){
		List<BusiProcess> busProcList = businessProcessDao.getBusinessProcessByCode(code,null);
		if(busProcList==null){
			log.warn("no BusinessProcess setted with code:"+code);
			return true;
		}
		ArrayList<Boolean> execResults = new ArrayList<Boolean>();
		String spName = "";
		/*
		 * 向sy_business_process_log表插入执行记录，初始化执行状态
		 * 执行存储过程
		 * 根据执行结果改写sy_business_process_log表的执行状态
		 */
		BusiProcessLog busProLog = null;
				
		for(BusiProcess bp:busProcList){
			busProLog = new BusiProcessLog();
			busProLog.setBusPro(bp);
			busProLog.setDetailId(args[args.length-1].toString());
			busProLog.setExecTime(new Date());
			busProLog.setExecStatus(BusiProcessLog.EXEC_INITIALIZATION);
			busProLog = businessProcessLogDao.save(busProLog);
			
			log.info("执行业务："+bp.getName()+"--顺序:"+bp.getSequence()+"--存储过程："+bp.getSpName());
			spName = bp.getSpName();
			//存储过程参数:单位、帐套、期间、操作员 
			String execMsg = spDao.processSp(spName, args);//执行存储过程
			//改写执行状态
			if(execMsg.equals("处理成功。")){
				busProLog.setExecStatus(BusiProcessLog.EXEC_SUCCESS);
				busProLog.setRemark(execMsg);
				execResults.add(true);
			}else{
				busProLog.setExecStatus(BusiProcessLog.EXEC_FAILED);
				execResults.add(false);
				if(!execMsg.equals("处理失败。")){
					busProLog.setRemark(execMsg);
				}
			}
			businessProcessLogDao.save(busProLog);
		}
		boolean execResult = true;
		for(Boolean result:execResults){//只要有一个失败 即认为该次业务执行失败
			if(!result){
				execResult = false;
				break;
			}
		}
		return execResult;
		
	}
    
    
}