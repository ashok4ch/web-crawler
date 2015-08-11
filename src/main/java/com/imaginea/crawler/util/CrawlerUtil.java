package com.imaginea.crawler.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CrawlerUtil {
	// We'll use a fake USER_AGENT so the web server thinks the robot is a
	// normal web browser.
	private static Logger logger = Logger.getLogger(CrawlerUtil.class);
	private static Scanner scan = new Scanner(System.in);
	public static final Properties PROPERTIES = new Properties();

	static {
		try {
			PROPERTIES.load(ClassLoader.class.getResourceAsStream("/crawler.properties"));
		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
			logger.fatal(fne.getMessage());
		} catch (IOException ioe) {
			logger.fatal(ioe.getMessage());
		}
	}

	public static String getUrl() {
		return PROPERTIES.getProperty("crawler.rooturl");
	}

	public static String getRootDirPath() {
		return CrawlerUtil.PROPERTIES.getProperty("crawler.rootdir");
	}

	public static File getRootDir(String dirName) {
			StringBuilder fullPath = new StringBuilder(CrawlerUtil.PROPERTIES.getProperty("crawler.rootdir"));
			if (dirName != null) {
				fullPath.append(File.separator).append(dirName);
			}
			logger.info("getRootDir has started path :" + fullPath.toString());
			File rootDir = new File(fullPath.toString());
			rootDir.mkdirs();
		
		return rootDir;
	}

	public static void setLogLevel() {
		System.out.println("Enter the log Level (OFF->6, FATAL->5, ERROR -> 4, WARN-> 3, INFO-> 2 and DEBUG->1): ");

		int level = scan.nextInt();
		switch (level) {
		case 1:
			setLevelToAllLoggers(Level.DEBUG);
			break;
		case 2:
			setLevelToAllLoggers(Level.INFO);
			break;
		case 3:
			setLevelToAllLoggers(Level.WARN);
			break;
		case 4:
			setLevelToAllLoggers(Level.ERROR);
			break;
		case 5:
			setLevelToAllLoggers(Level.FATAL);
			break;

		case 6:
			setLevelToAllLoggers(Level.OFF);
			break;

		default:
			System.out.println(
					"Please enter Correct log level (OFF->6, FATAL->5, ERROR -> 4, WARN-> 3, INFO-> 2 and DEBUG->1):");
			setLogLevel();
		}
	}

	@SuppressWarnings("unchecked")
	public static void setLevelToAllLoggers(Level level) {
		Logger root = Logger.getRootLogger();
		Enumeration<Category> allLoggers = root.getLoggerRepository().getCurrentCategories();
		root.setLevel(level);
		while (allLoggers.hasMoreElements()) {
			Category tmpLogger = (Category) allLoggers.nextElement();
			tmpLogger.setLevel(Level.DEBUG);
		}
	}

}
