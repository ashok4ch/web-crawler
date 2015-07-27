package com.imaginea;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class CrawlerMain {
	public final static Logger logger = Logger.getLogger(CrawlerMain.class);
	public static void main(String[] args) {
		//CrawlerUtil.setLogLevel();
		
		/*Document doc = CrawlerUtil.getDocument("http://mail-archives.apache.org/mod_mbox/maven-users/201507.mbox/%3CCAOe3-fZbQ4QeUNDkYyUUZ7bsoUFa_+EN9hxqDh+Ozm6ybVecKw@mail.gmail.com%3E");
		System.out.println("doc"+"IS true : "+(doc.text().contains("form")));
		*/
		stratCrawler();
	}
	public static void stratCrawler(){
		logger.debug("Crawler manin method execution start");
		Scanner scan = new Scanner(System.in);
	    System.out.println("Enter Input Year In YYYY formate: ");
	    long startTime= System.currentTimeMillis();
		int inputValue = scan.nextInt();
		scan.close();
		try{
			Crawler crawler = new CrawlerImpl(inputValue);
			crawler.executeCrawler();
			logger.debug("Process execution time is : "+ (System.currentTimeMillis()-startTime));
		}catch(Exception e){
			logger.debug("Process execution time is : "+ (System.currentTimeMillis()-startTime));
			e.printStackTrace();
		}
	}
}