package com.tag.analyzer;

import java.util.Map.Entry;

import com.tag.common.PropertiesPool;
import com.tag.common.TAGException;
import com.tag.restapi.spec.vo.ResponseInfo;
import com.tag.restapi.spec.vo.RestAPIInfo;
import com.tag.restapi.writer.RestAPITestCaseVO;
import com.tag.restapi.writer.RestAPITestSuiteVO;

/**
 *	Swagger Specification reference : <a href= "http://openapi-specification-visual-documentation.apihandyman.io">http://openapi-specification-visual-documentation.apihandyman.io</a></br> 
 *
 */
public class RestAPISpecAnalyzer implements ISpecAnalyzer {
	
	public RestAPITestSuiteVO analyze(RestAPIInfo restAPIInfo) throws TAGException{
		RestAPITestSuiteVO testSuite = new RestAPITestSuiteVO();
		analyzeTestCase(restAPIInfo, testSuite, PropertiesPool.getTestGenerateLevel());
		
		return testSuite;
	}
	
	/**
	 * level 1 -> 필수입력, 선택입력, 200 정상 응답
	 * level 2 -> Response codes
	 * level 3 -> Method type별 추가 테스트
	 * level 4 ->  각 입력 파라미터에 따른 TC
	 * level 5 -> API간 특성, 리소스의 특성 등을 반영한 테스트
	 * 
	 * @param restAPIInfo
	 * @param testDesignLevel
	 * @return
	 * @throws TAGException 
	 */
	public RestAPITestSuiteVO analyze(RestAPIInfo restAPIInfo, int testDesignLevel) throws TAGException{
		RestAPITestSuiteVO testSuite = new RestAPITestSuiteVO();
		analyzeTestCase(restAPIInfo, testSuite, testDesignLevel);
		return testSuite;
	}
	
	/**
	 * 
	 * @param restAPIInfo
	 * @param testSuiteVO
	 * @param testDesignLevel
	 * @throws TAGException
	 */
	private void analyzeTestCase(RestAPIInfo restAPIInfo, RestAPITestSuiteVO testSuiteVO, int testDesignLevel) throws TAGException {
		copyBasicInfo(restAPIInfo, testSuiteVO);
		switch (testDesignLevel) {
			case 1: 
				doLevel1TestDesign(restAPIInfo, testSuiteVO);
				break;
			case 2: 
				doLevel1TestDesign(restAPIInfo, testSuiteVO);
				doLevel2TestDesign(restAPIInfo, testSuiteVO);
				break;
			default: 
				throw new TAGException("Not yet implemented level- "+testDesignLevel);
		}
	}
	
	/**
	 * 
	 * @param restAPIInfo
	 * @param testSuiteVO
	 */
	private void copyBasicInfo(RestAPIInfo restAPIInfo, RestAPITestSuiteVO testSuiteVO) {
		testSuiteVO.setAcceptType(restAPIInfo.getProduces());
		testSuiteVO.setBaseURL(restAPIInfo.getHost());
		testSuiteVO.setDescription(restAPIInfo.getDescription());

		testSuiteVO.setMethodType(restAPIInfo.getMethod());
		testSuiteVO.setName(restAPIInfo.getApiName()+"Test");
		testSuiteVO.setPath(restAPIInfo.getApiPath());
//		testSuiteVO.setPort(restAPIInfo.get);
//		testSuiteVO.setInputParameters(inputParameters);
//		testSuiteVO.setRequestBody(restAPIInfo.getRequestBody());
//		testSuiteVO.setResponsesMap(restAPIInfo.getResponses());
		testSuiteVO.setResource(restAPIInfo.getApiTag());
		testSuiteVO.setTargetName(restAPIInfo.getApiName());
		testSuiteVO.setTargetPackage(restAPIInfo.getApiTag());
	}
			

