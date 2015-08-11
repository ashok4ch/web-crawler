package com.imaginea.crawler.loader;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imaginea.crawler.GenericCrawlerImpl;
import com.imaginea.crawler.dao.Mail;
import com.imaginea.crawler.dao.MailDao;
import com.imaginea.crawler.dao.MailDaoImpl;
import com.imaginea.crawler.util.CrawlerUtil;

public class CrawlerLeg {

	private static final Logger logger = Logger.getLogger(CrawlerLeg.class);
	private List<String> links = new LinkedList<String>();
	private ArrayBlockingQueue<String> mailUrlQueue;
	private Document htmlDocument;

	/**
	 * This performs all the work. It makes an HTTP request, checks the
	 * response, and then gathers up all the links on the page based on whether
	 * page is mail or not.Perform a mail validation after the successful page
	 * load.
	 * 
	 * @param url
	 *            - The URL to visit
	 * @return whether or not the crawl was successful
	 */
	public CrawlerLeg() {
		super();
	}

	public CrawlerLeg(ArrayBlockingQueue<String> mailUrlQueue) {
		super();
		this.mailUrlQueue = mailUrlQueue;
	}

	public List<String> crawl(String url) {
		logger.debug("Crawl method has started");
		this.htmlDocument = DocumentLoader.getDocument(url);

		if (isMail()) {
			try {
				mailUrlQueue.put(url);
			} catch (InterruptedException ie) {
				logger.fatal(ie);
			}
		} else {
			loadLinks();
		}
		return getLinks();

	}

	public void loadLinks() {

		if (htmlDocument == null)
			return;

		Elements linksOnPage = htmlDocument.select("a[href]");
		logger.debug("Found (" + linksOnPage.size() + ") links");
		for (Element link : linksOnPage) {
			if (isValidLink(link.absUrl("href")))
				this.links.add(link.absUrl("href"));
		}

	}

	public List<String> getLinks() {
		return this.links;
	}

	/**
	 * Performs a search on the body of on document(HTML/text) that is
	 * retrieved. This method should only be called after a successful crawl.
	 * 
	 * @return whether or not the current document was mail.
	 */
	private boolean isMail() {
		if (this.htmlDocument == null)
			return false;

		String text = this.htmlDocument.text();
		return (hasTag(this.htmlDocument, "form") && hasTag(this.htmlDocument, "subject")
				&& hasTag(this.htmlDocument, "mail")) || (text.contains("From") && text.contains("Subject")) ? true
						: false;
	}

	public boolean saveMail(Document doc) {
		Mail mail = new Mail();
		mail.setDocument(doc);
		int msgNameEndPoint = Integer.parseInt((String) CrawlerUtil.PROPERTIES.get("crawler.threadpoolsize"));
		mail.setMsgName(doc.text().substring(0, msgNameEndPoint));
		mail.setDirName("Generic_crawler");
		MailDao mailDao = new MailDaoImpl();
		mailDao.saveMail(mail);

		return true;
	}

	public boolean hasTag(Document document, String tagName) {
		return (document.getElementsByTag(tagName).size() > 0);
	}

	public boolean isValidLink(String urlLink) {
		return urlLink.contains(GenericCrawlerImpl.rootUrl);

	}

}
