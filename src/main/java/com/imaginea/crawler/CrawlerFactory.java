package com.imaginea.crawler;

public class CrawlerFactory {
	public enum CrawlerType {
		NORMAL, GENERIC
	};

	public static Crawler getCrawler(CrawlerType type) {

		Crawler crawler = null;
		if (type.equals(CrawlerType.NORMAL))
			crawler = new CrawlerImpl();

		if (type.equals(CrawlerType.GENERIC))
			crawler = new GenericCrawlerImpl();

		return crawler;
	}

	public static Crawler getCrawler(String Criteria, CrawlerType type) {

		Crawler crawler = null;
		if (type.equals(CrawlerType.NORMAL)) {
			crawler = new CrawlerImpl();
			crawler.setInputCriteria(Criteria);
		}
		if (type.equals(CrawlerType.GENERIC))
			crawler = new GenericCrawlerImpl(Criteria);

		return crawler;
	}

}
