package org.geometerplus.android.fbreader;

public class ResultObject {
	private String query;
	private String id;
	private String title;
	private String ownerName;
	private String dateupload;
	private String image;


	ResultObject(String query, String id, String title, String ownername, String dateupload, String image){
		this.query = query;
		this.id = id;
		this.title = title;
		this.ownerName = ownername;
		this.dateupload = dateupload;
		this.setImage(image);
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
