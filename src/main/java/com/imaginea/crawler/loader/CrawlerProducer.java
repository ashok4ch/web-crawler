package com.imaginea.crawler.loader;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

public class CrawlerProducer implements Runnable {
	private static Logger logger = Logger.getLogger(CrawlerProducer.class);
	private static final String EXIT = "exit";
	private CopyOnWriteArrayList<String> urlsToVisit;
	private ArrayBlockingQueue<String> mailUrlQueue;
	private ConcurrentSkipListSet<String> urlsVisited;
	private String url;

	public CrawlerProducer(CopyOnWriteArrayList<String> urlsToVisit, ArrayBlockingQueue<String> mailUrlQueue,
			ConcurrentSkipListSet<String> urlsVisited, String url) {
		super();
		this.urlsToVisit = urlsToVisit;
		this.mailUrlQueue = mailUrlQueue;
		this.urlsVisited = urlsVisited;
		this.url = url;
	}

	@Override
	public void run() {
		logger.debug("Thread CrawlerProducer run() method has started");
		while (true) {
			String currentUrl;
			if (this.urlsToVisit.isEmpty()) {
				currentUrl = this.url;
				this.urlsVisited.add(currentUrl);
			} else {
				String nextUrl = this.nextUrl();
				if (EXIT.equals(nextUrl)) {
					logger.info("Urls to visit list is empty so application is sutdowning.");
					break;
				}
				currentUrl = nextUrl;
			}
			CrawlerLeg leg = new CrawlerLeg(mailUrlQueue);
			List<String> linksList = leg.crawl(currentUrl);
			if (!linksList.isEmpty()) {
				this.urlsToVisit.addAll(linksList);
				logger.info(" this.urlsToVisit size is : " + this.urlsToVisit.size() + "::::" + leg.getLinks().size());
			}

			if (this.urlsToVisit.isEmpty())
				break;
		}
		logger.debug("Thread CrawlerProducer run() method has ended");
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
