package com.tag.restapi.spec.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tag.common.TAGException;
import com.tag.generator.restassured.RestAPIParameterVO;
import com.tag.generator.restassured.RestAPIResponseVO;
import com.tag.generator.restassured.RestAPITestSuiteVO;
import com.tag.restapi.info.vo.RestAPIInfo;

import io.swagger.annotations.Tag;
import io.swagger.models.Info;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;

/**
 * 
 * Swagger Specification reference : <a href= "http://openapi-specification-visual-documentation.apihandyman.io">http://openapi-specification-visual-documentation.apihandyman.io</a></br>
 * This internally call {@link  io.swagger.parser.SwaggerParser#read(String)} 
 *
 */
public class SwaggerSpecParser implements ISpecParser{
	Swagger swaggerSpecInfo = null;
	
	public Swagger parse(String yamlFilePath) {
		SwaggerParser parser = new SwaggerParser(); 
		return swaggerSpecInfo = parser.read(yamlFilePath); 
	}
	
	public List<RestAPIInfo> parse(Swagger swaggerInfo) {
//		여기서 열심히 꺼내서 열심히 데이터 담기

		List<RestAPIInfo> apiList = new ArrayList<>();
		
		String basePath = swaggerInfo.getBasePath();
		List<String> cosumeList = swaggerInfo.getConsumes();//
		Map<String, Model> definitions = swaggerInfo.getDefinitions();
		String host = swaggerInfo.getHost();
		List<io.swagger.models.Tag> tagList = swaggerInfo.getTags();
		Info docInfo = swaggerInfo.getInfo();
		//1. 
		
		
		//2. 
		
		
		//3. 
		
		
		//4. 
		
		
		//5. 
		
		
		
		return apiList; 
	}
	

	public Swagger getSwaggerSpecInfo() {
		return swaggerSpecInfo;
	}
	
	/*
	 * Swagger 스펙의 정보 데이터 구조는
	 * http://openapi-specification-visual-documentation.apihandyman.io 참조 함
	 */
	public List<RestAPITestSuiteVO> analyze(Swagger swaggerInfo) throws TAGException {
		//TODO	각 오브젝트들,응답바디, 에러바디 에 대한 정의가 담겨 있으며 각 API 명세에서 상대경로로 참조됨 ($ref:'#/definitions/Activities')	
//		Map<String, Model> modelMap = swaggerInfo.getDefinitions();// -> 테스트 데이터 생성에 관련된 거면 XXModelFactory 형태의 코드로 생성되도록 처리한다

		/** 전체에 대한 title, description, termsOfService, contact, license, version,... */
		//TODO	검토
//		swaggerInfo.getResponses();//An object to hold responses that can be used across operations. This property does not define global responses for all operations
//		swaggerInfo.getParameters();//An object to hold parameters that can be used across operations. This property does not define global parameters for all operations
//		swaggerInfo.getSchemes();		// https, http,
		
		Map<String, Path> pathModelMap = swaggerInfo.getPaths();
		String baseUrl = getBaseURL(swaggerInfo);
		
		List<RestAPITestSuiteVO> testSuiteList = new ArrayList<>();
		int countMethods = 0;
		for (String aPath : pathModelMap.keySet()) {
			countMethods = 0;

			if (pathModelMap.get(aPath).getHead() != null) {
				// TODO 헤더에 대한 처리는 어떻게 해야할까...
			}
			if (pathModelMap.get(aPath).getGet() != null) {
				countMethods++;
				testSuiteList.add(getOperationTestSuite(baseUrl, aPath, "get", pathModelMap.get(aPath).getGet()));
			} 
			
			if (pathModelMap.get(aPath).getPost() != null) {
				countMethods++;
				testSuiteList.add(getOperationTestSuite(baseUrl, aPath, "post", pathModelMap.get(aPath).getPost()));
			} 
			
			if (pathModelMap.get(aPath).getPut() != null) {
				countMethods++;
				testSuiteList.add(getOperationTestSuite(baseUrl, aPath, "put", pathModelMap.get(aPath).getPut()));
			} 

			if (pathModelMap.get(aPath).getDelete() != null) {
				countMethods++;
				testSuiteList.add(getOperationTestSuite(baseUrl, aPath, "delete", pathModelMap.get(aPath).getDelete()));
			} 
			
			if (pathModelMap.get(aPath).getPatch() != null) {
				countMethods++;
//				testSuiteList.add(getOperationTestSuite(baseUrl, aPath, "patch", pathModelMap.get(aPath).getPatch()));
				throw new TAGException("Patch operation is not yet supported.");
			}
			
			if(countMethods<=0) {
				throw new TAGException("There is no any methods for the path: "+aPath);
			}
		}
		return testSuiteList;
	}
	
