package com.imaginea;

import java.io.IOException;

/**
 * @author ashokh
 *
 */
public interface CrawlerToLoadMails {
	public static final String targetURL ="http://mail-archives.apache.org/mod_mbox/maven-users/";  
	public CrawlerToLoadMails getCrawler(int inputYear);
	public void executeCrawler();
	public boolean createDirs(String dirPath);
	public boolean isInputYearValid(int inputYear);
	public void loadLinks();
	public void ProcessLinks();
	public void loadMail(String mailLink);
    public void saveMail();

}
