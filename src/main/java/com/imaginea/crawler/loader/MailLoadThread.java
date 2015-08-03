package com.imaginea.crawler.loader;

import com.imaginea.crawler.dao.Mail;
import com.imaginea.crawler.dao.MailDao;
import com.imaginea.crawler.dao.MailDaoImpl;

public class MailLoadThread implements Runnable {
	private Mail mailObject = null;

	public MailLoadThread(Mail mailObject) {
		this.mailObject = mailObject;
	}

	@Override
	public void run() {

		/*MailLoader mailloader = new MailLoaderImpl();
		mailloader.processMailLoader(this.mailObject);
*/
		MailDao mailDao= new MailDaoImpl();
		mailDao.saveMail(this.mailObject);
	}

}
