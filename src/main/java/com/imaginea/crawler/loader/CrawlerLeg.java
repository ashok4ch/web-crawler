package com.imaginea.crawler.loader;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imaginea.crawler.GenericCrawlerImpl;
import com.imaginea.crawler.dao.Mail;
import com.imaginea.crawler.dao.MailDao;
import com.imaginea.crawler.dao.MailDaoImpl;

public class CrawlerLeg {

	private static final Logger logger = Logger.getLogger(CrawlerLeg.class);
	private List<String> links = new LinkedList<String>();
	private Document htmlDocument;
	private static String criteriaString = "";// we can change the criteria
												// string/ we can read from
												// property file .

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
	public boolean crawl(String url) {
		logger.debug("Crawl method has started");
		this.htmlDocument = DocumentLoader.getDocument(url);

		if (isMail()) {
			saveMail(this.htmlDocument);
		} else {
			loadLinks();
		}
		return true;

	}

	public boolean loadLinks(){
		Elements linksOnPage = htmlDocument.select("a[href]");
		logger.debug("Found (" + linksOnPage.size() + ") links");
		for (Element link : linksOnPage) {
			if (isValidLink(link.absUrl("href")))
				this.links.add(link.absUrl("href"));
		}
		
		return true;
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

	public void saveMail(Document doc) {
	/*	new MailLoaderImpl().saveMail(new File(GenericCrawlerImpl.rootDir,
				doc.text().substring(0, 100).replaceAll("[-+.^:,()?\\//*\"<>|=]", "") + ".txt"), doc.text());
	*/
		Mail mail=new Mail();
		mail.setDocument(doc);
		mail.setMsgName(doc.text().substring(0, 100));
		mail.setDirName("Generic_crawler");
		MailDao mailDao= new MailDaoImpl();
		mailDao.saveMail(mail);
		
	}

	public boolean hasTag(Document document, String tagName) {
		return (document.getElementsByTag(tagName).size() > 0);
	}

	public boolean isValidLink(String urlLink) {
		return (urlLink.contains(criteriaString) && urlLink.contains(GenericCrawlerImpl.rootUrl));
	}

}
