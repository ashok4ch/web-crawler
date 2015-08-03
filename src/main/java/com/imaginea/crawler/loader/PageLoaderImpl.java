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

/**
 * @author ashokh
 * 
 */
public class PageLoaderImpl implements PageLoader {
	private static Logger logger = Logger.getLogger(PageLoaderImpl.class);

	public boolean loadLinks(Document document,String Criteria) {
		logger.debug("loadLinks execution start");
		Elements elements = document.getElementsByClass("year");
		for (Element element : elements) {
			if (element.getElementsByTag("thead").text().equals(Criteria)) {
				Elements trs = element.getElementsByTag("tbody").first().getElementsByTag("tr");
				for (Element trElement : trs) {
					String monthName = trElement.getElementsByClass("date").first().text();
					String monthLink = trElement.select("a").first().absUrl("href");
					// Code to load the all mails links of respective month
					logger.debug("Links of " + monthName + " month load start ");
					if (monthLink.isEmpty())
						continue;
					loadmonthlyMails(monthLink, Criteria+File.separator+monthName);
				}
				break;
			}
		}
		logger.debug("loadLinks execution has end");
		return true;
	}

	public void loadmonthlyMails(String monthLink, String monthName) {
		logger.debug("loadmonthyMails() method execution started for the month" + monthName);
		/*File monthDir = new File(this.getRootDir(), monthName);
		monthDir.mkdirs();*/
		Document monthDoc = DocumentLoader.getDocument(monthLink);
		int noOfPage = (monthDoc.getElementsByClass("pages").select("a").size() - 1);

		// if the month has only one page below if block execute.
		if (noOfPage <= 0) {
			ProcessPage(monthDoc, monthName);
			return;
		}
		// To handle the multiple pages of a month mails
		for (int j = 0; j <= noOfPage; j++) {
			StringBuilder PageLink = new StringBuilder(monthLink).append("?").append(j);
			Document pageDoc = DocumentLoader.getDocument(PageLink.toString());
			ProcessPage(pageDoc, monthName);
		}
		logger.debug("loadmonthyMails() method execution has ended for the month" + monthName);
	}

	private void ProcessPage(Document pageDoc, String MonthName) {
		ExecutorService executor = Executors.newFixedThreadPool(20);
		Elements monthlinks = pageDoc.getElementById("msglist").getElementsByTag("tbody").first()
				.getElementsByTag("tr");
		for (int i = 0; i < monthlinks.size(); i++) {
			String msgfileName = monthlinks.get(i).text(); // test for msg naem
			if (msgfileName.length() > 150)
				msgfileName = msgfileName.substring(0, 150) + "...";
			String msgLink = monthlinks.get(i).select("a").attr("abs:href");
			if (msgLink.isEmpty()) {
				continue;
			}
			Mail mail = new Mail();
			mail.setMsgName(msgfileName);
			msgLink = msgLink.replaceFirst(".mbox/%", ".mbox/raw/%");
			mail.setMsgLink(msgLink);
			//mail.setFileDirectory(dir);
			mail.setDirName(MonthName);
			mail.setDocument(DocumentLoader.getDocument(msgLink));
			MailLoadThread mailLoader = new MailLoadThread(mail);
			executor.execute(mailLoader);
		}
		executor.shutdown();
	}

	}
