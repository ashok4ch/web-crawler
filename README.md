# web-crawler
simple crawler to crawl and download all mails for specified year from url:http://mail-archives.apache.org/mod_mbox/maven-users/.
The project must have a build system and the build system should create a jar with a defined application entry point (java -jar crawler.jar [opts] should work, assuming crawler.jar was the jar created by the build system)

Tools:

	 Maven
	 JDK
	 Jsoup
	 JUnit
	 Eclipsese IDE
	 
Iteration:

Time: 1 week Deliverable: working crawler which can download all mails(Coding: 3 days, Testing:2day)
Time: 1 week Deliverable: Robust crawler which can survive internet connection loss and can resume from last run
Time: 1 week Deliverable: Performance and memory optimization for the crawler

Goals:

Write good code which follows best practices (naming conventions, formatting, optimal use)
Write unit tests for relevant sections
Write production quality code (options to set the log level at runtime, decent logging which can be used to troubleshoot)

Application input and output overview:

	Input		: Year in the format of 'YYYY' 
	Validation	: Application should validate input year, like does it valid year(As per url:http://mail-archives.apache.org/mod_mbox/maven-users/ input year must be between 2002 to 2015) and valid format(YYYY).  
	Output		: Once all the mails loaded application will display  mails copied directory path. 

