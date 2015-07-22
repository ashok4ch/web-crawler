/**
 * 
 */
package com.imaginea;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

/**
 * @author ashokh
 * 
 */
public class MailLoaderImpl implements MailLoader {
	private static Logger logger =Logger.getLogger(MailLoaderImpl.class);
	
	/*public void processMailLoader(List<MonthlyLinks > mLinksList){
		logger.debug("Mothos processMailLoader execution start");
		ExecutorService executor = Executors.newFixedThreadPool(6);//creating a pool of 5 threads
		for(MonthlyLinks monthObj : mLinksList){
	        Runnable worker = new MonthlyMailLoadThread(monthObj);  
	        executor.execute(worker);//calling execute method of ExecutorService  
	    }
		executor.shutdown();
		logger.debug("Mothod processMailLoader execution End");
		
	}*/
	
	public void processMailLoader(Mail mailObject){
		logger.debug("processMailLoader for URL : "+mailObject.getMsgLink());
		Document doc=	CrawlerUtil.getDocument(mailObject.getMsgLink());
		if(doc == null){
			logger.error("the Documement return null to link :"+mailObject.getMsgLink());
			return;
		}
		String msg = doc.text();
		File msgFile = new File(mailObject.getFileDirectory(),mailObject.getMsgName().replaceAll("[-+.^:,()?\\//*\"<>|=]","")+".txt");
		saveMail(mailObject.getFileDirectory(), msgFile, msg);
	}
	
	public void saveMail(File targetDir, File fileObj,String msg){
		FileWriter fileWriter=null;
		BufferedWriter bWriter=null;
		try {
			fileWriter = new FileWriter(fileObj);
			bWriter = new BufferedWriter(fileWriter);
			bWriter.write(msg);
			bWriter.close();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
			logger.error(ioe.getMessage());
		}		
	}
}

/*  class MonthlyMailLoadThread implements Runnable{
	  private MonthlyLinks monthObj= null; 
	  private final Logger logger= Logger.getLogger(MonthlyMailLoadThread.class);
	  public MonthlyMailLoadThread(MonthlyLinks object){
		  this.monthObj=object;
		  
	  }
	  
	  @Override
	  public void run(){

		  logger.debug("MonthlyLoaderThread execution started for month : "+monthObj.getMonthName());
		//MonthlyLinks monthObj = monthsLinks.get(key);
		List<Mail> msglinkslist = this.monthObj.getLinksList();
		for(Mail mail : msglinkslist){
			String link =mail.getMsgLink();
			if(link.isEmpty())
				continue;
			
			Document mailDoc	=  CrawlerUtil.getDocument(link); //(link !=null)?processMailLoader(link):"empty";
			String msg = mailDoc.text();
			if(msg.isEmpty())
				continue;

			File msgFile = new File(monthObj.getMonthDir(),mail.getMsgName().replaceAll("[-+.^:,()?\\//*\"<>|=]","")+".txt");
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
				logger.error(ioe.getMessage());
			}							
		}
		logger.debug("MonthlyLoaderThread execution has done for month : "+monthObj.getMonthName());
	 }
  
  }
*/  
  
