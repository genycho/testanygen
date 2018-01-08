package com.tag.restapi.spec.vo;

public class ResponseInfo {
	/*EXAMPLE
	 * "responses": {
					"200": {
						"description": "An array of products",
						"schema": {
							"type": "array",
							"items": {
								"$ref": "#/definitions/Product"
							}
						}
					},
	 */
	String statusCode;
	String description;
	String tempResponseValue;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTempResponseValue() {
		return tempResponseValue;
	}
	public void setTempResponseValue(String tempResponseValue) {
		this.tempResponseValue = tempResponseValue;
	}
	
	
}
