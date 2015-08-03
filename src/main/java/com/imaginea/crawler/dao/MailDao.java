package com.imaginea.crawler.dao;

import java.util.List;

public interface MailDao {

	public boolean saveMail(Mail mail);

	public void saveMail(List<Mail> mailList);

}
