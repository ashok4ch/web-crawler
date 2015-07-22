package com.imaginea;

import java.io.File;

/**
 * @author ashokh
 *
 */
public class Mail {
	private String msgName = null;
	private String msgLink = null;
	private File fileDirectory =null;
	
	/**
	 * @return the fileDirectory
	 */
	
	public File getFileDirectory() {
		return fileDirectory;
	}
	/**
	 * @param fileDirectory the fileDirectory to set
	 */
	
	public void setFileDirectory(File fileDirectory) {
		this.fileDirectory = fileDirectory;
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
