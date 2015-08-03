package com.imaginea.crawler.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.imaginea.crawler.util.CrawlerUtil;

public class MailDaoImpl implements MailDao {
	public static Logger logger = Logger.getLogger(MailDaoImpl.class);

	@Override
	public boolean saveMail(Mail mail) {
		boolean result = true;
		if (mail == null)
			result = false;

		logger.debug("processMailLoader for URL : " + mail.getMsgLink());
		Document doc = mail.getDocument();
		String msg = doc.text();
		File msgFile = new File(CrawlerUtil.getRootDir(mail.getDirName()),
				mail.getMsgName().replaceAll("[-+.^:,()?\\//*\"<>|=]", "") + ".txt");

		FileWriter fileWriter = null;
		BufferedWriter bWriter = null;
		try {
			fileWriter = new FileWriter(msgFile);
			bWriter = new BufferedWriter(fileWriter);
			bWriter.write(msg);
			bWriter.close();
		} catch (IOException ioe) {
			result = false;
			ioe.printStackTrace();
			logger.error(ioe.getMessage());
		}

		return result;
	}

	@Override
	public void saveMail(List<Mail> mailList) {

		for (Mail mail : mailList)
			saveMail(mail);

	}

}
