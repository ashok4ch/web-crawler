package com.imaginea;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.imaginea.crawler.Crawler;
import com.imaginea.crawler.CrawlerFactory;
import com.imaginea.crawler.CrawlerFactory.CrawlerType;
import com.imaginea.crawler.util.CrawlerUtil;

public class CrawlerMain {
	public final static Logger logger = Logger.getLogger(CrawlerMain.class);

	public static void main(String[] args) {
		stratCrawler();
	}

	public static void stratCrawler() {
		logger.debug("Crawler manin method execution start");
		Scanner scan = new Scanner(System.in);
		String crawlerType;
		do {
			System.out.println("Enter Crawler type Normal(N/n) and Generic(G/g): ");
			crawlerType = scan.next();
		} while (!(crawlerType != null && ("n".equalsIgnoreCase(crawlerType) || "g".equalsIgnoreCase(crawlerType))));
		CrawlerType crwType = ("n".equalsIgnoreCase(crawlerType)) ? CrawlerType.NORMAL : CrawlerType.GENERIC;
		Crawler crawler;
		if ("n".equalsIgnoreCase(crawlerType)) {
			System.out.println("Enter Input Year In YYYY formate: ");
			String inputValue = scan.next();
			crawler = CrawlerFactory.getCrawler(inputValue, crwType);
		} else {
			System.out.println(
					"Do you want run with default url : http://mail-archives.apache.org/mod_mbox/maven-users (Y/N) ?");
			if ("n".equalsIgnoreCase(scan.next())) {
				System.out.println("Please enter url  ?");
				crawler = CrawlerFactory.getCrawler(scan.next(), crwType);
			} else {
				crawler = CrawlerFactory.getCrawler(crwType);
			}
		}
		crawler.executeCrawler();
		logger.info("The mail(s) down loaded in to following folder " + CrawlerUtil.getRootDirPath()); // long
		scan.close();
	}

}