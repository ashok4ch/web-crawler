package com.imaginea;

import java.util.Enumeration;
import java.util.Scanner;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CrawlerMain {
	public final static Logger _logger = Logger.getLogger(CrawlerMain.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		stratCrawler();
		//setLogLevel();

	}
		
	public static void setLogLevel(){
		Logger root = Logger.getRootLogger();
		Enumeration<Category> allLoggers = root.getLoggerRepository().getCurrentCategories();
	System.out.println("Enter the log Level (OFF->6, FATAL->5, ERROR -> 4, WARN-> 3, INFO-> 2 and DEBUG->1): ");
		Scanner scan =new Scanner(System.in); 
		int  level= scan.nextInt();
		scan.close();
		switch(level){
			case 1:
				root.setLevel(Level.DEBUG);
				 while (allLoggers.hasMoreElements()){
					         Category tmpLogger = (Category) allLoggers.nextElement();
					         tmpLogger .setLevel(Level.ERROR);
					  }

				break;
		    case 2:
		    		root.setLevel(Level.INFO);
		    		break;
		    	case 3:
		    		root.setLevel(Level.WARN);
		    		break;
		    	case 4:
		    		root.setLevel(Level.ERROR);
		    		break;
		    	case 5:
		    		root.setLevel(Level.FATAL);
		    		break;
		    	case 6:
		    		root.setLevel(Level.OFF);
		    		break;
		    	default:
		    	System.out.println("Please enter Correct log level (OFF->6, FATAL->5, ERROR -> 4, WARN-> 3, INFO-> 2 and DEBUG->1):");
		    	setLogLevel();
		    }
		}
	
	public static void stratCrawler(){
			
		_logger.debug("Crawler manin method execution start");
		Scanner scan = new Scanner(System.in);
	    System.out.println("Enter Input Year In YYYY formate: ");
	    long startTime= System.currentTimeMillis();
		int inputValue = scan.nextInt();
		scan.close();
		try {
			Crawler crawler = new CrawlerImp(inputValue);
			crawler.executeCrawler();
			_logger.debug("Process execution time is : "+ (System.currentTimeMillis()-startTime));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_logger.debug("Process execution time is : "+ (System.currentTimeMillis()-startTime));
			e.printStackTrace();
		}
		
	}
	
}