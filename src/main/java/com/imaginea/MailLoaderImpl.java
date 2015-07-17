/**
 * 
 */
package com.imaginea;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.imaginea.CrawlerImp.MonthlyLinks;

/**
 * @author ashokh
 * 
 */
public class MailLoaderImpl implements MailLoader {
	private static Logger _logger =Logger.getLogger(MailLoaderImpl.class);
	
	public void processMailLoader(Crawler ctlm){
		_logger.debug("Mothos processMailLoader execution start");
		List<MonthlyLinks> mLinksList = ctlm.getMonthlywiselinks();
		ExecutorService executor = Executors.newFixedThreadPool(4);//creating a pool of 5 threads
		for(MonthlyLinks monthObj : mLinksList){
	        Runnable worker = new MonthlyMailLoadThread(monthObj);  
	        executor.execute(worker);//calling execute method of ExecutorService  
	    }
		executor.shutdown();
		_logger.debug("Mothod processMailLoader execution End");
		
	}
	
	public String processMailLoader(String linkURL){
		_logger.debug("processMailLoader for URL : "+linkURL);
		Document doc=	CrawlerUtil.getDocument(linkURL);
		return (doc != null) ? doc.text():"";
		
	}
}


  class MonthlyMailLoadThread implements Runnable{
	  private MonthlyLinks monthObj= null; 
	  private final Logger _logger= Logger.getLogger(MonthlyMailLoadThread.class);
	  public MonthlyMailLoadThread(MonthlyLinks object){
		  this.monthObj=object;
		  
	  }
	  
	  @Override
	  public void run(){

		  _logger.debug("MonthlyLoaderThread execution started for month : "+monthObj.getMonthName());
		//MonthlyLinks monthObj = monthsLinks.get(key);
		List<Mail> msglinkslist = monthObj.getLinksList();
		for(Mail mail : msglinkslist){
			String link =mail.getMsgLink();
			if(link.isEmpty())
				continue;
			
			Document mailDoc	=  CrawlerUtil.getDocument(link); //(link !=null)?processMailLoader(link):"empty";
			String msg = mailDoc.text();
			if(msg.isEmpty())
				continue;

			File msgFile = new File(monthObj.getMonthDir(),mail.getMsgName().replaceAll("[-+.^:,()?\\//*\"<>|]","")+".txt");
			FileWriter fileWriter=null;
			BufferedWriter bWriter=null;
			try {
				fileWriter = new FileWriter(msgFile);
				bWriter = new BufferedWriter(fileWriter);
				bWriter.write(msg);
				bWriter.close();
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
				_logger.error(ioe.getMessage());
			}							
		}
		_logger.debug("MonthlyLoaderThread execution has done for month : "+monthObj.getMonthName());
	 }
  
  }
