package com.tag.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tag.common.PropertiesPool;
import com.tag.common.TestUtils;

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
	public void testAnalyzeAndGenerateForyaml() throws Exception {
		PropertiesPool.setOutputPath(TestUtils.getCurrentPath()+"/test_result");
		PropertiesPool.setTemplatePath(TestUtils.getCurrentPath()+"/test_resources");
		PropertiesPool.setTestGenerateLevel(1);
		String resultMessage = target.analyzeAndGenerate(TestUtils.getCurrentPath()+"/test_resources/petstore_sample.yaml");
		System.out.println(resultMessage);
	}
	
	@Test
	public void testAnalyzeAndGenerateForJson() throws Exception {
		PropertiesPool.setOutputPath(TestUtils.getCurrentPath()+"/test_result");
		PropertiesPool.setTemplatePath(TestUtils.getCurrentPath()+"/test_resources");
		PropertiesPool.setTestGenerateLevel(2);
		String resultMessage = target.analyzeAndGenerate(TestUtils.getCurrentPath()+"/test_resources/petstore_sample.json");
		System.out.println(resultMessage);
	}
	
	@Test
	public void testAnalyzeAndGenerateForUberJson() throws Exception {
		PropertiesPool.setOutputPath(TestUtils.getCurrentPath()+"/test_result");
		PropertiesPool.setTemplatePath(TestUtils.getCurrentPath()+"/test_resources");
		PropertiesPool.setTestGenerateLevel(2);
		String resultMessage = target.analyzeAndGenerate(TestUtils.getCurrentPath()+"/test_resources/uber_sample.json");
		System.out.println(resultMessage);
	}

}
