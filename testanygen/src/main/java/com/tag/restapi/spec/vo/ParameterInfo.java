package com.tag.restapi.spec.vo;

import java.util.Map;

public class ParameterInfo {
	String name;
	String inType;//path, query
	String type;
	String required = "false";//true,false
	String format;
	String description;
	
	String example;
	String defaultValue;
	
	Map<String, ParameterInfo> bodyParameterMap;
	String refBodyModelKey;
	
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
	public Map<String, ParameterInfo> getBodyParameterMap() {
		return bodyParameterMap;
	}
	public void setBodyParameterMap(Map<String, ParameterInfo> bodyParameterMap) {
		this.bodyParameterMap = bodyParameterMap;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	public String getRefBodyModelKey() {
		return refBodyModelKey;
	}
	public void setRefBodyModelKey(String refBodyModelKey) {
		this.refBodyModelKey = refBodyModelKey;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
