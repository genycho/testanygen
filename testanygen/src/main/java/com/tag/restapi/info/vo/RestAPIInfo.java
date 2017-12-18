package com.tag.restapi.info.vo;

import java.util.List;

public class RestAPIInfo {
	
	/*COMMON INFORMATION*/
	String swaggerVersion;//1.0, 2.0
//	String title;
//	String description'
	
	String hotst;
	String basePath;
	String schemes;
	String consumes;
	String produces;
	
	/*API INFORMATION*/
	String apiPath;
	String method;
	String description;
	String operationId;
	List<ParameterInfo> parameters;
	RequestBody requestBody;
	List<ResponseInfo> responses;
	ResponseBody responseBody;
	
	public String getSwaggerVersion() {
		return swaggerVersion;
	}
	public void setSwaggerVersion(String swaggerVersion) {
		this.swaggerVersion = swaggerVersion;
	}
	public String getHotst() {
		return hotst;
	}
	public void setHotst(String hotst) {
		this.hotst = hotst;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getSchemes() {
		return schemes;
	}
	public void setSchemes(String schemes) {
		this.schemes = schemes;
	}
	public String getConsumes() {
		return consumes;
	}
	public void setConsumes(String consumes) {
		this.consumes = consumes;
	}
	public String getProduces() {
		return produces;
	}
	public void setProduces(String produces) {
		this.produces = produces;
	}
	public String getApiPath() {
		return apiPath;
	}
	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public List<ParameterInfo> getParameters() {
		return parameters;
	}
	public void setParameters(List<ParameterInfo> parameters) {
		this.parameters = parameters;
	}
	public RequestBody getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(RequestBody requestBody) {
		this.requestBody = requestBody;
	}
	public List<ResponseInfo> getResponses() {
		return responses;
	}
	public void setResponses(List<ResponseInfo> responses) {
		this.responses = responses;
	}
	public ResponseBody getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(ResponseBody responseBody) {
		this.responseBody = responseBody;
	}
	
	
	

}
