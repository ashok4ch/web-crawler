package com.imaginea;

import java.util.Set;

public class Link {
	private String parentLink=null;
	private String link=null;
	private Set<Link> childLinksSet = null;
	private boolean isParent = false;
	private int level=0;
	
	/**
	 * @return the linkStr
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param linkStr the linkStr to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	/**
	 * @return the parentLink
	 */
	public String getParentLink() {
		return parentLink;
	}
	/**
	 * @param parentLink the parentLink to set
	 */
	public void setParentLink(String parentLink) {
		this.parentLink = parentLink;
	}
	/**
	 * @return the childLinksSet
	 */
	public Set<Link> getChildLinksSet() {
		return childLinksSet;
	}
	/**
	 * @param childLinksSet the childLinksSet to set
	 */
	public void setChildLinksSet(Set<Link> childLinksSet) {
		this.childLinksSet = childLinksSet;
	}
	/**
	 * @return the isParent
	 */
	public boolean isParent() {
		return isParent;
	}
	/**
	 * @param isParent the isParent to set
	 */
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
		

}
