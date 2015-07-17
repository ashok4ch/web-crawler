package com.imaginea;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class CrawlerUtil {
	private static Logger _logger =Logger.getLogger(CrawlerUtil.class);
	public static Document getDocument(String absUrl){
		_logger.debug("getDocument() execution has started for rul : "+absUrl);
		Document doc=null;
		try { 
			doc = Jsoup.connect(absUrl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_logger.error(e.getMessage());
		}
		_logger.debug("getDocument() execution has ended for rul : "+absUrl);
		return doc;
	}
}