	/**
	 * 기본 케이스를 생성하는 메소드. Input TestSute의 TestCase가 최초에는 비어있지만 분석 후에는 </br>
	 * 테스트 케이스 리스트가 생성된다	</br>
	 * 	</br> 
	 * @param testSuite
	 * @throws TAGException
	 */
	private void doLevel1TestDesign(RestAPIInfo restAPIInfo, RestAPITestSuiteVO testSuite) throws TAGException {
		//This will add two test cases
		RestAPITestCaseVO firstTestCaseVO = new RestAPITestCaseVO();
		firstTestCaseVO.setDescription("This is one of the basic tests only with mandatory input parameters. 선택 입력 값들이 디폴트 값으로 동작하는지 확인합니다");
		//TODO	201 등의 다른 정상 응답 코드를 쓴 경우의 텍스트 처리 
		firstTestCaseVO.setName(testSuite.getName()+"_MandatoryInput_ok");
		firstTestCaseVO.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_200OK);
		firstTestCaseVO.setGeneratedTestLevel(1);
		firstTestCaseVO.setExpectedResult("정상수행, 선택 입력 값들이 디폴트 값으로 동작하는지 확인합니다");
		testSuite.addTestCase(firstTestCaseVO);

		if(restAPIInfo.getParameters() !=null && restAPIInfo.getParameters().size() > 1) {
			RestAPITestCaseVO secondTestCaseVO = new RestAPITestCaseVO();
			secondTestCaseVO.setDescription("All input parameters tests( mandatory & optional parameters). 선택 입력 값들이 디폴트 값이 아닌 선택한 값으로 동작하는지 확인합니다");
			secondTestCaseVO.setExpectedResult("정상수행, 선택 입력 값들이 디폴트 값이 아닌 선택한 값으로 동작한다"); 
			secondTestCaseVO.setName(testSuite.getName()+"_AllInput_ok");
			secondTestCaseVO.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_200OK);
			secondTestCaseVO.setGeneratedTestLevel(1);
			testSuite.addTestCase(secondTestCaseVO);
		}
	}
	
	/**
	 * Response에 따른 테스트 코드 
	 * @param restAPIInfo
	 * @param testSuite
	 * @throws TAGException
	 */
	private void doLevel2TestDesign(RestAPIInfo restAPIInfo, RestAPITestSuiteVO testSuite) throws TAGException {
		for(Entry<String, ResponseInfo>  temp : restAPIInfo.getResponses().entrySet()) {
			if("200".equals(temp.getKey())){
				//do what? yet nothing. doLevel1TestDesign?
			}else if("201".equals(temp.getKey())){
				//do what? yet nothing. doLevel1TestDesign?
			}else {
				RestAPITestCaseVO restAPITestCaseVO = generateLevel2TestCase(restAPIInfo.getApiName(), temp.getKey(), temp.getValue());
				if(restAPITestCaseVO!= null) {
					testSuite.addTestCase(restAPITestCaseVO);
				}
			}
		}
	}
	
	private RestAPITestCaseVO generateLevel2TestCase(String targetName, String responseCode, ResponseInfo response) {
		RestAPITestCaseVO generatedTestCase = new RestAPITestCaseVO();
		generatedTestCase.setDescription("해당 응답코드 발생 테스트");
		generatedTestCase.setExpectedResult("기대한 응답코드가 발생한다");
		generatedTestCase.setGeneratedTestLevel(2);
		if("400".equals(responseCode)) {
			generatedTestCase.setName(targetName+"_400check");
			generatedTestCase.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_400BADREQUEST);			
		}else if("404".equals(responseCode)) {
			generatedTestCase.setName(targetName+"_404NotFound");
			generatedTestCase.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_404NOTFOUND);
//		}else if("400".equals(responseCode)) {
//			generatedTestCase.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_400BADREQUEST);
		}else if("500".equals(responseCode)) {
			//SKIP
			generatedTestCase = null;
//			generatedTestCase.setTestCaseType(RestAPITestCaseVO.RESTTCTYPE_400BADREQUEST);
		}else {
			//TODO
			generatedTestCase = null;
		}
		
		return generatedTestCase;
	}
	
}
