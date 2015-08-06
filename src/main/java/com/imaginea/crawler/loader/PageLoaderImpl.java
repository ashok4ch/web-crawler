/**
 * 
 */
package com.imaginea.crawler.loader;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imaginea.crawler.dao.Mail;
import com.imaginea.crawler.util.CrawlerUtil;

/**
 * @author ashokh
 * 
 */
public class PageLoaderImpl implements PageLoader {
	private static Logger logger = Logger.getLogger(PageLoaderImpl.class);

	public boolean loadLinks(Document document, String criteria) {
		logger.debug("loadLinks execution start");
		Elements elements = document.select("a[href]");
		for (Element link : elements) {
			String url = link.absUrl("href");
			if (url.contains(criteria) && url.contains("thread")) {
				int startpoint = url.indexOf(criteria);
				String monthName = url.substring(startpoint, startpoint + 6);
				loadmonthlyMails(url, criteria + File.separator + monthName);
			}
		}
		logger.debug("loadLinks execution has end");
		return true;
	}

	public boolean loadmonthlyMails(String monthLink, String monthName) {
		logger.debug("loadmonthyMails() method execution started for the month" + monthName);
		Document monthDoc = DocumentLoader.getDocument(monthLink);
		int noOfPage = (monthDoc.getElementsByClass("pages").select("a").size() - 1);
		int j = 0;
		do {
			StringBuilder PageLink = new StringBuilder(monthLink).append("?").append(j);
			Document pageDoc = DocumentLoader.getDocument(PageLink.toString());
			processPage(pageDoc, monthName);
			j++;
		} while (j <= noOfPage);

		logger.debug("loadmonthyMails() method execution has ended for the month" + monthName);
		return true;
	}

	public boolean processPage(Document pageDoc, String MonthName) {
		int poolsize = Integer.parseInt((String) CrawlerUtil.PROPERTIES.get("crawler.threadpoolsize"));
		ExecutorService executor = Executors.newFixedThreadPool(poolsize);
		Elements monthlinks = pageDoc.getElementById("msglist").getElementsByTag("tbody").first()
				.getElementsByTag("tr");
		for (int i = 0; i < monthlinks.size(); i++) {
			String msgfileName = monthlinks.get(i).text(); // test for msg naem
			if (msgfileName.length() > 150)
				msgfileName = msgfileName.substring(0, 100);
			String msgLink = monthlinks.get(i).select("a").attr("abs:href");
			if (msgLink.isEmpty()) {
				continue;
			}
			Mail mail = new Mail();
			mail.setMsgName(msgfileName);
			msgLink = msgLink.replaceFirst(".mbox/%", ".mbox/raw/%");
			mail.setMsgLink(msgLink);
			mail.setDirName(MonthName);
			mail.setDocument(DocumentLoader.getDocument(msgLink));
			MailLoadThread mailLoader = new MailLoadThread(mail);
			executor.execute(mailLoader);
		}
		executor.shutdown();
		return true;
	}

}
