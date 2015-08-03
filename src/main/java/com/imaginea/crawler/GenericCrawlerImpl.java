package com.imaginea.crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.imaginea.crawler.loader.CrawlerLeg;
import com.imaginea.crawler.util.CrawlerUtil;

public class GenericCrawlerImpl implements Crawler {
	private static Logger logger = Logger.getLogger(GenericCrawlerImpl.class);
	private Set<String> urlsVisited = new HashSet<String>();
	private List<String> urlsToVisit = new LinkedList<String>();
	public static String rootUrl;
	private static final String EXIT = "exit";

	public GenericCrawlerImpl() {
		rootUrl = CrawlerUtil.getUrl();// Crawler.TARGET_URL;
	}

	public GenericCrawlerImpl(String url) {
		rootUrl = url;
	}

	public void executeCrawler() {

		processURL(rootUrl);
	}

	private void processURL(String url) {

		while (true) {
			String currentUrl;
			CrawlerLeg leg = new CrawlerLeg();
			if (this.urlsToVisit.isEmpty()) {
				currentUrl = url;
				this.urlsVisited.add(url);
			} else {
				String nextUrl = this.nextUrl();
				if (EXIT.equals(nextUrl)) {
					logger.info("Urls to visit list is empty so application is sutdowning.");
					break;
				}
				currentUrl = nextUrl;
			}
			leg.crawl(currentUrl); // Lots of stuff(loading links and validating
									// document whether is it email if yes
									// saving it else loading all it's links )
									// happening here. Look at the crawl method
									// in
			if (!leg.getLinks().isEmpty()) {
				this.urlsToVisit.addAll(leg.getLinks());
				logger.info(" this.urlsToVisit size is : " + this.urlsToVisit.size() + "::::" + leg.getLinks().size());
			}
			if (this.urlsToVisit.isEmpty())
				break;
		}
		logger.debug("\n**Done** Visited " + this.urlsVisited.size() + " web page(s)");
	}

	private String nextUrl() {
		String nextUrl;
		do {
			if (this.urlsToVisit.isEmpty()) {
				return EXIT;
			}
			nextUrl = this.urlsToVisit.remove(0);

		} while (this.urlsVisited.contains(nextUrl));
		this.urlsVisited.add(nextUrl);
		return nextUrl;
	}

}
