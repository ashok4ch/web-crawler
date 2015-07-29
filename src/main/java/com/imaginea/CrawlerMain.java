package com.imaginea;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class CrawlerMain {
	public final static Logger logger = Logger.getLogger(CrawlerMain.class);
	public static void main(String[] args) {
		Crawler crawler = new GenericCrawlerImpl();
		crawler.executeCrawler();
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