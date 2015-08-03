package com.imaginea.crawler.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.imaginea.crawler.loader.DocumentLoader;

public class MailDaoImplTest {
	private Mail mail;
	private MailDao mailDao;
	private List<Mail> list;

	@Before
	public void setUp() throws Exception {
		mail = new Mail();
		mail.setMsgLink(
				"http://mail-archives.apache.org/mod_mbox/maven-users/201507.mbox/raw/%3CCAOe3-fZbQ4QeUNDkYyUUZ7bsoUFa_%2BEN9hxqDh%2BOzm6ybVecKw%40mail.gmail.com%3E");
		mail.setDocument(DocumentLoader.getDocument(
				"http://mail-archives.apache.org/mod_mbox/maven-users/201507.mbox/raw/%3CCAOe3-fZbQ4QeUNDkYyUUZ7bsoUFa_%2BEN9hxqDh%2BOzm6ybVecKw%40mail.gmail.com%3E"));
		mail.setDirName("ashok");
		mail.setMsgName("ashok_testmail");
		list = new ArrayList<Mail>(2);
		Mail mail2 = new Mail();
		mail.setMsgLink(
				"http://mail-archives.apache.org/mod_mbox/maven-users/201507.mbox/raw/%3C559403DB.1080806%40artifact-software.com%3E");
		mail.setDocument(DocumentLoader.getDocument(
				"http://mail-archives.apache.org/mod_mbox/maven-users/201507.mbox/raw/%3C559403DB.1080806%40artifact-software.com%3E"));
		mail2.setDirName("ashok1");
		mail2.setMsgName("ashok_testmail");
		list.add(mail);
		list.add(mail2);
		mailDao = new MailDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
		if (mail != null)
			mail = null;
	}

	@Test
	public final void testSaveMailMail() {
		assertEquals(mailDao.saveMail(mail), true);
	}
}
