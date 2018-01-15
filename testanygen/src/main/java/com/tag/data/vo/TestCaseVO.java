package com.tag.data.vo;

import java.util.List;

public class TestCaseVO {
	/** */
	public static final String TCTYPE_POSITIVE = "POS";
	public static final String TCTYPE_NEGATIVE = "NEG";
	public static final String TCTYPE_EXCEPTION = "EXC";
	
	public static final String TCRESULTCODE_PASS = "pass";
	public static final String TCRESULTCODE_FAIL = "fail";
	public static final String TCRESULTCODE_BLOCKED = "blocked";
	public static final String TCRESULTCODE_NA = "na";
	
	private String name;
	private 	String description;
	private String testCaseType;
	private int generatedTestLevel = -1;

	private String expectedResult;
	private String actualResult;
	
	private List<TestStepVO> testStepList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTestCaseType() {
		return testCaseType;
	}
	public void setTestCaseType(String testCaseType) {
		this.testCaseType = testCaseType;
	}
	public String getExpectedResult() {
		return expectedResult;
	}
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}
	public String getActualResult() {
		return actualResult;
	}
	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}
	public int getGeneratedTestLevel() {
		return generatedTestLevel;
	}
	public void setGeneratedTestLevel(int generatedTestLevel) {
		this.generatedTestLevel = generatedTestLevel;
	}
	public List<TestStepVO> getTestStepList() {
		return testStepList;
	}
	public void setTestStepList(List<TestStepVO> testStepList) {
		this.testStepList = testStepList;
	}
	
//	String preCondition;
//	String postCondition;
	
	
	

}
