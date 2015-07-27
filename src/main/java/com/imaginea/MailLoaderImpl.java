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

	public void processMailLoader(Mail mailObject){
		logger.debug("processMailLoader for URL : "+mailObject.getMsgLink());
		Document doc=	CrawlerUtil.getDocument(mailObject.getMsgLink());
		if(doc == null){
			logger.error("the Documement return null to link :"+mailObject.getMsgLink());
			return;
		}
		String msg = doc.text();
		File msgFile = new File(mailObject.getFileDirectory(),mailObject.getMsgName().replaceAll("[-+.^:,()?\\//*\"<>|=]","")+".txt");
		saveMail( msgFile, msg);
	}
	
	public void saveMail(File fileObj,String msg){
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

 

