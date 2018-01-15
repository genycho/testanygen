package com.tag.restapi.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tag.common.PropertiesPool;
import com.tag.common.TAGException;
import com.tag.data.vo.TestCaseVO;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

public class FreeMarkerGenerator {
	final Logger logger = LoggerFactory.getLogger(FreeMarkerGenerator.class);
	Map<String, RestAPIParameterVO> checkBodyParameterGen = new HashMap<>();
	
	/**
	 * 
	 * @param testSuiteVO
	 * @return	generated File path(absolute path), if not generated, return ""(not null)
	 * @throws TAGException 
	 */
	public String generateRestAssured(RestAPITestSuiteVO testSuiteVO) throws TAGException {
		massageForJUnitWriting(testSuiteVO);
		
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
		StringBuffer sb = new StringBuffer();
		
		try {
			cfg.setDirectoryForTemplateLoading(new File(PropertiesPool.getTemplatePath()));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			Template template = cfg.getTemplate("restassured_template.ftl");

			//Map에 넣어 FreeMarker에 전달
			Map<String, Object> map = new HashMap<>();
            map.put("testSuiteVO", testSuiteVO);
            
			// Console output
			Writer out = new OutputStreamWriter(System.out);
			template.process(map, out);
			out.flush();

			// File output
			String outputFilePath = PropertiesPool.getOutputPath() + "/"
					+ ((testSuiteVO.getTargetName().endsWith("Test")) ? testSuiteVO.getTargetName()
							: testSuiteVO.getTargetName() + "Test.java");
			Writer file = new FileWriter(
					new File(outputFilePath));
			
			template.process(map, file);
			file.flush();
			file.close();
			
			for(String bodyParamName : this.checkBodyParameterGen.keySet()) {
				this.generateDataFactory(bodyParamName+"DataFactory", (RestAPIParameterVO) checkBodyParameterGen.get(bodyParamName));
			}
			
			sb.append(outputFilePath);
		}catch(TemplateNotFoundException e) {
			throw new TAGException("There is no template in " + PropertiesPool.getTemplatePath(),e);
		} catch (TemplateException e) {
			throw new TAGException("Freemarker template error. check the template(restassured_template.ftl) exists in template path-"+PropertiesPool.getTemplatePath(),e);
		} catch (IOException e) {
			throw new TAGException("File IOException to "+PropertiesPool.getOutputPath(), e);
		}
		return sb.toString();
	}
	
	public String generateDataFactory(String fileName, RestAPIParameterVO bodyParameter) throws TAGException {
		fileName = Character.toUpperCase(fileName.charAt(0)) + fileName.substring(1);
		
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
		StringBuffer sb = new StringBuffer();
		
		try {
			cfg.setDirectoryForTemplateLoading(new File(PropertiesPool.getTemplatePath()));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			Template template = cfg.getTemplate("restassured_testdata_template.ftl");

			//Map에 넣어 FreeMarker에 전달
			Map<String, Object> map = new HashMap<>();
			Map<String, RestAPIParameterVO> parameterMap = new HashMap<>();
			map.put("fileName", fileName);
            map.put("bodyName", bodyParameter.getName());
            map.put("description", bodyParameter.getDescription());
            map.put("paramMap", parameterMap);
			
            if("body".equalsIgnoreCase(bodyParameter.getInType()) && bodyParameter.getSubParameterList() !=null) {
            	for(RestAPIParameterVO subParameter : bodyParameter.getSubParameterList()) {
            		if(subParameter!=null && subParameter.getName() != null) {
                		parameterMap.put(subParameter.getName(), subParameter);
            		}else {
            			logger.warn("There is no proper sub parameter info. parent parameter definition is - {} ", bodyParameter.getName());
            		}
            	}
            }
			Writer out = new OutputStreamWriter(System.out);
			template.process(map, out);
			out.flush();

			// File output
			String outputFilePath = PropertiesPool.getOutputPath() + "/"+fileName+".java";
			Writer file = new FileWriter(new File(outputFilePath));
			template.process(map, file);
			file.flush();
			file.close();
			sb.append(outputFilePath);
		}catch(TemplateNotFoundException e) {
			throw new TAGException("There is no template in " + PropertiesPool.getTemplatePath(),e);
		} catch (TemplateException e) {
			throw new TAGException("Freemarker template error. check the template(restassured_testdata_template.ftl) in template path-"+PropertiesPool.getTemplatePath(),e);
		} catch (IOException e) {
			throw new TAGException("File IOException to "+PropertiesPool.getOutputPath(), e);
		}
		return sb.toString();
	}
	