	private RestAPITestSuiteVO getOperationTestSuite(String baseUrl, String aPath, String methodType, Operation operation){
		RestAPITestSuiteVO testSuiteVO = new RestAPITestSuiteVO();
		testSuiteVO.setBaseURL(baseUrl);
		testSuiteVO.setInputParameters(getInputParameterInfo(operation.getParameters()));
		testSuiteVO.setMethodType(methodType);
		testSuiteVO.setPath(aPath);
		//TODO	Description 정리 
		testSuiteVO.setDescription(operation.getDescription() + "\n"+operation.getSummary());
		testSuiteVO.setResponsesMap(getResponseInfo(operation.getResponses()));
		testSuiteVO.setName((operation.getOperationId()==null?operation.getSummary():operation.getOperationId()));
		testSuiteVO.setTargetName(testSuiteVO.getName());
		testSuiteVO.setTargetPackage(getResourceGroup(operation.getTags()));
		testSuiteVO.setAcceptType(getAccepType(operation.getProduces()));
		return testSuiteVO;
	}
	
	private String getResourceGroup(List<String> tags) {
		if(tags!=null && tags.size() >0) {
			return tags.get(0);
		}
		return null;
	}

	private String getAccepType(List<String> produces) {
		if(produces!=null && produces.size() >0) {
			return produces.get(0);
		}
		return null;
	}


	private String getBaseURL(Swagger swaggerInfo) {
		StringBuffer sb = new StringBuffer();
		if(swaggerInfo.getSchemes() !=null && swaggerInfo.getSchemes().size() >0) {
			//TODO	여러 건인 경우 처리
			sb.append(swaggerInfo.getSchemes().get(0).toString().toLowerCase());
			sb.append("://");
		}
		if(swaggerInfo.getHost() !=null) {
			sb.append(swaggerInfo.getHost());
		}
		
		if(swaggerInfo.getBasePath() !=null) {
			sb.append(swaggerInfo.getBasePath());
		}
		
		return sb.toString();
	}

	private Map<String, RestAPIResponseVO> getResponseInfo(Map<String, Response> responses) {
		// TODO
		// responses TreeMap이 2개. 200키, value에 Response 객체가 들어있고,
		if(responses == null) {
			return null;
		}
		Map<String, RestAPIResponseVO> responsesMap = new HashMap();
		for(Entry<String, Response> swaggerResponse : responses.entrySet()) {
			Response response = swaggerResponse.getValue();
//			response.getDescription();
//			response.getExamples();
			RestAPIResponseVO responseVO = new RestAPIResponseVO();
			responseVO.setResponseCode(swaggerResponse.getKey());
			responseVO.setDescription(response.getDescription());
			responsesMap.put(swaggerResponse.getKey(), responseVO);
		}
		return responsesMap;
	}

	private List<RestAPIParameterVO> getInputParameterInfo(List<Parameter> list) {
		//TODO
		if(list == null) {
			return null;
		}
		List<RestAPIParameterVO> parameterList = new ArrayList<>();
		for(Parameter swaggerParameter : list) {
			RestAPIParameterVO parameterVO = new RestAPIParameterVO();
			parameterVO.setName(swaggerParameter.getName());
			parameterVO.setRequired(String.valueOf(swaggerParameter.getRequired()));
			parameterVO.setDescription(swaggerParameter.getDescription());
			if("query".equals(swaggerParameter.getIn())){
				//TODO, FIXME
				//query
				//header
				//path
				//formData
				//body
				parameterVO.setCategory("request::query");
//				parameterVO.setType(swaggerParameter.);
//				parameterVO.setFormat(format);
			}
//			parameterVO.setDefaultValue(defaultValue);
//			parameterVO.setValue(value);
			parameterList.add(parameterVO);
		}
		return parameterList;
	}

	/**
	 * /products, get:
	 * summary: Product Types description: |~
	 * @param methodType
	 * @param apiPath
	 * @return
	 */
	private RestAPITestSuiteVO getAPIInfo(String apiPath, String methodType, Path anApiPath) {
		// anApiPath.
		RestAPITestSuiteVO restAPITestSuiteVO = new RestAPITestSuiteVO();

		// restAPITestSuiteVO.setDescription(description);
		// restAPITestSuiteVO.setExpectedResult(expectedResult);
		// restAPITestSuiteVO.setInputParameters(inputParameters);
		// restAPITestSuiteVO.setMethodType(methodType);
		// restAPITestSuiteVO.setName(name);
		// restAPITestSuiteVO.setPath(path);
		// restAPITestSuiteVO.setPort(port);
		// restAPITestSuiteVO.setTestCaseType(testCaseType); //Not yet

		return restAPITestSuiteVO;
	}
	
}
