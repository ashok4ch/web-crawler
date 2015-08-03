package com.imaginea.crawler.loader;

import org.jsoup.nodes.Document;

public interface PageLoader {

	public boolean loadLinks(Document document, String Criteria);
}
