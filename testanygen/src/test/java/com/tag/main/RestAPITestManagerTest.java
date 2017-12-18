package com.tag.main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tag.analyzer.TestUtils;
import com.tag.common.PropertiesPool;

import junit.extensions.TestSetup;

public class RestAPITestManagerTest {
	RestAPITestManager target;
	
	@Before
	public void setUp() throws Exception {
		target = new RestAPITestManager();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAnalyzeAndGenerate() throws Exception {
		PropertiesPool.setOutputPath(TestUtils.getCurrentPath()+"/test_result");
		PropertiesPool.setTemplatePath(TestUtils.getCurrentPath()+"/test_resources");
		PropertiesPool.setTestGenerateLevel(1);
		String resultMessage = target.analyzeAndGenerate(TestUtils.getCurrentPath()+"/test_resources/petstore_sample.yaml");
		System.out.println(resultMessage);
	}

	@Test
	public void testAnalyzeRestAPITestCase() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateRestAssuredTestFileListOfRestAPITestSuiteVO() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateRestAssuredTestFileRestAPITestSuiteVO() throws Exception {
		fail("Not yet implemented");
	}

}
