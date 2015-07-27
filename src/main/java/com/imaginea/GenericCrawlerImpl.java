package com.imaginea;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GenericCrawlerImpl implements Crawler{
	private Set <String>visitedURLSet = null;
	private File rootDir = null;
//	private String RootURL=null;
	private Link rootLink=null;
	public GenericCrawlerImpl(){
		this.visitedURLSet  = new HashSet<String>();
		this.rootDir = new File(Crawler.targetRootDIRPath);
		this.rootDir.mkdirs();
		this.rootLink = new Link();
	}
/*	public static void main(String[] args) {
		new GenericCrawlerImpl().executeCrawler();
	}
*/	
	public void executeCrawler(){
		//System.out.println("Hi" +CrawlerUtil.getDocument("http://mail-archives.apache.org/mod_mbox/maven-users/201507.mbox/ajax/%3CCAOe3-fZbQ4QeUNDkYyUUZ7bsoUFa_%2BEN9hxqDh%2BOzm6ybVecKw%40mail.gmail.com%3E").textNodes());
		rootLink.setParent(false);
		rootLink.setLink(Crawler.targetURL);
		rootLink.setParentLink("");
		rootLink.setLevel(0);
		addToVisitedURLset(rootLink.getLink());
		processLink(rootLink);
		
	}
	private void processLink(Link link){
		
		if(!isValidURL(link.getLink())) 
			return;
			
		Document doc = CrawlerUtil.getDocument(link.getLink());
		if(this.isDocEmail(doc)){
			saveMail(doc);
			addToVisitedURLset(link.getLink());
			return;	
		}
		
		Elements links = doc.select("a");//.attr("abs:href");
		for(Element linkElement : links){
			String URLstr =linkElement.attr("abs:href");
			if(!this.isValidURL(URLstr) && this.isVisitedURL(URLstr))
				continue;
			
			Document elementDoc = CrawlerUtil.getDocument(URLstr);
			if(this.isDocEmail(elementDoc)){
				saveMail(doc);
				addToVisitedURLset(URLstr);
				continue;
			}
			
		}
	}
	
	private void saveMail( Document doc){
		new MailLoaderImpl().saveMail(this.rootDir, doc.text());
	}
	
	private void processDocument(String strURL){
		Document doc = CrawlerUtil.getDocument(Crawler.targetURL);
		if(isDocEmail(doc)){
			
	//		this.addToVisitedURLset(URLstr);
		}
		Elements links = doc.select("a");//.attr("abs:href");
		for(Element linkElement : links){
			//System.out.println(e.attr("abs:href"));
			String URLstr =linkElement.attr("abs:href");
			if(!this.isValidURL(URLstr) || this.isVisitedURL(URLstr))
				continue;
			
			Document nestedDoc = CrawlerUtil.getDocument(URLstr);
			if(isDocEmail(nestedDoc)){
				saveMail(nestedDoc);
				this.addToVisitedURLset(URLstr);
				continue;
			}
			//isDocEmail(document)
		}
	}
	
	private File getRootDir(){
		return this.rootDir;
	}
	
	private boolean isValidURL(String URL){
		return URL.contains(rootLink.getLink());
	}
	
	private boolean isDocEmail(Document document){
		String text= document.text();
		return (hasTag(document,"Form") && hasTag(document,"subject") && hasTag(document,"mail"))||(text.contains("Form") && text.contains("subject") ) ? true: false;
	
	}
	private boolean hasTag(Document document, String tagName){
		return (document.getElementsByTag(tagName).size() > 0);
	}
	
	private boolean isVisitedURL(String URLstr){
		return visitedURLSet.contains(URLstr);
	}
	
	private boolean addToVisitedURLset(String URLstr){
		 return this.visitedURLSet.add(URLstr);
	}
	
}
