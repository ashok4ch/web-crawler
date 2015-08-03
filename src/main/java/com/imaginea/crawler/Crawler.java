package com.imaginea.crawler;

import java.io.File;

/**
 * @author ashokh
 *
 */
public interface Crawler {
	public static final String TARGET_URL = "http://mail-archives.apache.org/mod_mbox/maven-users";
	public static final String TARGET_ROOT_DIR_PATH = "c:" + File.separator + "Crawler" + File.separator
			+ "DownloadedMail";

	public void executeCrawler();
}
