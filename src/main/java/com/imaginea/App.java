package com.imaginea;

import java.io.IOException;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Hello world!
 *
 */
public class App 
{
	public static final  String TargetURL= "http://mail-archives.apache.org/mod_mbox/maven-users/";
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );
        System.out.println("Enter Input Year : ");
        Scanner scan = new Scanner(System.in);
        int inputValue = scan.nextInt();
        if(inputValue < 2002 ||inputValue > 2015 )
        	System.out.println("The Entered input year is not valide");
        else{
        	//System.out.println("The Entered input year is valide");
        	//loadAllMails(TargetURL);
        }
    }
    
    public static void loadAllMails(String uRLName){
    	
 try {
			Document doc =Jsoup.connect(TargetURL).get();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
}