	private void massageForJUnitWriting(RestAPITestSuiteVO testSuiteVO) {
		if(testSuiteVO == null) {
			return;
		}
		testSuiteVO.setName((testSuiteVO.getName() ==null)? "" : testSuiteVO.getName().replaceAll(" ", ""));
		testSuiteVO.setTargetName((testSuiteVO.getTargetName() ==null)? "restapi.test" : testSuiteVO.getTargetName().replaceAll(" ", ""));
		
		if(testSuiteVO.getTestCaseList() != null) {
			for(TestCaseVO temp : testSuiteVO.getTestCaseList()) {
				this.massageForJUnitWriting((RestAPITestCaseVO) temp);
			}
		}
	}
	
	private void massageForJUnitWriting(RestAPITestCaseVO testCaseVO) {
		testCaseVO.setName( (testCaseVO.getName() == null)?  "": testCaseVO.getName().replaceAll(" ", ""));
		if(testCaseVO.getInputParameters() != null) {
			for(RestAPIParameterVO inputParameter : testCaseVO.getInputParameters()) {
				massageForJUnitWriting(inputParameter);
			}
		}
	}
	
	private void massageForJUnitWriting(RestAPIParameterVO restAPIParameterVO) {
		restAPIParameterVO.setName( (restAPIParameterVO.getName() == null)?  "temp": restAPIParameterVO.getName().replaceAll(" ", ""));
		restAPIParameterVO.setDefaultValue( (restAPIParameterVO.getDefaultValue() == null)?  "": restAPIParameterVO.getDefaultValue() );
		restAPIParameterVO.setRequired( (restAPIParameterVO.getRequired() == null)? "true": restAPIParameterVO.getRequired() );
		//THINKME	입력값 타입 정보가 없으면 query 파라미터로 처리???  
		restAPIParameterVO.setInType( (restAPIParameterVO.getInType() == null)? "query": restAPIParameterVO.getInType() );
		restAPIParameterVO.setDescription( (restAPIParameterVO.getDescription() == null)? "": restAPIParameterVO.getDescription() );
		restAPIParameterVO.setValue( (restAPIParameterVO.getDefaultValue() == null)?  getDefaultValue(restAPIParameterVO.getType()): restAPIParameterVO.getDefaultValue() );
		
		/* body 파라미터의 경우 별도 파일을 생성하기 위해 map에다 후보로 추가 함 */
		if("body".equalsIgnoreCase(restAPIParameterVO.getInType())) {
			//기존에 같은 이름이 존재하면 덮어쓰기. 막아야 할까?? 
			this.checkBodyParameterGen.put(restAPIParameterVO.getName(), restAPIParameterVO);
		}
	}
	
	/**
	 * TODO 나중에 기능 강화  
	 * @param type
	 * @return
	 */
	private String getDefaultValue(String type) {
		if("int".equalsIgnoreCase(type) || ("integer".equalsIgnoreCase(type) || type.contains("int"))) {
			return "1";
		}else if("string".equalsIgnoreCase(type) || type.contains("char")) {
			return "\"string_value\"";
		}else if("".equalsIgnoreCase(type)) {
			return "???";
		}else {
			return "\"default_value\"";
		}
	}
}
