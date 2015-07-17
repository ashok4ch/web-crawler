/**
 * 
 */
package com.imaginea;

import java.util.List;
import java.util.Map;

import com.imaginea.CrawlerImp.MonthlyLinks;

/**
 * @author ashokh
 *
 */
public interface MailLoader {
	public void processMailLoader(Crawler ctlm);
	public String processMailLoader(String linkURL);
}
