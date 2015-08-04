package com.imaginea.crawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.imaginea.crawler.dao.Mail;
import com.imaginea.crawler.dao.MailDao;
import com.imaginea.crawler.dao.MailDaoImpl;
import com.imaginea.crawler.loader.DocumentLoader;

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
		do {
			String url = null;
			try {
				url = mailUrlQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (url == null) {
				return;
			}
			Document doc = DocumentLoader.getDocument(url);
			Mail mail = new Mail();
			mail.setMsgLink(url);
			mail.setDocument(doc);
			mail.setMsgName(doc.text().substring(0, 150));
			mail.setDirName("Generic_Crawler");

			MailDao mailDao = new MailDaoImpl();
			mailDao.saveMail(mail);

		} while (!urlsToVisit.isEmpty());
		logger.debug("CrawlerConsumer thread run() method has ended.");
	}

}
