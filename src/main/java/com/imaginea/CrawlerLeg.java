package com.imaginea;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerLeg {

	private static final Logger logger= Logger.getLogger(CrawlerLeg.class);
	
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT ="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    private Document htmlDocument;
    private static  String criteriaString="2014";// we can change the criteria string/ we can read from property file .


    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page based on whether page is mail or not.Perform a mail validation after the successful page load.
     * 
     * @param url
     *            - The URL to visit
     * @return whether or not the crawl was successful
     */
    public boolean crawl(String url)
    {
    	logger.debug("Crawl method has started");
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
                                                          // indicating that everything is great.
            {
               logger.debug("\n**Visiting** Received web page at " + url);
            }
            
            if(!checkDoesItMail()){
	            Elements linksOnPage = htmlDocument.select("a[href]");
	            logger.debug("Found (" + linksOnPage.size() + ") links");
	            for(Element link : linksOnPage)
	            {
	            	if(isValidLink(link.absUrl("href")))//link.absUrl("href").contains(Crawler.targetURL))
	            		this.links.add(link.absUrl("href"));
	            }
        	
            }
            
            return true;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return false;
        }
    }


    /**
     * Performs a search on the body of on document(HTML/text) that is retrieved. This method should
     * only be called after a successful crawl.
     * 
     * @return whether or not the current document was mail.
     */
    public boolean checkDoesItMail()
    {

    	if(isDocEmail()){
    		saveMail(this.htmlDocument);
    		return true;
    	}
    	return false;
    }

    public List<String> getLinks()
    {
        return this.links;
    }
    
    
    private boolean isDocEmail(){
    	if(this.htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
    	
		String text= this.htmlDocument.text();
		return (hasTag(this.htmlDocument,"form") && hasTag(this.htmlDocument,"subject") && hasTag(this.htmlDocument,"mail"))||(text.contains("From") && text.contains("Subject") ) ? true: false;
	
	}
    
    private void saveMail( Document doc){
		new MailLoaderImpl().saveMail(new File(GenericCrawlerImpl.rootDir, doc.text().substring(0, 100).replaceAll("[-+.^:,()?\\//*\"<>|=]","")+".txt"), doc.text());
	}
    
    private boolean hasTag(Document document, String tagName){
		return (document.getElementsByTag(tagName).size() > 0);
	}
    
    private boolean isValidLink(String urlLink){
    	return (urlLink.contains(criteriaString) && urlLink.contains(Crawler.targetURL));
    }

}
