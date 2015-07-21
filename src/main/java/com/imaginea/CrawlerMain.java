package com.imaginea;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

public class CrawlerMain {
	public final static Logger logger = Logger.getLogger(CrawlerMain.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//CrawlerUtil.setLogLevel();
		stratCrawler();
		ArrayBlockingQueue<String> bQueue = new ArrayBlockingQueue<>(20);
		try {
			bQueue.put("Ashok ");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
}