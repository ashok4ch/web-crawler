package com.imaginea.crawler;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.imaginea.CrawlerMain;
import com.imaginea.crawler.loader.DocumentLoader;
import com.imaginea.crawler.loader.PageLoader;
import com.imaginea.crawler.loader.PageLoaderImpl;
import com.imaginea.crawler.util.CrawlerUtil;

/*
 *CrawlerImp does loads all the mails of specified year 
 */
public class CrawlerImpl implements Crawler {
	public final static Logger logger = Logger.getLogger(CrawlerMain.class);
	private Document document;
	private int inputYear;
	private String inputYearText;

	public int getInputYear() {
		return inputYear;
	}

	public String getInputYearText() {
		return inputYearText;
	}

	public CrawlerImpl() {
		// this.inputYear = inputYear;
		// this.inputYearText = "Year " + inputYear;
		this.document = DocumentLoader.getDocument(CrawlerUtil.getUrl());
	}

	public void setInputCriteria(String Criteria) {
		this.inputYear = Integer.parseInt(Criteria);
		this.inputYearText = "Year " + Criteria;
	}

	public CrawlerImpl(int inputYear) {
		this.inputYear = inputYear;
		this.inputYearText = "Year " + inputYear;
		this.document = DocumentLoader.getDocument(CrawlerUtil.getUrl());
	}

	public boolean isYearValid(String inputYear) {
		logger.debug("isInputYearValid execution start");
		return this.document.text().contains(inputYear);

	}

	public void executeCrawler() {
		logger.debug("executeCrawler execution start");
		if (this.isYearValid(this.inputYearText)) {
			PageLoader pageLoader = new PageLoaderImpl();
			pageLoader.loadLinks(this.document, inputYear + "");
		} else {
			logger.fatal("Entered Input Year" + inputYear + " is not valid/has no emails Please enter valid :");
			return;
		}
		logger.debug("executeCrawler execution end");
	}
}
