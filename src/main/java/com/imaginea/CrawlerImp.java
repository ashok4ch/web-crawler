package com.imaginea;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/*
 *CrawlerImp does loads all the mails of specified year 
 */
public class CrawlerImp implements Crawler{
	public final static Logger _logger = Logger.getLogger(CrawlerMain.class);
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

	public CrawlerImp( int inputYear){
		//this.Monthlywiselinks=new HashMap<String, MonthlyLinks>();
		this.MonthlylinksList = new ArrayList<MonthlyLinks>();
		this.inputYear=inputYear;
		this.inputYearText= "Year "+inputYear;
		this.document = CrawlerUtil.getDocument(Crawler.targetURL);//Jsoup.connect(Crawler.targetURL).get();
	}
	private boolean isInputYearValid(int inputYear){
		_logger.debug("isInputYearValid execution start");
		String textValue ="Year "+inputYear;
		return this.document.text().contains(textValue);
	}
	
	public void executeCrawler(){
		_logger.debug("executeCrawler execution start");
		if(this.isInputYearValid(this.inputYear)==false){
			System.out.println("Entered Input Year"+inputYear+" is not valide/has no emails");
			return;
		}
		if(this.isInputYearValid(inputYear)==true){
			//this.createDirs("/"+inputYearText);
			this.createDirs();
			this.loadLinks();
			MailLoader mailloader = new MailLoaderImpl();
			mailloader.processMailLoader(this);
		}
		
		_logger.debug("executeCrawler execution end");
	}
	public void createDirs(){
		_logger.debug("Method createDirs() started to create Root directory ");
		this.rootDir=new File(Crawler.targetRootDIRPath+File.separator+inputYear);
			this.getRootDir().delete();
			//this.getRootDir().deleteOnExit();
		this.getRootDir().mkdirs();
	}
	private void loadLinks(){
		_logger.debug("loadLinks execution start");
		//loading all Months links of the input year
		Elements elements= this.document.getElementsByClass("year");
		for(Element element : elements){
			if(element.getElementsByTag("thead").text().equals(this.inputYearText)){
				Elements trs = element.getElementsByTag("tbody").first().getElementsByTag("tr");
				for(Element trElement : trs){
					String monthName= trElement.getElementsByClass("date").first().text();
					String monthLink= trElement.select("a").first().attr("abs:href");
					//Code to load the all mails links of respective month
					_logger.debug("Links of "+monthName+" month load start ");
					if(monthLink.isEmpty())
						continue;
					loadmonthylinks(monthLink, monthName);
	
					//break;//testing
				}
				break; 
			}
		}
		
		//Loading all links of each month
	}
	
	public void loadmonthylinks(String monthLink,String monthName){
		
		List<Mail> monthlinkslist = new ArrayList<Mail>();
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
		File monthlyDir =new File(this.getRootDir(), monthName);
		//System.out.println("monthlyDir path : "+monthlyDir.getAbsolutePath());
		monthlyDir.mkdirs();
		monLinks.setMonthDir(monthlyDir);
		monLinks.setMonthName(monthName);
		monLinks.setMonthLinks(monthLink);
		monLinks.setLinksList(monthlinkslist);
	    this.MonthlylinksList.add(monLinks);
		
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
