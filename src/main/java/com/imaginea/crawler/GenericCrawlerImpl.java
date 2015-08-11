package com.imaginea.crawler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.imaginea.crawler.loader.CrawlerProducer;
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

	public void setInputCriteria(String inputCriteria) {
		// this.rootUrl=inputCriteria;
	}

	private void processURL(String url) {
		logger.info("processURL has started with url: " + url);
		int poolsize = Integer.parseInt((String) CrawlerUtil.PROPERTIES.get("crawler.threadpoolsize"));
		ExecutorService executor = Executors.newFixedThreadPool(poolsize);

		for (int i = 0; i <= poolsize; i++) {
			if (i % 2 == 0) {
				Thread cProducer = new Thread(new CrawlerProducer(urlsToVisit, mailUrlQueue, urlsVisited, url),
						"Producer Thread" + i);
				executor.execute(cProducer);

			} else {

				Thread cConsumer = new Thread(new CrawlerConsumer(mailUrlQueue, urlsToVisit), "consumerThread : " + i);
				executor.execute(cConsumer);
			}
		}

		executor.shutdown();
	}

}
