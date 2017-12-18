package com.tag.analyzer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tag.common.TagUtils;
import com.tag.generator.restassured.RestAPIResponseVO;
import com.tag.generator.restassured.RestAPITestSuiteVO;
import com.tag.restapi.spec.parser.SwaggerSpecParser;

import io.swagger.models.Swagger;

public class SwaggerSpecAnalyzerTest {
	RestAPISpecAnalyzer target;

	@Before
	public void setUp() throws Exception {
		target = new RestAPISpecAnalyzer();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSwaggerSpecAnalyze_Basic() throws Exception {
		List<RestAPITestSuiteVO> testSuiteList = new ArrayList<>();
		testSuiteList.add(getTestSuite("CreateMember","POST"));
		testSuiteList.add(getTestSuite("GETMEMBER","GET"));
		
		assertNull(testSuiteList.get(0).getTestCaseList());
		assertNull(testSuiteList.get(1).getTestCaseList());
		
		target.doTestDesign(testSuiteList);
		
		assertNotNull(testSuiteList.get(0).getTestCaseList());
		assertNotNull(testSuiteList.get(1).getTestCaseList());
	}
	
	private RestAPITestSuiteVO getTestSuite(String name, String methodType) {
		RestAPITestSuiteVO testSuite = new RestAPITestSuiteVO();
		testSuite.setAcceptType("acceptType");
		testSuite.setBaseURL("baseURL");
		testSuite.setMethodType(methodType);
		testSuite.setName(name);
		testSuite.setPath("path");
		testSuite.setPort("port");
		testSuite.setRequestBody("requestBod");
		testSuite.setResponsesMap(null);
		testSuite.addResponseMap("200", getTempResponse("200","200_ok"));
		testSuite.addResponseMap("400", getTempResponse("400","400AA"));
		testSuite.addResponseMap("400", getTempResponse("400","400BB"));
		testSuite.addResponseMap("404", getTempResponse("404","Not Found"));
		testSuite.addResponseMap("500", getTempResponse("500","Internal Server error"));
		
		testSuite.setTargetName("targetName");
		testSuite.setTargetPackage("targetPackage");
//		testSuite.setTestCaseList(testCaseList);

		testSuite.setInputParameters(null);
		return testSuite;
	}

	private RestAPIResponseVO getTempResponse(String responseCode, String responseMessage) {
		RestAPIResponseVO temp = new RestAPIResponseVO();
		temp.setDescription("description");
		temp.setResponseBody(null);
		temp.setResponseCode(responseCode);
		temp.setResponseMessage(responseMessage);
		temp.setResponseStatusCode(responseCode);
		
		return null;
	}


}
