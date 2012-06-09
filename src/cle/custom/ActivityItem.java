package cle.custom;

import java.util.Date;

import javax.persistence.Entity;

public class ActivityItem {
	public ActivityItem(String commentText, Date modified, int pageNumber,
			int resourceId, String resourceTitle) {
		
		this.commentText = commentText;
		this.modified = modified;
		this.pageNumber = pageNumber;
		this.resourceId = resourceId;
		this.resourceTitle = resourceTitle;
	}
	String commentText;
	Date modified;
	int pageNumber;
	int resourceId;
	String resourceTitle;
	
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceTitle() {
		return resourceTitle;
	}
	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}
	

}
