package com.datum.article;

class ClassInfo{
	private Double ID;
	private Double parentID;
	private String code;
	private String name;
	private String memo;
	private String keywords;

	public ClassInfo(Double ID, Double parentID, String code, String name, String memo, String keywords) {
		// TODO Auto-generated constructor stub
		this.ID = ID;
		this.parentID = parentID;
		this.code = code;
		this.name = name;
		this.memo = memo;
		this.keywords = keywords;
	}
	
	public String toString(){
		return code + " " + name;
	}

	public Double getID() {
		return ID;
	}

	public void setID(Double iD) {
		this.ID = iD;
	}

	public Double getParentID() {
		return parentID;
	}

	public void setParentID(Double parentID) {
		this.parentID = parentID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

}
