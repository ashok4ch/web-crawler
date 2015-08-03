package com.imaginea.crawler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CrawlerImplTest {

	private CrawlerImpl crw;

	@Before
	public void setUp() throws Exception {
		crw = new CrawlerImpl(2011);

	}

	@After
	public void tearDown() throws Exception {
		crw = null;
	}

	@Test
	public final void testICrawlerImplsYearValid() {
		assertEquals("2011 is valid input year", true, crw.isYearValid(crw.getInputYearText()));

	}

}
