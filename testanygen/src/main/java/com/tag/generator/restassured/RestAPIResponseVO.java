package com.tag.generator.restassured;

/**
 * 
 *	 Response에 대한 정보를 담은 VO
 */
public class RestAPIResponseVO {
	private String responseCode;
	private String description;
	
	private String responseStatusCode;
	private String responseMessage;
	private RestAPIParameterVO responseBody;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseStatusCode() {
		return responseStatusCode;
	}
	public void setResponseStatusCode(String responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public RestAPIParameterVO getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(RestAPIParameterVO responseBody) {
		this.responseBody = responseBody;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 
	 * @param responseKey
	 * @return
	 */
	public String getExpectedStringResult() {
		if(responseStatusCode != null) {
			StringBuffer sb = new StringBuffer();
			sb.append("응답코드반환::");
			sb.append(responseStatusCode);
			if(responseMessage !=null || !"".equals(responseMessage)) {
				sb.append(",\t ");
				sb.append(responseMessage);
			}
//			if(responseBody !=null || !"".equals(responseBody)) {
//				sb.append(",\t ");
//				sb.append(responseBody);
//			}
			return sb.toString();
		}else {
			return "";
		}
	}
	
}







