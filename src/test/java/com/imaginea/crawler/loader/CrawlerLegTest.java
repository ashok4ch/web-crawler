package com.imaginea.crawler.loader;

import static org.junit.Assert.assertEquals;

import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CrawlerLegTest {
	private CrawlerLeg cLeg;
	private String url;
	Document doc;

	@Before
	public void setUp() throws Exception {
		cLeg = new CrawlerLeg();
		// url = CrawlerUtil.getUrl();
		url = "http://mail-archives.apache.org/mod_mbox/maven-users/201507.mbox/raw/%3CCAOe3-fZbQ4QeUNDkYyUUZ7bsoUFa_%2BEN9hxqDh%2BOzm6ybVecKw%40mail.gmail.com%3E";
		doc = DocumentLoader.getDocument(url);
	}

	@After
	public void tearDown() throws Exception {
		cLeg = null;
		url = null;
	}

	@Test
	public final void testLoadLinks() {
		assertEquals("The page has not links ", true, cLeg.getLinks());
	}

	@Test
	public final void testSaveMail() {
		assertEquals("the passed document is not saved ", true, cLeg.saveMail(doc));
	}

	@Test
	public final void testHasTag() {
		assertEquals("this tag doesn't exixt in the given doc", true, cLeg.hasTag(doc, "mail"));
	}

}
