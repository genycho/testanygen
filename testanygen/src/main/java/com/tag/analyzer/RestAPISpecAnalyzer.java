package com.tag.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tag.common.PropertiesPool;
import com.tag.common.TAGException;
import com.tag.data.vo.TestCaseVO;
import com.tag.generator.restassured.RestAPIParameterVO;
import com.tag.generator.restassured.RestAPIResponseVO;
import com.tag.generator.restassured.RestAPITestCaseVO;
import com.tag.generator.restassured.RestAPITestSuiteVO;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;

/**
 *	Swagger Specification reference : <a href= "http://openapi-specification-visual-documentation.apihandyman.io">http://openapi-specification-visual-documentation.apihandyman.io</a></br> 
 *
 */
public class RestAPISpecAnalyzer implements ISpecAnalyzer {
	
	/**
	 * 
	 * @param testSuiteList
	 * @throws TAGException
	 */
	public void doTestDesign(List<RestAPITestSuiteVO> testSuiteList) throws TAGException {
		analyzeTestCase(testSuiteList, PropertiesPool.getTestGenerateLevel());
	}
	
	private void analyzeTestCase(List<RestAPITestSuiteVO> testSuiteList, int testDesignLevel) throws TAGException {
		if(testSuiteList == null || testSuiteList.size() ==0) {
			return;
		}else {
			for(RestAPITestSuiteVO testSuite : testSuiteList) {
				analyzeTestCase(testSuite, PropertiesPool.getTestGenerateLevel());
			}
		}
	}
	
	private void analyzeTestCase(RestAPITestSuiteVO testSuiteVO, int testDesignLevel) throws TAGException {
		//level 1 -> 필수입력, 선택입력, 200 정상 응답
		//level 2 -> Response codes
		//level 3 -> Method type별 추가 테스트 
		//level 4 ->  각 입력 파라미터에 따른 TC
		//level 5 -> API간 특성, 리소스의 특성 등을 반영한 테스트
		switch (testDesignLevel) {
			case 1: 
				doLevel1TestDesign(testSuiteVO);
				break;
			case 2: 
				doLevel2TestDesign(testSuiteVO);
				break;
			default: 
				throw new TAGException("Not yet implemented.");
		}
	}

	/**
	 * 기본 케이스를 생성하는 메소드. Input TestSute의 TestCase가 최초에는 비어있지만 분석 후에는 </br>
	 * 테스트 케이스 리스트가 생성된다	</br>
	 * 	</br> 
	 * @param testSuite
	 * @throws TAGException
	 */
	private void doLevel1TestDesign(RestAPITestSuiteVO testSuite) throws TAGException {
		//This will add two test cases
		
		RestAPITestCaseVO firstTestCaseVO = new RestAPITestCaseVO();
		firstTestCaseVO.setDescription("This is one of the basic tests only with mandatory input parameters. 선택 입력 값들이 디폴트 값으로 동작하는지 확인합니다");
		//TODO	201 등의 다른 정상 응답 코드를 쓴 경우의 텍스트 처리 
		firstTestCaseVO.setName(testSuite.getName()+"_MandatoryInput_200ok");
		firstTestCaseVO.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_200OK);
		firstTestCaseVO.setGeneratedTestLevel(1);
		firstTestCaseVO.setExpectedResult("정상수행, 선택 입력 값들이 디폴트 값으로 동작하는지 확인합니다");
		testSuite.addTestCase(firstTestCaseVO);

		if(testSuite.getInputParameters() !=null && testSuite.getInputParameters().size() > 1) {
			RestAPITestCaseVO secondTestCaseVO = new RestAPITestCaseVO();
			firstTestCaseVO.setDescription("All input parameters tests( mandatory & optional parameters). 선택 입력 값들이 디폴트 값이 아닌 선택한 값으로 동작하는지 확인합니다");
			//TODO	201 등의 다른 정상 응답 코드를 쓴 경우의 텍스트 처리 
			firstTestCaseVO.setExpectedResult("정상수행, 선택 입력 값들이 디폴트 값이 아닌 선택한 값으로 동작한다"); 
			firstTestCaseVO.setName(testSuite.getName()+"_AllInput_200ok");
			firstTestCaseVO.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_200OK);
			firstTestCaseVO.setGeneratedTestLevel(1);
			testSuite.addTestCase(secondTestCaseVO);
		}
	}
	
	private void doLevel2TestDesign(RestAPITestSuiteVO testSuite) throws TAGException {
		doLevel1TestDesign(testSuite);
		for(Entry<String, RestAPIResponseVO>  temp : testSuite.getResponsesMap().entrySet()) {
			if("200".equals(temp.getKey())){
				//do what? yet nothing. doLevel1TestDesign?
			}else if("201".equals(temp.getKey())){
				//do what? yet nothing. doLevel1TestDesign?
			}else {
				RestAPITestCaseVO restAPITestCaseVO = generateLevel2TestCase(testSuite.getName(), temp.getKey(), temp.getValue());
				testSuite.addTestCase(restAPITestCaseVO);
			}
		}
	}
	
	private RestAPITestCaseVO generateLevel2TestCase(String targetName, String responseCode, RestAPIResponseVO response) {
		RestAPITestCaseVO generatedTestCase = new RestAPITestCaseVO();
		generatedTestCase.setDescription("해당 응답코드 발생 테스트");
		generatedTestCase.setExpectedResult("기대한 응답코드가 발생한다");
		generatedTestCase.setName(targetName+"_AllInput_200ok");
		
		if("400".equals(responseCode)) {
			generatedTestCase.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_400BADREQUEST);			
		}else if("404".equals(responseCode)) {
			generatedTestCase.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_404NOTFOUND);
		}else if("400".equals(responseCode)) {
			generatedTestCase.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_400BADREQUEST);
		}else if("400".equals(responseCode)) {
			generatedTestCase.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_400BADREQUEST);
		}else {
			generatedTestCase.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_400BADREQUEST);
		}
		generatedTestCase.setGeneratedTestLevel(2);
		return generatedTestCase;
	}
	
}
