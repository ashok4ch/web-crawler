package com.imaginea;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class GenericCrawlerImpl implements Crawler{
	private static final Logger logger= Logger.getLogger(GenericCrawlerImpl.class);
	private Set<String> urlsVisited = new HashSet<String>();
	private List<String> urlsToVisit = new LinkedList<String>();
	public static File rootDir = new File(Crawler.targetRootDIRPath+File.separator+"Generic_Crawler");
	public static String rootUrl=null;
	static{
			rootDir.mkdirs();
	}
	public GenericCrawlerImpl(){
		rootUrl= Crawler.targetURL;
	}
	
	public GenericCrawlerImpl(String url){
		rootUrl=url;	
	}
	
	public void executeCrawler(){
	
		processURL(rootUrl);
	}
	
	private void processURL(String url){

	      while(true)
	      {
	          String currentUrl;
	          CrawlerLeg leg = new CrawlerLeg();
	          if(this.urlsToVisit.isEmpty()){
	              currentUrl = url;
	              this.urlsVisited.add(url);
	          }else{
	        	 String nextUrl=this.nextUrl();
	             if("exit".equals(nextUrl)){
	            	 logger.info("Urls to visit list is empty so application is sutdowning.");
			    	 break;
	             }
	             currentUrl = nextUrl;
	          }
	          leg.crawl(currentUrl); // Lots of stuff(loading links and validating document whether is it email if yes saving it else loading all it's links ) happening here. Look at the crawl method in
	          if(!leg.getLinks().isEmpty()){
	        	  this.urlsToVisit.addAll(leg.getLinks());
	        	  logger.info(" this.urlsToVisit size is : "+ this.urlsToVisit.size()+"::::"+leg.getLinks().size());
	          }
	          if(this.urlsToVisit.isEmpty())
	        	  break;
	      }
	      logger.debug("\n**Done** Visited " + this.urlsVisited.size() + " web page(s)");
	}
	
	private String nextUrl()
	  {
	      String nextUrl;
	      do
	      {
		       if(this.urlsToVisit.isEmpty()){
		    	   return "exit";
		       }
		       nextUrl = this.urlsToVisit.remove(0);
		       
	    	} while(this.urlsVisited.contains(nextUrl));
	      this.urlsVisited.add(nextUrl);
	      return nextUrl;
	  }
	
}


