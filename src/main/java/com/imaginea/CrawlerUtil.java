package com.imaginea;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class CrawlerUtil {
	private static Logger logger =Logger.getLogger(CrawlerUtil.class);
	private static Scanner scan =new Scanner(System.in); 
	public static Document getDocument(String absUrl){
		logger.debug("getDocument() execution has started for rul : "+absUrl);
		Document doc=null;
		try { 
			doc = Jsoup.connect(absUrl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.debug("getDocument() execution has ended for rul : "+absUrl);
		return doc;
	}
	
	public static void setLogLevel(){
		System.out.println("Enter the log Level (OFF->6, FATAL->5, ERROR -> 4, WARN-> 3, INFO-> 2 and DEBUG->1): ");
	
		int  level= scan.nextInt();
		switch(level){
			case 1:
				setLEvelToAllLoggers(Level.DEBUG);
				break;
		    case 2:
		    	setLEvelToAllLoggers(Level.INFO);
		    	break;
	    	case 3:
	    		setLEvelToAllLoggers(Level.WARN);
	    		break;
	    	case 4:
	    		setLEvelToAllLoggers(Level.ERROR);
	    		break;
	    	case 5:
	    		setLEvelToAllLoggers(Level.FATAL);
	    		break;

	    	case 6:
	    		setLEvelToAllLoggers(Level.OFF);
	    		break;

    		default:
		    	System.out.println("Please enter Correct log level (OFF->6, FATAL->5, ERROR -> 4, WARN-> 3, INFO-> 2 and DEBUG->1):");
		    	setLogLevel();
		    }
		}

	public static void setLEvelToAllLoggers(Level level){
		Logger root = Logger.getRootLogger();
		Enumeration allLoggers = root.getLoggerRepository().getCurrentCategories();
		root.setLevel(level);
		while (allLoggers.hasMoreElements()){
	         Category tmpLogger = (Category) allLoggers.nextElement();
	         tmpLogger .setLevel(Level.DEBUG);
		}
	}
	
	
}
