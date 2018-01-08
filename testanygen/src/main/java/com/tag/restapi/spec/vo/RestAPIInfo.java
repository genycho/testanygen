package com.tag.restapi.spec.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestAPIInfo {
	
	/*COMMON INFORMATION*/
	String swaggerVersion;//1.0, 2.0
//	String title;
//	String description'
	
	String host;
	String baseURL;
	String schemes;
	String consumes;
	String produces;
	
	/*API INFORMATION*/
	/**	Parent resource group hints*/
	String apiTag;
	String apiName;
	String apiPath;
	String method;
	String description;
	String operationId;
	List<ParameterInfo> parameters;
	
	String tempRequestBody;
	
	RequestBody requestBody;
	Map<String,ResponseInfo> responses;
	
	public String getApiTag() {
		return apiTag;
	}
	public void setApiTag(String apiTag) {
		this.apiTag = apiTag;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getBaseURL() {
		return baseURL;
	}
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	public String getSwaggerVersion() {
		return swaggerVersion;
	}
	public void setSwaggerVersion(String swaggerVersion) {
		this.swaggerVersion = swaggerVersion;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
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
	public void addParameter(ParameterInfo parameter) {
		if(this.parameters == null) {
			this.parameters = new ArrayList<>();
		}
		this.parameters.add(parameter);
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
	public String getTempRequestBody() {
		return tempRequestBody;
	}
	public void setTempRequestBody(String tempRequestBody) {
		this.tempRequestBody = tempRequestBody;
	}
	public Map<String, ResponseInfo> getResponses() {
		return responses;
	}
	public void setResponses(Map<String, ResponseInfo> responses) {
		this.responses = responses;
	}
	
	public void addResponseMap(String responseCode, ResponseInfo response) {
		//TODO	현재는 같은 status code에 여러 응답을 추가하면 Map의 키로 덮어 쓰는데... 이를 별도로 식별할지 고민 필요...
		//swagger 의 데이터 모델에서의 키는 ... 
		if(this.responses ==null) {
			this.responses = new HashMap<>();
		}
		this.responses.put(responseCode, response);
	}
	
}
