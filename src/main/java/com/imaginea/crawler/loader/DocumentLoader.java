package com.imaginea.crawler.loader;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocumentLoader {
	
	private static Logger logger= Logger.getLogger(DocumentLoader.class);
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	public static Document getDocument(String url){
		Document doc=null;
		logger.debug("getDocument() execution has started for rul : " + url);
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			doc = connection.get();
			// 200 is the HTTP OK status code indicating that request was
			// successfully received, understood, and accepted .
			if (connection.response().statusCode() == 200) {
				logger.debug("\n**Visiting** Received web page at " + url);
			}

		} catch (HttpStatusException hse) {
			logger.fatal("The given URL:" + url + " is not ccorrect. Please correct it (" + hse.getMessage() + ")");

		} catch (UnknownHostException ukhe) {
			logger.fatal(
					"Crawler can not able to connect inter net/ system in Offline please check your internate connection.");
		} catch (IOException ioe) {
			logger.error("HTTP request not successful, more descritpion :" + ioe.getMessage());
		}
		logger.debug("getDocument() execution has ended for rul : " + url);
		return doc;

	}

}
