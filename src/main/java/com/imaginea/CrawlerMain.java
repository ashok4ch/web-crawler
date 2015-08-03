package com.imaginea;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.imaginea.crawler.Crawler;
import com.imaginea.crawler.CrawlerImpl;
import com.imaginea.crawler.GenericCrawlerImpl;

public class CrawlerMain {
	public final static Logger logger = Logger.getLogger(CrawlerMain.class);

	public static void main(String[] args) {
		stratCrawler();
		// File file= CrawlerUtil.getRootDir("ashok");
		// System.out.println("file is "+ file.getAbsolutePath());
	}

	public static void stratCrawler() {
		logger.debug("Crawler manin method execution start");
		Scanner scan = new Scanner(System.in);
		String crawlerType;
		do {
			System.out.println("Enter Crawler type Normal(N/n) and Generic(G/g): ");
			crawlerType = scan.next();
		} while (!(crawlerType != null && ("n".equalsIgnoreCase(crawlerType) || "g".equalsIgnoreCase(crawlerType))));
		String selType = ("n".equalsIgnoreCase(crawlerType)) ? "Normal Crawler" : "Generic Craler";
		System.out.println("Crawler type is : " + selType);

		if ("n".equalsIgnoreCase(crawlerType)) {
			System.out.println("Enter Input Year In YYYY formate: ");
			int inputValue = scan.nextInt();
			Crawler crawler = new CrawlerImpl(inputValue);
			crawler.executeCrawler();
		}
		if ("g".equalsIgnoreCase(crawlerType)) {
			System.out.println(
					"Do you want run with default url : http://mail-archives.apache.org/mod_mbox/maven-users (Y/N) ?");
			if ("n".equalsIgnoreCase(scan.next())) {
				System.out.println("Please enter url  ?");
				Crawler crawler = new GenericCrawlerImpl(scan.next());
				crawler.executeCrawler();
			} else {
				Crawler crawler = new GenericCrawlerImpl();
				crawler.executeCrawler();
			}

		}
		System.out.println("The mail(s) down loaded in to following folder " + Crawler.TARGET_ROOT_DIR_PATH);
		// long startTime= System.currentTimeMillis();
		scan.close();
	}
}