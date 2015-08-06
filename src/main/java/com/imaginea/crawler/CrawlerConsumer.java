package com.imaginea.crawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.imaginea.crawler.dao.Mail;
import com.imaginea.crawler.dao.MailDao;
import com.imaginea.crawler.dao.MailDaoImpl;
import com.imaginea.crawler.loader.DocumentLoader;
import com.imaginea.crawler.util.CrawlerUtil;

public class CrawlerConsumer implements Runnable {
	private Logger logger = Logger.getLogger(CrawlerConsumer.class);
	private BlockingQueue<String> mailUrlQueue;
	private CopyOnWriteArrayList<String> urlsToVisit;

	public CrawlerConsumer(BlockingQueue<String> mailUrlQueue, CopyOnWriteArrayList<String> urlsToVisit) {
		this.mailUrlQueue = mailUrlQueue;
		this.urlsToVisit = urlsToVisit;
	}

	public void run() {
		logger.debug("CrawlerConsumer thread run() method has started.");
		/*int poolsize = Integer.parseInt(CrawlerUtil.PROPERTIES.getProperty("crawler.threadpoolsize"));
		ExecutorService executor = Executors.newFixedThreadPool(poolsize);*/
		do {
			/*Runnable runnableThread = new Runnable() {
				public void run() {
			*/		String url = null;
					try {
						url = this.mailUrlQueue.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (url == null)
						return;

					Document doc = DocumentLoader.getDocument(url);
					Mail mail = new Mail();
					mail.setMsgLink(url);
					mail.setDocument(doc);
					mail.setMsgName(doc.text().substring(0, 100));
					mail.setDirName("Generic_Crawler");

					MailDao mailDao = new MailDaoImpl();
					mailDao.saveMail(mail);
			/*	}
			};
			executor.execute(runnableThread);*/
		} while (!urlsToVisit.isEmpty());
		//executor.shutdown();
		logger.debug("CrawlerConsumer thread run() method has ended.");
	}

}
