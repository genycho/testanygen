package com.tag.analyzer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tag.restapi.spec.vo.ResponseInfo;
import com.tag.restapi.spec.vo.RestAPIInfo;
import com.tag.restapi.writer.RestAPITestCaseVO;
import com.tag.restapi.writer.RestAPITestSuiteVO;

public class RestAPISpecAnalyzerTest {
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

		RestAPIInfo restAPIInfo = getTempRestAPIInfo("Find members", "get","/api/s/member");
		
		RestAPITestSuiteVO analyzedTestSuite = target.analyze(restAPIInfo,1);
		assertNotNull(analyzedTestSuite);
		assertNotNull(analyzedTestSuite.getTestCaseList());
		assertTrue(analyzedTestSuite.getTestCaseList().size() > 0);
		assertEquals(1, analyzedTestSuite.getTestCaseList().size());
		
		RestAPITestCaseVO case1 = (RestAPITestCaseVO) analyzedTestSuite.getTestCaseList().get(0);
		assertNotNull(case1);
		int tcCount = analyzedTestSuite.getTestCaseList().size();
		
		assertEquals(null, case1.getActualResult());
		assertEquals("This is one of the basic tests only with mandatory input parameters. 선택 입력 값들이 디폴트 값으로 동작하는지 확인합니다", case1.getDescription());
		assertEquals("정상수행, 선택 입력 값들이 디폴트 값으로 동작하는지 확인합니다", case1.getExpectedResult());
		assertEquals(1, case1.getGeneratedTestLevel());
		assertEquals("apiName_MandatoryInput_ok", case1.getName());
		assertEquals("200", case1.getTestCaseType());
		assertNull(case1.getTestStepList());
		
		analyzedTestSuite = target.analyze(restAPIInfo,2);
		assertTrue(analyzedTestSuite.getTestCaseList().size() > tcCount);

		assertEquals(tcCount+2, analyzedTestSuite.getTestCaseList().size());
	}
	
	private RestAPIInfo getTempRestAPIInfo(String name, String methodType, String apiPath) {
		RestAPIInfo restAPIInfo = new RestAPIInfo();
		restAPIInfo.setApiName("apiName");
		restAPIInfo.setApiPath(apiPath);
		restAPIInfo.setApiTag("apiTag");
		restAPIInfo.setBaseURL("baseURL");
		restAPIInfo.setConsumes("consumes");
		restAPIInfo.setDescription("description");
		restAPIInfo.setHost("host");
		restAPIInfo.setMethod("method");
		restAPIInfo.setOperationId("operationId");
		// testSuite.setParameters(parameters);
		restAPIInfo.setProduces("produces");
		// testSuite.setRequestBody(requestBody);
		restAPIInfo.setSchemes("schemes");
		restAPIInfo.setSwaggerVersion("swaggerVersion");
		restAPIInfo.setTempRequestBody("tempRequestBody");

		restAPIInfo.addResponseMap("200", getTempResponse("200", "200_ok"));
		restAPIInfo.addResponseMap("400", getTempResponse("400", "400AA"));
		restAPIInfo.addResponseMap("400", getTempResponse("400", "400BB"));
		restAPIInfo.addResponseMap("404", getTempResponse("404", "Not Found"));
		restAPIInfo.addResponseMap("500", getTempResponse("500", "Internal Server error"));

		// testSuite.setTargetName("targetName");
		// testSuite.setTargetPackage("targetPackage");
		// testSuite.setTestCaseList(testCaseList);

		// testSuite.setInputParameters(null);
		return restAPIInfo;
	}
	
	private ResponseInfo getTempResponse(String responseCode, String responseMessage) {
		ResponseInfo temp = new ResponseInfo();
		temp.setDescription(responseMessage);
		temp.setStatusCode(responseCode);
		temp.setTempResponseValue(responseMessage);
		return temp;
	}
	
}
