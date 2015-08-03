package com.imaginea.crawler.loader;

import static org.junit.Assert.assertEquals;

import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.imaginea.crawler.util.CrawlerUtil;

public class PageLoaderImplTest {

	Document document;
	String criteria;
	PageLoaderImpl pageLoader;
	String monthLink;
	Document monthDoc;
	String monthName;

	@Before
	public void setUp() throws Exception {
		document = DocumentLoader.getDocument(CrawlerUtil.getUrl());
		criteria = "Year 2004";
		pageLoader = new PageLoaderImpl();
		monthDoc = DocumentLoader.getDocument("http://mail-archives.apache.org/mod_mbox/maven-users/201501.mbox/date");
		monthLink = "http://mail-archives.apache.org/mod_mbox/maven-users/201501.mbox/date";
		monthName = "Jan 2015";
	}

	@After
	public void tearDown() throws Exception {
		document = null;
		criteria = null;
		pageLoader = null;
		monthDoc = null;
		monthName = null;
		monthLink = null;
	}

	/*
	 * @Test public final void testLoadLinks() { assertEquals(
	 * "Entered url is not valid", true, pageLoader.loadLinks(document,
	 * criteria)); }
	 */
	@Test
	public final void testLoadmonthlyMails() {
		assertEquals("Entered url is not valid", true, pageLoader.loadmonthlyMails(monthLink, monthName));
	}

	@Test
	public final void testProcessPage() {
		assertEquals("Entered url is not valid", true, pageLoader.processPage(monthDoc, monthName));
	}
}
