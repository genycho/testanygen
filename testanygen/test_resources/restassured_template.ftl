package eoe.resttest.appmanager;

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

public class ${testSuiteVO.targetName}Test extends MyCustomTestCase{
	String sysAdminID = "sysadmin";
	
	public  ${testSuiteVO.targetName}Test() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
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