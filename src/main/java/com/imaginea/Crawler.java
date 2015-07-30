package com.imaginea;

import java.io.File;

/**
 * @author ashokh
 *
 */
public interface Crawler {
	public static final String targetURL ="http://mail-archives.apache.org/mod_mbox/maven-users";
	public static final String targetRootDIRPath ="c:"+File.separator+"Crawler"+File.separator+"DownloadedMail";
	public void executeCrawler();
}
