package com.tag.restapi.writer;

import java.util.ArrayList;
import java.util.List;

import com.tag.data.vo.TestCaseVO;

public class RestAPITestCaseVO extends TestCaseVO{
	public static final String RESTTCTYPE_200OK = "200";
	public static final String RESTTCTYPE_400BADREQUEST = "400";
	public static final String RESTTCTYPE_404NOTFOUND = "404";
	public static final String RESTTCTYPE_500UNKNOWN = "500";
	
	//각 API에 대한 테스트에 뭐가 필요할까? - TestCase 레벨

	//기대결과?
	//TC의 목적? 레벨 1/2/3/4
	//API Method에 따른 테스트  
	
	private List<RestAPIParameterVO> inputParameters;
	private String responseCode;
	
	public void addInputParameter(RestAPIParameterVO inputParameter) {
		if(this.inputParameters == null) {
			this.inputParameters = new ArrayList<>();
		}
		this.inputParameters.add(inputParameter);
	}
	public List<RestAPIParameterVO> getInputParameters() {
		return inputParameters;
	}
	public void setInputParameters(List<RestAPIParameterVO> inputParameters) {
		this.inputParameters = inputParameters;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	

}