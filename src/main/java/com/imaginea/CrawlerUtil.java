package com.imaginea;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class CrawlerUtil {
	// We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT ="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private static Logger logger =Logger.getLogger(CrawlerUtil.class);
	private static Scanner scan =new Scanner(System.in); 
	public static Document getDocument(String absUrl){
		logger.debug("getDocument() execution has started for rul : "+absUrl);
		Document doc=null;
		try { 
			  Connection connection = Jsoup.connect(absUrl).userAgent(USER_AGENT);
			  doc= connection.get();
	    }catch(HttpStatusException hse){
			logger.fatal("The given URL:"+absUrl+" is not ccorrect. Please correct it ("+hse.getMessage()+")");
		
		}catch (UnknownHostException ukhe) {
				logger.fatal("Crawler can not able to connect inter net/ system in Offline please check your internate connection.");
		}catch (IOException e) {
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
