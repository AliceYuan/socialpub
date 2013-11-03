package org.geometerplus.android.fbreader;

import android.widget.EditText;

public class ResultObject {
	private String query;
	private String id;
	private String title;
	private String userId;
	private String ownerName;
	private String dateupload;
	private String image;
	private int paragraphStartIndex;
	private int paragraphEndIndex;
	private int elementStartIndex;
	private int elementEndIndex;
	private int charStartIndex;
	private int charEndIndex;

	public ResultObject(String query, String id, String title, String ownername, String dateupload, String image, String userId) {
		this.query = query;
		this.id = id;
		this.title = title;
		this.ownerName = ownername;
		this.dateupload = dateupload;
		this.setImage(image);
		this.userId = userId;
	}
	

	public void setRange(int paragraphStartIndex, int paragraphEndIndex, int elementStartIndex, int elementEndIndex, int charStartIndex, int charEndIndex) {
		this.paragraphStartIndex = paragraphStartIndex;
		this.paragraphEndIndex = paragraphEndIndex;
		this.elementStartIndex = elementStartIndex;
		this.elementEndIndex = elementEndIndex;
		this.charStartIndex = charStartIndex;
		this.charEndIndex = charEndIndex;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getDatetaken() {
		return dateupload;
	}

	public void setDatetaken(String datetaken) {
		this.dateupload = datetaken;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getImageUrl() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
