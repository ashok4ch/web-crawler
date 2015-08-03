package com.imaginea.crawler.dao;

import org.jsoup.nodes.Document;

/**
 * @author ashokh
 *
 */
public class Mail {
	private String msgName = null;
	private String msgLink = null;
	private String dirName=null;
	private Document document=null;
	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}
	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
	/**
	 * @return the inputyear
	 */
	public String getDirName() {
		return dirName;
	}
	/**
	 * @param inputyear the inputyear to set
	 */
	public void setDirName(String inputyear) {
		this.dirName = inputyear;
	}
/**
	 * @return the msgName
	 */
	public String getMsgName() {
		return msgName;
	}
	/**
	 * @param msgName the msgName to set
	 */
	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}
	/**
	 * @return the msgLink
	 */
	public String getMsgLink() {
		return msgLink;
	}
	/**
	 * @param msgLink the msgLink to set
	 */
	public void setMsgLink(String msgLink) {
		this.msgLink = msgLink;
	}
	
}
