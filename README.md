# web-crawler
simple crawler to crawl and download all mails for specified year from url:http://mail-archives.apache.org/mod_mbox/maven-users/.
The project must have a build system and the build system should create a jar with a defined application entry point (java -jar crawler-jar-with-dependencies.jar [opts] should work, assuming crawler.jar was the jar created by the build system)

Tools:

	 Maven
	 JDK
	 Jsoup
	 JUnit
	 Eclipsese IDE
	 
Iteration:

Time: 1 week Deliverable: working crawler which can download all mails(Coding: 3 days, Testing:2day).

Time: 2 week Deliverable: Generic crawler which can download all mails from specified url if it has any mails.

Application input and output overview:

For Normal crawler

	Input		: Year in the format of 'YYYY' 
	Validation	: Application should validate input year like does it valid year(As per url:http://mail-archives.apache.org/mod_mbox/maven-users/ input year must be between 2002 to 2015) and valid format(YYYY).  
	Output		: Once all the mails loaded application will display  mails copied directory path. 
	

For Generic crawler

	Input		: Year in the format of 'YYYY' 
	Validation	: Application should validate input url and it child urls to avoid losing the root url context.  
	Output		: Once all the mails loaded application will display  mails copied directory path. 

	
	
Note:  Separate design document attached in same project which has class and flow chart diagrams.
