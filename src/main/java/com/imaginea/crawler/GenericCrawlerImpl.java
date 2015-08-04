package com.imaginea.crawler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.imaginea.crawler.util.CrawlerUtil;

public class GenericCrawlerImpl implements Crawler {
	private static Logger logger = Logger.getLogger(GenericCrawlerImpl.class);
	private ConcurrentSkipListSet<String> urlsVisited = new ConcurrentSkipListSet<String>();
	private CopyOnWriteArrayList<String> urlsToVisit = new CopyOnWriteArrayList<String>();
	private ArrayBlockingQueue<String> mailUrlQueue = new ArrayBlockingQueue<String>(500);
	public static String rootUrl;

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
		logger.info("processURL has started with url: " + url);
		Thread cProducer = new Thread(new CrawlerProducer(urlsToVisit, mailUrlQueue, urlsVisited, url));
		cProducer.start();
		Thread cConsumer = new Thread(new CrawlerConsumer(mailUrlQueue, urlsToVisit));
		cConsumer.start();
	}

}
