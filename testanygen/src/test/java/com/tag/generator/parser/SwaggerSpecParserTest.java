package com.tag.generator.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tag.common.TagUtils;
import com.tag.restapi.spec.parser.SwaggerSpecParser;
import com.tag.restapi.spec.vo.RestAPIInfo;

import io.swagger.models.Swagger;

public class SwaggerSpecParserTest {
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
		Swagger swaggerSpec = target.parseSpecFile(filePath);
		assertNotNull(swaggerSpec);
	}
	
	@Test
	public void testParsePetStoreSpec() throws Exception {
		String filePath = TagUtils.getCurrentPath()+"/test_resources/petstore_sample.json";
		Swagger swaggerInfo = target.parseSpecFile(filePath);
		List<RestAPIInfo> resultList = target.parse(swaggerInfo);
		assertNotNull(resultList);
		assertEquals(4,resultList.size());
		
		RestAPIInfo firstAPIInfo = resultList.get(0);
	}
	
	@Test
	public void testParseUberJSONSpec() throws Exception {
		String filePath = TagUtils.getCurrentPath()+"/test_resources/uber_sample.json";
		Swagger swaggerInfo = target.parseSpecFile(filePath);
		List<RestAPIInfo> resultList = target.parse(swaggerInfo);
		assertNotNull(resultList);
		assertEquals(5,resultList.size());
		
		RestAPIInfo firstAPIInfo = resultList.get(0);
		assertEquals("Product Types", firstAPIInfo.getApiName());
		assertEquals("/products", firstAPIInfo.getApiPath());
		assertEquals("Products", firstAPIInfo.getApiTag());
		assertEquals("https://api.uber.com/v1", firstAPIInfo.getBaseURL());
		assertEquals(null, firstAPIInfo.getConsumes());
		assertTrue(firstAPIInfo.getDescription().startsWith("The Products endpoint returns information about the Uber products "));
		assertEquals(null, firstAPIInfo.getHost());
		assertEquals("get", firstAPIInfo.getMethod());
		assertEquals(null, firstAPIInfo.getOperationId());
//		assertEquals("", firstAPIInfo.getParameters());
		assertNotNull(firstAPIInfo.getParameters());
		assertTrue(firstAPIInfo.getParameters().size() > 0);
		
		assertEquals(null, firstAPIInfo.getProduces());
//		assertEquals(null, firstAPIInfo.getRequestBody());
		
		assertNotNull(firstAPIInfo.getResponses());
		assertEquals(2, firstAPIInfo.getResponses().size());
		
		assertEquals(null, firstAPIInfo.getSwaggerVersion());//TODO	현재는 2.0만 only 분석하고 있어서 우리 내부 모델에 담지 않음 
		assertEquals(null, firstAPIInfo.getSchemes());//상동 
	}
	
	@Test
	public void testParseForUberYamlParser() throws Exception {
		String filePath = TagUtils.getCurrentPath()+"/test_resources/uber_sample.yaml";
		Swagger swaggerInfo = target.parseSpecFile(filePath);
		List<RestAPIInfo> resultList = target.parse(swaggerInfo);
		assertNotNull(resultList);
		assertEquals(5,resultList.size());
	}
	
}
