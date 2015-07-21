/**
 * 
 */
package com.imaginea;

import java.io.File;
import java.util.List;

import com.imaginea.CrawlerImpl.MonthlyLinks;

/**
 * @author ashokh
 *
 */
public interface MailLoader {
	public void processMailLoader(List<MonthlyLinks > mLinksList);
	public void processMailLoader(File targetDir, Mail mailObject);
}
