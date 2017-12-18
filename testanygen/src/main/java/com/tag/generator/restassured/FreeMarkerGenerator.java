package com.tag.generator.restassured;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.tag.common.PropertiesPool;
import com.tag.common.TAGException;
import com.tag.data.vo.TestSuiteVO;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerGenerator {

	/**
	 * 
	 * @param testSuiteVO
	 * @return	generated File path(absolute path), if not generated, return ""(not null)
	 * @throws TAGException 
	 */
	public String generator(TestSuiteVO testSuiteVO) throws TAGException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
		StringBuffer sb = new StringBuffer();
		
		try {
			cfg.setDirectoryForTemplateLoading(new File(PropertiesPool.getTemplatePath()));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			Template template = cfg.getTemplate("restassured_template.ftl");

			Map<String, Object> map = new HashMap<>();
            map.put("testSuiteVO", testSuiteVO);
            
			// Console output
			Writer out = new OutputStreamWriter(System.out);
			template.process(map, out);
			out.flush();

			// File output
			String outputFilePath = PropertiesPool.getOutputPath() + "/" + testSuiteVO.getTargetName() + "Test.java";
			Writer file = new FileWriter(
					new File(outputFilePath));
			
			template.process(map, file);
			file.flush();
			file.close();
			sb.append(outputFilePath);
		} catch (IOException e) {
			throw new TAGException("File IOException to "+PropertiesPool.getOutputPath(), e);
		} catch (TemplateException e) {
			throw new TAGException("Freemarker template error- restassured_template.ftl",e);
		}
		return sb.toString();
	}
}
