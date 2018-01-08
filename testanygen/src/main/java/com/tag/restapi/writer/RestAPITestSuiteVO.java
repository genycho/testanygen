package com.tag.restapi.writer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tag.data.vo.TestSuiteVO;

/**
 *
 */
public class RestAPITestSuiteVO extends TestSuiteVO{
	//각 API에 대한 테스트에 뭐가 필요할까? - TestSuite 레벨 
	private String resource = null;
	private String baseURL = null;
	private String port = null;
	private String acceptType = null;
	private String path = null;
	private String methodType = null;
	private List<RestAPIParameterVO> inputParameters = null;	//테스트 케이스에 넣어야 할까? 각 입력 파라미터에 대한. Body에 대한?
	private Map<String, RestAPIResponseVO> responsesMap = null;
	
	String requestBody = null;	//json? only json
	
	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public List<RestAPIParameterVO> getInputParameters() {
		return inputParameters;
	}

	public void setInputParameters(List<RestAPIParameterVO> inputParameters) {
		this.inputParameters = inputParameters;
	}

	public String getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}

	public Map<String, RestAPIResponseVO> getResponsesMap() {
		return responsesMap;
	}

	public void setResponsesMap(Map<String, RestAPIResponseVO> responsesMap) {
		this.responsesMap = responsesMap;
	}
	
	public void addResponseMap(String key, RestAPIResponseVO response) {
		if(this.responsesMap == null) {
			this.responsesMap = new HashMap<>();
		}
		this.responsesMap.put(key, response);
	}
	
	public RestAPIResponseVO getResponse(String key) {
		return this.responsesMap.get(key);
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Name=");
		sb.append(getName());
		
		sb.append(", APIPath= ");
		sb.append(getPath());
		
		sb.append(", MethodType= ");
		sb.append(getMethodType());
		
		return sb.toString();
	}
	
	
	
}
