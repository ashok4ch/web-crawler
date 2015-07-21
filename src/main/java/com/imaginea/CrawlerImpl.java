package com.imaginea;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	private List<MonthlyLinks> MonthlylinksList = null;
	private Document document=null;
	private int inputYear=0;
	private String inputYearText=null;
	private File rootDir = null;

	public List<MonthlyLinks> getMonthlylinksList() {
		return MonthlylinksList;
	}
	public void setMonthlylinksList(List<MonthlyLinks> monthlylinksList) {
		MonthlylinksList = monthlylinksList;
	}
	
	public File getRootDir() {
		return rootDir;
	}
	public List<MonthlyLinks> getMonthlywiselinks() {
		return Collections.unmodifiableList(this.getMonthlylinksList());
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
		//this.Monthlywiselinks=new HashMap<String, MonthlyLinks>();
		this.MonthlylinksList = new ArrayList<MonthlyLinks>();
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
			//this.createDirs("/"+inputYearText);
			this.createDirs();
			this.loadLinks();
			//below code commented as per as part of design changes to improve performance
			/*MailLoader mailloader = new MailLoaderImpl();
			mailloader.processMailLoader(this.getMonthlywiselinks());*/
		}
		
		logger.debug("executeCrawler execution end");
	}
	public void createDirs(){
		logger.debug("Method createDirs() started to create Root directory ");
		this.rootDir=new File(Crawler.targetRootDIRPath+File.separator+inputYear);
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
					
					//loadmonthylinks(monthLink, monthName);// Before review application flow
						
				}
				break; 
			}
		}
		logger.debug("loadLinks execution has ended");
		
	}
	
	public void loadmonthylinks(String monthLink,String monthName){
		
		List<Mail> monthlinkslist = new ArrayList<Mail>();
		
		File monthlyDir =new File(this.getRootDir(), monthName);
		monthlyDir.mkdirs();
		
		Document monthDoc= CrawlerUtil.getDocument(monthLink);
		Elements monthlinks = monthDoc.getElementById("msglist").getElementsByTag("tbody").first().getElementsByTag("tr");
		for(int i=0; i<monthlinks.size(); i++){
			String msgfileName=monthlinks.get(i).text(); // test for msg naem
			String msgLink = monthlinks.get(i).select("a").attr("abs:href");
			if(msgLink.isEmpty()){
				continue;
			}
			Mail mail= new Mail();
			mail.setMsgName(msgfileName);
			mail.setMsgLink(msgLink.replaceFirst(".mbox/%", ".mbox/raw/%"));
			
		
			monthlinkslist.add(mail);
		}
		MonthlyLinks monLinks = new MonthlyLinks();
		monLinks.setMonthDir(monthlyDir);
		monLinks.setMonthName(monthName);
		monLinks.setMonthLinks(monthLink);
		monLinks.setLinksList(monthlinkslist);
	    this.MonthlylinksList.add(monLinks);
		
	}

	public void loadmonthyMails(String monthLink,String monthName){
		logger.debug("loadmonthyMails() method execution started for the month"+monthName);
		File monthlyDir =new File(this.getRootDir(), monthName);
		monthlyDir.mkdirs();
		Document monthDoc= CrawlerUtil.getDocument(monthLink);
		Elements monthlinks = monthDoc.getElementById("msglist").getElementsByTag("tbody").first().getElementsByTag("tr");
		ExecutorService executor = Executors.newFixedThreadPool(20);
		
		for(int i=0; i<monthlinks.size(); i++){
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
			MailLoadThread mailLoader = new MailLoadThread(monthlyDir, mail);
			executor.execute(mailLoader);
		}
		executor.shutdown();
		logger.debug("loadmonthyMails() method execution has ended for the month"+monthName);
	}
	
	
	public String getRootDirPath(){
		return (rootDir != null) ? rootDir.getAbsolutePath() : "" ;
	}
	
	
	 public class MonthlyLinks{
		 private String monthName  = null;
		 private String monthLinks = null;
		 private List<Mail> linksList = null;
		 private File monthDir =null;
		 /**
		 * @return the monthDir
		 */
		public File getMonthDir() {
			return monthDir;
		}
		/**
		 * @param monthDir the monthDir to set
		 */
		public void setMonthDir(File monthDir) {
			this.monthDir = monthDir;
		}
		
		 
		public String getMonthName() {
			return monthName;
		}
		public void setMonthName(String monthName) {
			this.monthName = monthName;
		}
		public String getMonthLinks() {
			return monthLinks;
		}
		public void setMonthLinks(String monthLinks) {
			this.monthLinks = monthLinks;
		}
		public List<Mail> getLinksList() {
			return linksList;
		}
		public void setLinksList(List<Mail> linksList) {
			this.linksList = linksList;
		}
	}

}

class MailLoadThread implements Runnable{
	private File  targetDir = null;
	private Mail mailObject = null;
	public  MailLoadThread(File  targetDir,Mail mailObject) {
		// TODO Auto-generated constructor stub
		this.targetDir	=	targetDir;
		this.mailObject	=	mailObject;
	}
	 @Override
	 public void run(){
		 
		 MailLoader mailloader = new MailLoaderImpl();
		 mailloader.processMailLoader(this.targetDir,this.mailObject);
	 }
	 
}

