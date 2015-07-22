package com.imaginea;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imaginea.CrawlerImpl.MonthlyLinks;

public class GenericCrawlerImpl implements Crawler{
	private Set visitedURLSet = null;
	private List URLsList = new ArrayList<>();
	public GenericCrawlerImpl(){
		visitedURLSet  = new HashSet<String>();
		URLsList = new ArrayList<String>();
	}
	public static void main(String[] args) {
		new GenericCrawlerImpl().executeCrawler();
	}
	
	public void executeCrawler(){
		
		System.out.println("Hi" +CrawlerUtil.getDocument("http://mail-archives.apache.org/mod_mbox/maven-users/201507.mbox/ajax/%3CCAOe3-fZbQ4QeUNDkYyUUZ7bsoUFa_%2BEN9hxqDh%2BOzm6ybVecKw%40mail.gmail.com%3E").textNodes());
		
		Document doc = CrawlerUtil.getDocument(Crawler.targetURL);
		if(this.isDocEmail(doc)){
			
		return;	
		}
		
		Elements links = doc.select("a");//.attr("abs:href");
		for(Element linkElement : links){
			//System.out.println(e.attr("abs:href"));
			String URLstr =linkElement.attr("abs:href");
			if(!this.isValidURL(URLstr) && this.isVisitedURL(URLstr))
				continue;
			if(this.isDocEmail(doc)){
				loadMail(new File("crawler/").mkdir(),doc);
			}
			this.addToVisitedURLset(URLstr);
		}
	}
	
	private void loadMail(File rootDir, Document doc){
		
	}
	private void processDocument(String strURL){
	
		Document doc = CrawlerUtil.getDocument(Crawler.targetURL);
		
		Elements links = doc.select("a");//.attr("abs:href");
		for(Element linkElement : links){
			//System.out.println(e.attr("abs:href"));
			String URLstr =linkElement.attr("abs:href");
			if(!this.isValidURL(URLstr) && this.isVisitedURL(URLstr))
				continue;
			
			this.addToVisitedURLset(URLstr);
			//isDocEmail(document)
		}
	}
	
	
	
	
	private boolean isValidURL(String URL){
		return URL.contains(Crawler.targetURL);
	}
	
	private boolean isDocEmail(Document document){
		String text= document.text();
		return (hasTag(document,"Form") && hasTag(document,"subject") && hasTag(document,"mail"))||(text.contains("Form") && text.contains("subject") ) ? true: false;
	
	}
	private boolean hasTag(Document document, String tagName){
		
		return (document.getElementsByTag("Form").size() > 0);
	}
	
	private boolean isVisitedURL(String URLstr){
		return visitedURLSet.contains(URLstr);
	}
	
	private void addToVisitedURLset(String URLstr){
		visitedURLSet.add(URLstr);
	}
	
}
