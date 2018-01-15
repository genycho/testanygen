package ${testSuiteVO.targetPackage};

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Auto-generated rest-assured Test class</br>
 * ${testSuiteVO.description}
 * @author TestAnyGen(v0.1)
 *
 */
public class ${testSuiteVO.targetName}Test extends MyCustomTestCase{
	String baseUrl = "${testSuiteVO.baseURL}";
	String apiPath = "${testSuiteVO.path}";
<#if testSuiteVO.methodType = "get">
	//TODO	
	String sharedKeyData = "aKeyToSelect";
<#elseif testSuiteVO.methodType = "post">
	//TODO
	String generatedDataToDelete = "aKeyToCleanseAfterTest";
<#elseif testSuiteVO.methodType = "put">
	//TODO	
	String sharedKeyData = "aKeyToModify";
<#elseif testSuiteVO.methodType = "delete">
	//TODO	
	String sharedKeyData = "aKeyToDelete";
<#else>
	//NotDefined RestAPI Method type : ${testSuiteVO.methodType}
</#if>
	
	public ${testSuiteVO.targetName}Test() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		//authorized request spec maybe needed.
		//test data preparation can be needed.
	}

	@After
	public void tearDown() throws Exception {
	}
	
	<#if testSuiteVO.getTestCaseList()??>
	<#list testSuiteVO.getTestCaseList() as testCaseVO>
	<#include "/restassured_positive_particle.ftl">
	</#list>
	<#else>
	//there is no testcase anlyzed!!
	</#if>
	
}