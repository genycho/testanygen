package com.tag.main;

import java.util.List;

import com.tag.analyzer.RestAPISpecAnalyzer;
import com.tag.common.TAGException;
import com.tag.generator.restassured.FreeMarkerGenerator;
import com.tag.generator.restassured.RestAPITestSuiteVO;
import com.tag.restapi.info.vo.RestAPIInfo;
import com.tag.restapi.spec.parser.SwaggerSpecParser;

public class RestAPITestManager {

	SwaggerSpecParser parser = new SwaggerSpecParser();
	RestAPISpecAnalyzer analyzer = new RestAPISpecAnalyzer();
	FreeMarkerGenerator generator = new FreeMarkerGenerator();

	
	public String analyzeAndGenerate(String yamlSpecFilePath) throws TAGException {
//		 List<RestAPIInfo> temp = analyzeRestAPITestCase(yamlSpecFilePath);
//		 String resultMessage = this.generateRestAssuredTestFile(temp);
//		return resultMessage;
		return null;
	}
	
//	public List<RestAPITestSuiteVO> analyzeRestAPITestCase(List<RestAPIInfo> temp) throws TAGException {
//		parser.parse(temp);
//		List<RestAPITestSuiteVO> result = parser.analyze(parser.getSwaggerSpecInfo());
//		if (result == null) {
//			throw new TAGException("Failed to analyze REST API SPEC Information.");
//		}
//		return result;
//	}

	public String generateRestAssuredTestFile(List<RestAPITestSuiteVO> testSuiteList) throws TAGException {
		analyzer.doTestDesign(testSuiteList);
		StringBuffer sb = new StringBuffer();
		for(RestAPITestSuiteVO testSuite : testSuiteList){
			try {
				sb.append(generateRestAssuredTestFile(testSuite));
				sb.append("generated.\n");
			}catch(TAGException checkAndContinue) {
				sb.append("Generation failed for ");
				sb.append(testSuite.getName());
				sb.append("- ");
				sb.append(checkAndContinue.getMessage());
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	public String generateRestAssuredTestFile(RestAPITestSuiteVO testSuite) throws TAGException {
		return generator.generator(testSuite);
	}

}
