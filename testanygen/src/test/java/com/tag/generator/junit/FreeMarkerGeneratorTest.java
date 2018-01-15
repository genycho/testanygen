package com.tag.generator.junit;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tag.common.PropertiesPool;
import com.tag.common.TagUtils;
import com.tag.restapi.writer.FreeMarkerGenerator;
import com.tag.restapi.writer.RestAPIParameterVO;
import com.tag.restapi.writer.RestAPITestCaseVO;
import com.tag.restapi.writer.RestAPITestSuiteVO;

public class FreeMarkerGeneratorTest {
	FreeMarkerGenerator target;
	int varCount = 0;
	
	@Before
	public void setUp() throws Exception {
		target = new FreeMarkerGenerator();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testDataFactoryGeneration() throws Exception {
		PropertiesPool.setOutputPath(TagUtils.getCurrentPath()+"/test_result");
		PropertiesPool.setTemplatePath(TagUtils.getCurrentPath()+"/test_resources");
		
		RestAPIParameterVO bodyParameter = new RestAPIParameterVO();
		bodyParameter.setDefaultValue("");
		bodyParameter.setDescription("bodyParameter입니다.");
		bodyParameter.setFormat("format");
		bodyParameter.setInType("body");
		bodyParameter.setName("UserInfo");
		bodyParameter.setRequired("true");
		bodyParameter.setType("");
		bodyParameter.setValue("");
		
		varCount++;
		bodyParameter.addSubParameter(this.generateInputParameter("sub", "name"+this.varCount, "value"+varCount));
		varCount++;
		bodyParameter.addSubParameter(this.generateInputParameter("sub", "name"+this.varCount, "value"+varCount));
		varCount++;
		bodyParameter.addSubParameter(this.generateInputParameter("sub", "name"+this.varCount, "value"+varCount));
		varCount++;
		bodyParameter.addSubParameter(this.generateInputParameter("sub", "name"+this.varCount, "value"+varCount));
		
		String generatedFilePath = target.generateDataFactory("XXDataFactory", bodyParameter);
		
		System.out.println(generatedFilePath);
		assertNotNull(generatedFilePath);
	}
	
	@Test
	public void testGetMethodGenerate() throws Exception {
		PropertiesPool.setOutputPath(TagUtils.getCurrentPath()+"/test_result");
		PropertiesPool.setTemplatePath(TagUtils.getCurrentPath()+"/test_resources");
		
		RestAPITestSuiteVO testSuiteVO = getTestSuiteInfo("get");
		testSuiteVO.addTestCase(generateTestCaseVO("FirstTestCaseName"));
		testSuiteVO.addTestCase(generateTestCaseVO("SecondTestCaseName"));
		testSuiteVO.addTestCase(generateTestCaseVO("ThirdTestCaseName"));
		
		String generatedFilePath = target.generateRestAssured(testSuiteVO);
		
		System.out.println(generatedFilePath);
		assertNotNull(generatedFilePath);
	}
	
	@Test
	public void testPostMethodGenerate() throws Exception {
		PropertiesPool.setOutputPath(TagUtils.getCurrentPath()+"/test_result");
		PropertiesPool.setTemplatePath(TagUtils.getCurrentPath()+"/test_resources");
		
		RestAPITestSuiteVO testSuiteVO = getTestSuiteInfo("post");
		testSuiteVO.addTestCase(generateTestCaseVO("FirstTestCaseName"));
		testSuiteVO.addTestCase(generateTestCaseVO("SecondTestCaseName"));
		testSuiteVO.addTestCase(generateTestCaseVO("ThirdTestCaseName"));
		
		String generatedFilePath = target.generateRestAssured(testSuiteVO);
		
		System.out.println(generatedFilePath);
		assertNotNull(generatedFilePath);
	}
	
	@Test
	public void testPutMethodGenerate() throws Exception {
		PropertiesPool.setOutputPath(TagUtils.getCurrentPath()+"/test_result");
		PropertiesPool.setTemplatePath(TagUtils.getCurrentPath()+"/test_resources");
		
		RestAPITestSuiteVO testSuiteVO = getTestSuiteInfo("put");

		testSuiteVO.addTestCase(generateTestCaseVO("FirstTestCaseName"));
		testSuiteVO.addTestCase(generateTestCaseVO("SecondTestCaseName"));
		testSuiteVO.addTestCase(generateTestCaseVO("ThirdTestCaseName"));
		
		String generatedFilePath = target.generateRestAssured(testSuiteVO);
		
		System.out.println(generatedFilePath);
		assertNotNull(generatedFilePath);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteGenerate() throws Exception {
		PropertiesPool.setOutputPath(TagUtils.getCurrentPath()+"/test_result");
		PropertiesPool.setTemplatePath(TagUtils.getCurrentPath()+"/test_resources");
		
		RestAPITestSuiteVO testSuiteVO = getTestSuiteInfo("delete");
		testSuiteVO.addTestCase(generateTestCaseVO("FirstTestCaseName"));
		testSuiteVO.addTestCase(generateTestCaseVO("SecondTestCaseName"));
		testSuiteVO.addTestCase(generateTestCaseVO("ThirdTestCaseName"));
		
		String generatedFilePath = target.generateRestAssured(testSuiteVO);
		
		System.out.println(generatedFilePath);
		assertNotNull(generatedFilePath);
	}

	private RestAPITestSuiteVO getTestSuiteInfo(String methodType) {
		RestAPITestSuiteVO testSuiteVO = new RestAPITestSuiteVO();
		testSuiteVO.setName(methodType.substring(0,1).toUpperCase().concat(methodType.substring(1, methodType.length()))+"Member");
		testSuiteVO.setTargetName(testSuiteVO.getName());
		testSuiteVO.setAcceptType("acceptType");
		testSuiteVO.setMethodType(methodType);
		testSuiteVO.setDescription(methodType + " a person information");
		testSuiteVO.setBaseURL("/api/1.0");
		testSuiteVO.setPath("/member");
		testSuiteVO.setPort(null);
		testSuiteVO.setRequestBody("");
		testSuiteVO.setResource("member");
		testSuiteVO.setTargetPackage("member");
		return testSuiteVO;
	}
	
	private com.tag.restapi.writer.RestAPITestCaseVO generateTestCaseVO(String name) {
		this.varCount++;
		RestAPITestCaseVO testCaseVO = new RestAPITestCaseVO();
		testCaseVO.setActualResult("actualResult");
		testCaseVO.setDescription("description");;
		testCaseVO.setExpectedResult("expectedResult");
		testCaseVO.setName(name);
		testCaseVO.setTestCaseType("positive");
		testCaseVO.addInputParameter(this.generateInputParameter("path", "pathVarName"+varCount, "value"+varCount));
		testCaseVO.addInputParameter(this.generateInputParameter("query", "queryVarname"+varCount, "value"+varCount));
		testCaseVO.addInputParameter(this.generateInputParameter("body", "bodyVarname"+varCount, "value"+varCount));
		testCaseVO.addInputParameter(this.generateInputParameter("unknown", "unknownVarname"+varCount, "value"+varCount));
		return testCaseVO;
	}
	
	private RestAPIParameterVO generateInputParameter(String type, String name, String value) {
		RestAPIParameterVO restAPIParameterVO = new RestAPIParameterVO();
		restAPIParameterVO.setName(name);
		restAPIParameterVO.setDefaultValue("defaultValue"+varCount);
		restAPIParameterVO.setFormat("good format");
		restAPIParameterVO.setInType(type);
		
		if( (varCount%2) ==0) {
			restAPIParameterVO.setRequired("true");
		}else {
			restAPIParameterVO.setRequired("false");
		}
		
		restAPIParameterVO.setType("string");
		restAPIParameterVO.setValue(value);
		return restAPIParameterVO;
	}
}
