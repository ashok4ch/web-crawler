package com.imaginea;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/*
 *CrawlerImp does loads all the mails of specified year 
 */
public class CrawlerImpl implements Crawler{
	public final static Logger logger = Logger.getLogger(CrawlerMain.class);
	private Document document=null;
	private int inputYear=0;
	private String inputYearText=null;
	private File rootDir = null;

	
	private File getRootDir() {
		return rootDir;
	}
	public Document getDocument() {
		return document;
	}
	public int getInputYear() {
		return inputYear;
	}
	public String getInputYearText() {
		return inputYearText;
	}

	public CrawlerImpl( int inputYear){
		this.inputYear=inputYear;
		this.inputYearText= "Year "+inputYear;
		this.document = CrawlerUtil.getDocument(Crawler.targetURL);//Jsoup.connect(Crawler.targetURL).get();
	}
	private boolean isInputYearValid(int inputYear){
		logger.debug("isInputYearValid execution start");
		String textValue ="Year "+inputYear;
		return this.document.text().contains(textValue);
	
	}
	
	public void executeCrawler(){
		logger.debug("executeCrawler execution start");
		if(this.isInputYearValid(this.inputYear)==false){
			System.out.println("Entered Input Year"+inputYear+" is not valid/has no emails Please enter valid :");
			//CrawlerMain.stratCrawler();
			
			return;
		}
		if(this.isInputYearValid(inputYear)==true){
			this.createDirs();
			this.loadLinks();
		}
		
		logger.debug("executeCrawler execution end");
	}
	public void createDirs(){
		logger.debug("Method createDirs() started to create Root directory ");
		this.rootDir=new File(Crawler.targetRootDIRPath+File.separator+"Normal_Crawler"+File.separator+inputYear);
			this.getRootDir().delete();
			//this.getRootDir().deleteOnExit();
		this.getRootDir().mkdirs();
	}
	private void loadLinks(){
		logger.debug("loadLinks execution start");
		//loading all Months links of the input year
	
		Elements elements= this.document.getElementsByClass("year");
		for(Element element : elements){
			if(element.getElementsByTag("thead").text().equals(this.inputYearText)){
				Elements trs = element.getElementsByTag("tbody").first().getElementsByTag("tr");
				for(Element trElement : trs){
					String monthName= trElement.getElementsByClass("date").first().text();
					String monthLink= trElement.select("a").first().attr("abs:href");
					//Code to load the all mails links of respective month
					logger.debug("Links of "+monthName+" month load start ");
					if(monthLink.isEmpty())
						continue;
					loadmonthyMails(monthLink, monthName);
				}
				break; 
			}
		}
		logger.debug("loadLinks execution has ended");
		
	}
	
	public void loadmonthyMails(String monthLink,String monthName){
		logger.debug("loadmonthyMails() method execution started for the month"+monthName);
		File monthDir =new File(this.getRootDir(), monthName);
		monthDir.mkdirs();
		Document monthDoc= CrawlerUtil.getDocument(monthLink);
		int noOfPage = (monthDoc.getElementsByClass("pages").select("a").size() -1);
		// if the month has only one page below if  block execute.
		if(noOfPage <= 0){
			ProcessPage(monthDoc, monthDir);
			return ;
		}
		// To handle the multiple pages of a month mails
		for(int j=0; j <= noOfPage; j++){
			String PageLink = monthLink+"?"+j;
			Document pageDoc = CrawlerUtil.getDocument(PageLink);
			ProcessPage(pageDoc, monthDir);
			
		}
		
		logger.debug("loadmonthyMails() method execution has ended for the month"+monthName);
	}
	
	//private void
	/*private String getRootDirPath(){
		return (rootDir != null) ? rootDir.getAbsolutePath() : "" ;
	}*/
	
	private void ProcessPage(Document pageDoc, File monthDir)
	{
		ExecutorService executor = Executors.newFixedThreadPool(20);
		Elements monthlinks = pageDoc.getElementById("msglist").getElementsByTag("tbody").first().getElementsByTag("tr");
		int linkesSize = monthlinks.size();
		for(int i=0; i<linkesSize; i++){
			String msgfileName=monthlinks.get(i).text(); // test for msg naem
			if(msgfileName.length() >150)
				msgfileName = msgfileName.substring(0, 150)+"...";
			String msgLink = monthlinks.get(i).select("a").attr("abs:href");
			if(msgLink.isEmpty()){
				continue;
			}
			Mail mail= new Mail();
			mail.setMsgName(msgfileName);
			mail.setMsgLink(msgLink.replaceFirst(".mbox/%", ".mbox/raw/%"));
			mail.setFileDirectory(monthDir);
			MailLoadThread mailLoader = new MailLoadThread(mail);
			executor.execute(mailLoader);
		}
		executor.shutdown();
	}
	
}

class MailLoadThread implements Runnable{
	//private File  targetDir = null;
	private Mail mailObject = null;
	public  MailLoadThread(Mail mailObject) {
		// TODO Auto-generated constructor stub
		//this.targetDir	=	targetDir;
		this.mailObject	=	mailObject;
	}
	 @Override
	 public void run(){
		 
		 MailLoader mailloader = new MailLoaderImpl();
		 mailloader.processMailLoader(this.mailObject);
	 }
	 
}

