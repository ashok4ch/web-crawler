package com.imaginea;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Hello world!
 *
 */
public class App 
{
	public static final  String TargetURL= "http://mail-archives.apache.org/mod_mbox/maven-users/";
	//public final static Logger _logger = Logger.getLogger(App.class);
	
	Map<String, Map<String, List<String>>> MonthlywiseURLS= new HashMap<String, Map<String, List<String>>>();
	
    public static void main( String[] args )
    { //_logger.debug("mAIN TEST");
		
//        System.out.println( "Hello World!" );
        //System.out.println("Enter Input Year : ");
       // Scanner scan = new Scanner(System.in);
        int inputValue = 2015;//scan.nextInt();
        if(inputValue < 2002 ||inputValue > 2015 )
        	System.out.println("The Entered input year is not valide");
        else{
        	//System.out.println("The Entered input year is valide");
        	loadAllMails(TargetURL);
        }
    }
    
    public static void loadAllMails(String uRLName){
    	
 try {
	 
	 
	 		String textValue ="Year "+2015;
			Document doc =Jsoup.connect(TargetURL).get();
			Elements elements= doc.getElementsByClass("year");
			boolean isValideYear = false;
			for(Element element : elements){
			
				System.out.println(element.getElementsByTag("thead").text().equals(textValue));
				if(element.getElementsByTag("thead").text().equals(textValue)){
					isValideYear= true;
					Elements tBody = element.getElementsByTag("tbody");
					//System.out.println("The Document"+tBody.select("tr").get(0).child(1));
					System.out.println("The Document"+tBody.select("tr").get(0));
					for(Element DateEle : element.getElementsByClass("date")){
						System.out.println(DateEle.text());
						
					}
					//System.out.println("The Document"+element.getElementsByClass("date"));
					//MonthlywiseURLS.put()
					break;
					
				}
			
			}
			
			
			//System.out.println("The Document"+doc);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
}
