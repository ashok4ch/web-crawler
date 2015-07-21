package com.imaginea;

import java.io.File;
import java.util.List;

import com.imaginea.CrawlerImpl.MonthlyLinks;

/**
 * @author ashokh
 *
 */
public interface Crawler {
	
	public static final String targetURL ="http://mail-archives.apache.org/mod_mbox/maven-users/";
	public static final String targetRootDIRPath ="c:"+File.separator+"crawler"+File.separator+"crawler_downloadedMail";
	public void executeCrawler();
	public File getRootDir();
	public List<MonthlyLinks> getMonthlywiselinks();
}
