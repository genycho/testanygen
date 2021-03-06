package com.tag.restapi.spec.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tag.common.TAGException;
import com.tag.restapi.spec.vo.ParameterInfo;
import com.tag.restapi.spec.vo.RequestBody;
import com.tag.restapi.spec.vo.ResponseInfo;
import com.tag.restapi.spec.vo.RestAPIInfo;

import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.parser.SwaggerParser;

/**
 * 
 * Swagger Specification reference : <a href= "http://openapi-specification-visual-documentation.apihandyman.io">http://openapi-specification-visual-documentation.apihandyman.io</a></br>
 * This internally call {@link  io.swagger.parser.SwaggerParser#read(String)} 
 *
 */
public class SwaggerSpecParser implements ISpecParser{
	final Logger logger = LoggerFactory.getLogger(SwaggerSpecParser.class);
	
	private Map<String, RequestBody> bodyModelMap = new HashMap<>(); 
	
	public  List<RestAPIInfo> parse(String specFilePath) throws TAGException {
		return parse(parseSpecFile(specFilePath));
	}
	
	public Swagger parseSpecFile(String specFilePath) {
		SwaggerParser parser = new SwaggerParser(); 
		return parser.read(specFilePath); 
	}
	
	/*
	 * Swagger 스펙의 정보 데이터 구조는
	 * http://openapi-specification-visual-documentation.apihandyman.io 참조 함
	 */
	public List<RestAPIInfo> parse(Swagger swaggerInfo) throws TAGException {
		//TODO	각 오브젝트들,응답바디, 에러바디 에 대한 정의가 담겨 있으며 각 API 명세에서 상대경로로 참조됨 ($ref:'#/definitions/Activities')
		//TODO	글로벌, 공통 응답코드는 같이 작성되어 있지 않음. 개별 API에서 이 정보를 알 필요가 있을지... 
//		Map<String, Model> modelMap = swaggerInfo.getDefinitions();// -> 테스트 데이터 생성에 관련된 거면 XXModelFactory 형태의 코드로 생성되도록 처리한다

		/** 전체에 대한 title, description, termsOfService, contact, license, version,... */
		//TODO	검토
//		swaggerInfo.getResponses();//An object to hold responses that can be used across operations. This property does not define global responses for all operations
//		swaggerInfo.getParameters();//An object to hold parameters that can be used across operations. This property does not define global parameters for all operations
//		swaggerInfo.getSchemes();		// https, http,
		if(!"2.0".equals(swaggerInfo.getSwagger())){
			throw new TAGException("Not supported SPEC version - "+swaggerInfo.getSwagger());
		}
		Map<String, Path> pathModelMap = swaggerInfo.getPaths();
		String baseUrl = getBaseURL(swaggerInfo);
		
		List<RestAPIInfo> restAPIInfoList = new ArrayList<>();
		int countMethods = 0;
		
		//1. 개별 API에 대한 분석 결과 가져오기 
		for (String aPath : pathModelMap.keySet()) {
			countMethods = 0;

			if (pathModelMap.get(aPath).getHead() != null) {
				// TODO 헤더에 대한 처리는 어떻게 해야할까...
			}
			if (pathModelMap.get(aPath).getGet() != null) {
				countMethods++;
				restAPIInfoList.add(getRestAPIInfo(baseUrl, aPath, "get", pathModelMap.get(aPath).getGet()));
			} 
			
			if (pathModelMap.get(aPath).getPost() != null) {
				countMethods++;
				restAPIInfoList.add(getRestAPIInfo(baseUrl, aPath, "post", pathModelMap.get(aPath).getPost()));
			} 
			
			if (pathModelMap.get(aPath).getPut() != null) {
				countMethods++;
				restAPIInfoList.add(getRestAPIInfo(baseUrl, aPath, "put", pathModelMap.get(aPath).getPut()));
			} 

			if (pathModelMap.get(aPath).getDelete() != null) {
				countMethods++;
				restAPIInfoList.add(getRestAPIInfo(baseUrl, aPath, "delete", pathModelMap.get(aPath).getDelete()));
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
		//TODO	2. 데이터 모델에 대한 분석 결과 가져오기
		;
		for(String modelKey : swaggerInfo.getDefinitions().keySet()) {
			this.bodyModelMap.put(modelKey, comvertModelToRequestBody(modelKey, swaggerInfo.getDefinitions().get(modelKey)));
		}
		return restAPIInfoList;
	}
	
	private String generateAPIName(final String aPath, final String methodType, final Operation operation) {
		if(operation.getOperationId() != null) {
			return operation.getOperationId();
		}
		
		if(operation.getSummary() != null ) {
			return operation.getSummary();
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(methodType);
		sb.append(aPath.replace("/","_"));
		return sb.toString();
	}
	
	/**	analyze api information from swagger's OPERATION model	
	 * @throws TAGException */
	private RestAPIInfo getRestAPIInfo(String baseUrl, String aPath, String methodType, Operation operation) throws TAGException{
		RestAPIInfo restAPIInfo = new RestAPIInfo();//apiName - methodType, aPath에서 / 제거하고?
		restAPIInfo.setApiName(generateAPIName(aPath, methodType, operation));
		
		restAPIInfo.setBaseURL(baseUrl);
		
		if(operation.getParameters() != null) {
			for(Parameter inputParameter : operation.getParameters()) {
				if("query".equals(inputParameter.getIn())){
					restAPIInfo.addParameter(getQueryParameter((QueryParameter) inputParameter));
				}else if("path".equals(inputParameter.getIn())){
					restAPIInfo.addParameter(getPathParameter((PathParameter) inputParameter));
				}else if("body".equals(inputParameter.getIn())){
					//FIXME
					BodyParameter temp = (BodyParameter)inputParameter;
					restAPIInfo.setTempRequestBody(temp.getSchema().getReference());
					restAPIInfo.addParameter(getBodyParameter((BodyParameter) inputParameter));
				}else {
					throw new TAGException("Not yet supporting type!! - " + inputParameter.getIn());
				}
			}
		}
		restAPIInfo.setMethod(methodType);
		restAPIInfo.setApiPath(aPath);
		//TODO	Description 정리 
		restAPIInfo.setDescription(operation.getDescription() + "\n"+operation.getSummary());
		restAPIInfo.setResponses(getResponseInfo(operation.getResponses()));
		restAPIInfo.setApiTag(getResourceGroup(operation.getTags()));
		return restAPIInfo;
	}
	
	private ParameterInfo getQueryParameter(QueryParameter inputParameter) {
		ParameterInfo parameterInfo = new ParameterInfo();
		parameterInfo.setDescription(inputParameter.getDescription());
		parameterInfo.setFormat(inputParameter.getFormat());
		parameterInfo.setInType(inputParameter.getIn());
		parameterInfo.setName(inputParameter.getName());
		parameterInfo.setRequired(String.valueOf(inputParameter.getRequired()));
		parameterInfo.setType(inputParameter.getType());
		return parameterInfo;
	}
	
	private ParameterInfo getPathParameter(PathParameter inputParameter) {
		ParameterInfo parameterInfo = new ParameterInfo();
		parameterInfo.setDescription(inputParameter.getDescription());
		parameterInfo.setFormat(inputParameter.getFormat());
		parameterInfo.setInType(inputParameter.getIn());
		parameterInfo.setName(inputParameter.getName());
		parameterInfo.setRequired(String.valueOf(inputParameter.getRequired()));
		parameterInfo.setType(inputParameter.getType());
		return parameterInfo;
	}
	
	private ParameterInfo getBodyParameter(BodyParameter inputParameter) {
		ParameterInfo parameterInfo = new ParameterInfo();
		parameterInfo.setDescription(inputParameter.getDescription());
		parameterInfo.setInType(inputParameter.getIn());
		parameterInfo.setName(inputParameter.getName());
		parameterInfo.setRequired(String.valueOf(inputParameter.getRequired()));
//		parameterInfo.setType(inputParameter.getVendorExtensions().);
		parameterInfo.setRefBodyModelKey(inputParameter.getSchema().getReference());
		return parameterInfo;
	}

	/**	get API's parent resource group hint from... */
	private String getResourceGroup(List<String> tags) {
		if(tags!=null && tags.size() >0) {
			return tags.get(0);
		}
		return null;
	}

	/**	get responses's type - eg)application/xml, application/json*/
	private String getAcceptType(List<String> produces) {
		if(produces!=null && produces.size() >0) {
			return produces.get(0);
		}
		return null;
	}


	private String getBaseURL(Swagger swaggerInfo) {
		StringBuffer sb = new StringBuffer();
		if(swaggerInfo.getSchemes() !=null && swaggerInfo.getSchemes().size() > 0) {
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
	
	/**
	 * #/definitions/
	 * @param model
	 * @return
	 */
	private RequestBody comvertModelToRequestBody(String modelKey, Model model) {
		RequestBody tempBody = new RequestBody();
		tempBody.setModelKey(modelKey);
//		for(String modelKey : model.getProperties().keySet()) {
//			tempBody.
//		}
		return tempBody;
	}

	private Map<String, ResponseInfo> getResponseInfo(Map<String, Response> responses) {
		// TODO
		// responses TreeMap이 2개. 200키, value에 Response 객체가 들어있고,
		if(responses == null) {
			return null;
		}
		Map<String, ResponseInfo> responsesMap = new HashMap<String, ResponseInfo>();
//		for(Entry<String, Response> swaggerResponse : responses.entrySet()) {
		for(String aKey : responses.keySet()) {
			Response response = responses.get(aKey);
			ResponseInfo responseVO = new ResponseInfo();
			responseVO.setStatusCode(aKey);
			responseVO.setDescription(response.getDescription());
			//TODO
			if(response.getSchema() != null) {
				responseVO.setTempResponseValue(response.getSchema().getType() + "..."+response.getSchema().getName());
			}
			
			responsesMap.put(aKey, responseVO);
//			Property responseProperty = responses.get(aKey).getSchema();
//			StringBuffer sb = new StringBuffer();
//			sb.append(responseProperty.getType());
//			sb.append(responseProperty.get);
//			sb.append(responseProperty.getType());
//			sb.append(responseProperty.getType());
//			sb.append(responseProperty.getType());
		}
		return responsesMap;
	}
}
