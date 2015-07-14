package com.imaginea;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerToLoadMailsImp implements CrawlerToLoadMails{
	//private Map<String, Map<String, List<String>>> Monthlywiselinks= null;//new HashMap<String, Map<String, List<String>>>();
	private Map<String, MonthyLinks> Monthlywiselinks= null;
	private Document document=null;
	private int inputYear=0;
	private String inputYearText=null;
	//Map<String, List<String>> MonthlylinksList= new HashMap<String, List<String>>();
	public CrawlerToLoadMails getCrawler(int inputYear){
		 return new CrawlerToLoadMailsImp(inputYear);
		
	}
	
	private CrawlerToLoadMailsImp( int inputYear){
		//this.Monthlywiselinks=new HashMap<String, Map<String, List<String>>>();
		this.Monthlywiselinks=new HashMap<String, MonthyLinks>();
		this.inputYear=inputYear;
		this.inputYearText= "Year "+inputYear;
		try {
			this.document = Jsoup.connect(CrawlerToLoadMails.targetURL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isInputYearValid(int inputYear){
		String textValue ="Year "+inputYear;
		/*int index = this.document.text().indexOf(textValue);
	    if(index >0)
	    	return true;
	    else
	    	return false;*/
		return this.document.text().contains(textValue);
	}
	
	public void executeCrawler(){
		if(this.isInputYearValid(this.inputYear)==false){
			System.out.println("Entered Input Year"+inputYear+" is not valide/has no emails");
			return;
		}
		if(this.isInputYearValid(inputYear)==true){
			//this.createDirs("/"+inputYearText);
			this.loadLinks();
		}
	}
	public void validateInputYear(int inputYear) throws IOException{
		String textValue ="Year "+inputYear;
		Document doc =this.document;//Jsoup.connect(CrawlerToLoadMails.targetURL).get();
		Elements elements= doc.getElementsByClass("year");
		boolean isValideYear = false;
		for(Element element : elements){
		
			System.out.println(element.getElementsByTag("thead").text().equals(textValue));
			if(element.getElementsByTag("thead").text().equals(textValue)){
				isValideYear= true;
				//Monthlywiselinks.put(key, value)
				
				for(Element DateEle : element.getElementsByClass("date")){
					System.out.println(DateEle.text());
					Monthlywiselinks.put(DateEle.text(), new MonthyLinks());
				}
				break;
			}
		}

		if(isValideYear== false)
			System.out.println("the input year is not correct");
		
	}
	public boolean createDirs(String dirPath){
		File fileDir=new File(dirPath);
		fileDir.deleteOnExit();
		fileDir.mkdirs();
		return true;
	}
	public void loadLinks(){
		//loading all Months links of the input year
		//Element element=null;
		Elements elements= this.document.getElementsByClass("year");
		for(Element element : elements){
			if(element.getElementsByTag("thead").text().equals(this.inputYearText)){
				for(Element DateEle : element.getElementsByClass("date")){
					System.out.println(DateEle.text());
					String monthName= DateEle.text();
					String monthLink= "MonthLink";
					this.Monthlywiselinks.put(monthName, new MonthyLinks());
					
				}
				
				break;
			}
		}
		
		//Loading all links of each month
		
	}
	
	public void ProcessLinks() {
		// TODO Auto-generated method stub
		Set <String>keySet = this.Monthlywiselinks.keySet();
		for(String monthName :keySet){
			String dirPath = File.pathSeparator+inputYearText+File.pathSeparator+monthName;
			this.createDirs(dirPath);
			//this.Monthlywiselinks.get(monthName)
		}	
	}
	public void loadMail(String mailLink){
		
	}
    public void saveMail(){
    	
    }

 class MonthyLinks{
	 private String monthName  = null;
	 private String monthLinks = null;
	 private List linksList = null;
	 
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
	public List getLinksList() {
		return linksList;
	}
	public void setLinksList(List linksList) {
		this.linksList = linksList;
	}
}



}
