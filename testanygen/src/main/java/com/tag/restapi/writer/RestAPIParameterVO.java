package com.tag.restapi.writer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 */
public class RestAPIParameterVO {
	String inType;//path, query, Input(request body), Output(response), 
	String name;
	String description;
	String required;	//true/false
	String type;	//Primitive, Primitive Class, Collection, Array, Object
	String format;
	
	String defaultValue;
	String value;
	
	List<RestAPIParameterVO> subParameterList = null;

	public String getInType() {
		return inType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<RestAPIParameterVO> getSubParameterList() {
		return subParameterList;
	}

	public void setSubParameterList(List<RestAPIParameterVO> subParameterList) {
		this.subParameterList = subParameterList;
	}
	
	public void addSubParameter(RestAPIParameterVO subParameter) {
		if(this.subParameterList == null) {
			this.subParameterList = new ArrayList<>();
		}
		this.subParameterList.add(subParameter);
	}
	
}
