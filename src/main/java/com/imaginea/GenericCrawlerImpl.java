package com.imaginea;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class GenericCrawlerImpl implements Crawler{
	private static final Logger logger= Logger.getLogger(GenericCrawlerImpl.class);
	private static final int MAX_PAGES_TO_SEARCH = 5000;
	private Set<String> urlsVisited = new HashSet<String>();
	private List<String> urlsToVisit = new LinkedList<String>();
	public static File rootDir = new File(Crawler.targetRootDIRPath);
	static{
			rootDir.mkdirs();
	}
	
	public void executeCrawler(){
	
		processURL(Crawler.targetURL);
	}
	private void processURL(String url){

	      while(this.urlsVisited.size() < MAX_PAGES_TO_SEARCH)
	      {
	          String currentUrl;
	          CrawlerLeg leg = new CrawlerLeg();
	          if(this.urlsToVisit.isEmpty()){
	              currentUrl = url;
	              this.urlsVisited.add(url);
	          }else{
	              currentUrl = this.nextUrl();
	          }
	          leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
	          boolean success = leg.checkingForMail();
	          if(success){
	        	  logger.info("the following url page was mail URL: "+currentUrl); 
	          }
	          
	          if(!leg.getLinks().isEmpty())
	        	  this.urlsToVisit.addAll(leg.getLinks());
	      }
	      logger.debug("\n**Done** Visited " + this.urlsVisited.size() + " web page(s)");
	}
	
	private String nextUrl()
	  {
	      String nextUrl;
	      do
	      {
	          nextUrl = this.urlsToVisit.remove(0);
	      } while(this.urlsVisited.contains(nextUrl));
	      this.urlsVisited.add(nextUrl);
	      return nextUrl;
	  }
	
}


