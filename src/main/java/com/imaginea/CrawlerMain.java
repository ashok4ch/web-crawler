package com.imaginea;

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerMain {
	public final static Logger logger = Logger.getLogger(CrawlerMain.class);
	public static void main(String[] args) {
		//CrawlerUtil.setLogLevel();
		stratCrawler();
	}
		
		public static void stratCrawler(){
		logger.debug("Crawler manin method execution start");
		Scanner scan = new Scanner(System.in);
	    System.out.println("Enter Input Year In YYYY formate: ");
	    long startTime= System.currentTimeMillis();
		int inputValue = scan.nextInt();
		scan.close();
		
		try {
			Crawler crawler = new CrawlerImpl(inputValue);
			crawler.executeCrawler();
			logger.debug("Process execution time is : "+ (System.currentTimeMillis()-startTime));
		} catch (Exception e) {
			logger.debug("Process execution time is : "+ (System.currentTimeMillis()-startTime));
			e.printStackTrace();
		}
		
	}
		
		public static void  test(Document doc){
			Elements elements= doc.getElementsByClass("year");
			for(Element element : elements){
				if(element.getElementsByTag("thead").text().equals("Year 2014")){
					Elements trs = element.getElementsByTag("tbody").first().getElementsByTag("tr");
					for(Element trElement : trs){
						String monthName= trElement.getElementsByClass("date").first().text();
						String monthLink= trElement.select("a").first().attr("abs:href");
						//int noOfpages = 3;
						//Code to load the all mails links of respective month
						logger.debug("Links of "+monthName+" month load start ");
						logger.debug("Links is "+trElement.select("a"));
						if(monthLink.isEmpty())
							continue;
						//loadmonthyMails(monthLink, monthName);
						break;
					}
					break; 
				}
			}
		
		}
	
}