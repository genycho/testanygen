package com.tag.generator.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tag.common.PropertiesPool;
import com.tag.common.TagUtils;
import com.tag.data.vo.TestCaseVO;
import com.tag.data.vo.TestSuiteVO;
import com.tag.generator.restassured.FreeMarkerGenerator;

public class FreeMarkerGeneratorTest {
	FreeMarkerGenerator target;
	
	@Before
	public void setUp() throws Exception {
		target = new FreeMarkerGenerator();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerator() throws Exception {
		PropertiesPool.setOutputPath(TagUtils.getCurrentPath()+"/test_result");
		PropertiesPool.setTemplatePath(TagUtils.getCurrentPath()+"/test_resources");
		
		TestSuiteVO testSuiteVO = new TestSuiteVO();
		testSuiteVO.setTargetName("MyTarget");
		testSuiteVO.addTestCase(generateTestCaseVO("FirstTestCaseName"));
		testSuiteVO.addTestCase(generateTestCaseVO("SecondTestCaseName"));
		testSuiteVO.addTestCase(generateTestCaseVO("ThirdTestCaseName"));
		String generatedFilePath = target.generator(testSuiteVO);
		System.out.println(generatedFilePath);
		assertNotNull(generatedFilePath);
	}
	
	private TestCaseVO generateTestCaseVO(String name) {
		TestCaseVO testCaseVO = new TestCaseVO();
		testCaseVO.setActualResult("actualResult");
		testCaseVO.setDescription("description");;
		testCaseVO.setExpectedResult("expectedResult");
		testCaseVO.setName(name);
		testCaseVO.setTestCaseType("positive");
		return testCaseVO;
	}

}
