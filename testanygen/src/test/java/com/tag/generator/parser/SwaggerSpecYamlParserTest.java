package com.tag.generator.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tag.common.TagUtils;
import com.tag.generator.restassured.RestAPITestSuiteVO;
import com.tag.restapi.spec.parser.SwaggerSpecParser;

import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;

public class SwaggerSpecYamlParserTest {
	SwaggerSpecParser target;

	@Before
	public void setUp() throws Exception {
		target = new SwaggerSpecParser();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParse() throws Exception {
		String filePath = TagUtils.getCurrentPath()+"/test_resources/uber_sample.yaml";
		target.parse(filePath);
	}
	
	@Test
	public void testParseJSONSpec() throws Exception {
		String filePath = TagUtils.getCurrentPath()+"/test_resources/uber_sample.json";
		SwaggerParser parser = new SwaggerParser();
		Swagger swagger = parser.read(filePath);
		swagger.getInfo();
	}
	
	@Test
	public void testParseWithSwaggerParser() throws Exception {
		String filePath = TagUtils.getCurrentPath()+"/test_resources/uber_sample.yaml";
		SwaggerParser parser = new SwaggerParser();
		Swagger swagger = parser.read(filePath);
		swagger.getInfo();
	}
	
	@Test
	public void testAnalyzeUberSample() throws Exception {
		SwaggerSpecParser parser = new SwaggerSpecParser();
		parser.parse(TagUtils.getCurrentPath() + "/test_resources/uber_sample.yaml");
		Swagger swaggerInfo = parser.getSwaggerSpecInfo();

		List<RestAPITestSuiteVO> result = target.analyze(swaggerInfo);

		assertNotNull(result);
		assertEquals(5, result.size());// package 값에 tags ArrayList의 값 Products로 구분,
		for (RestAPITestSuiteVO testSuite : result) {
			System.out.println(testSuite.getName());
		}
		RestAPITestSuiteVO firstTestSuite = result.get(0);
		assertNotNull(firstTestSuite.getName());
		assertEquals("Product Types", firstTestSuite.getName());
		assertNotNull(firstTestSuite.getBaseURL());
		assertEquals("https://api.uber.com/v1", firstTestSuite.getBaseURL());
		assertNotNull(firstTestSuite.getDescription());
		assertNotNull(firstTestSuite.getMethodType());
		assertEquals("get", firstTestSuite.getMethodType());
		assertNotNull(firstTestSuite.getPath());
		assertEquals("/products", firstTestSuite.getPath());
		assertNull(firstTestSuite.getPort());
		assertNotNull(firstTestSuite.getResponsesMap());
		assertNotNull(firstTestSuite.getTargetName());
		assertEquals("Product Types", firstTestSuite.getTargetName());
		assertNotNull(firstTestSuite.getTargetPackage());
		assertEquals("Products", firstTestSuite.getTargetPackage());
		assertNull(firstTestSuite.getTestCaseList());
	}
	
	@Test
	public void testAnalyzePetStoreSample() throws Exception {
		SwaggerSpecParser parser = new SwaggerSpecParser();
		parser.parse(TagUtils.getCurrentPath() + "/test_resources/petstore_sample.yaml");
		Swagger swaggerInfo = parser.getSwaggerSpecInfo();
		
		List<RestAPITestSuiteVO> result = target.analyze(swaggerInfo);
		
		assertNotNull(result);
		assertEquals(20, result.size());//produces에 application/json 값, package 값에 tags ArrayList의 값 Products로 구분, 
		
		for(RestAPITestSuiteVO testSuite : result) {
			System.out.println(testSuite.getName());
		}
		RestAPITestSuiteVO firstTestSuite = result.get(0);
		assertNotNull(firstTestSuite.getName());
		assertEquals("addPet", firstTestSuite.getName());
		assertNotNull(firstTestSuite.getBaseURL());
		assertEquals("http:///v2", firstTestSuite.getBaseURL());
		assertNotNull(firstTestSuite.getDescription());
		assertNotNull(firstTestSuite.getMethodType());
		assertEquals("post", firstTestSuite.getMethodType());
		assertNotNull(firstTestSuite.getPath());
		assertEquals("/pet", firstTestSuite.getPath());
		assertNull(firstTestSuite.getPort());
		assertNotNull(firstTestSuite.getResponsesMap());
		assertNotNull(firstTestSuite.getTargetName());
		assertEquals("Add a new pet to the store", firstTestSuite.getTargetName());
		assertNotNull(firstTestSuite.getTargetPackage());
		assertEquals("pet", firstTestSuite.getTargetPackage());
		assertNull(firstTestSuite.getTestCaseList());
	}

}
