package com.tag.restapi.info.vo;

public class ParameterInfo {
	String name;
	String inType;//path, query
	String type;
	String required = "false";//true,false
	String format;
	String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInType() {
		return inType;
	}
	public void setInType(String inType) {
		this.inType = inType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	
}
