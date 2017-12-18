package com.tag.data.vo;

import java.util.ArrayList;
import java.util.List;

public class TestSuiteVO {
	String name = null;
	String targetPackage;
	String targetName;
	String description;
//	int targetType;
//	String inputSourceType;
//	String inputSourcePath
	
	private List<TestCaseVO> testCaseList;

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public List<TestCaseVO> getTestCaseList() {
		return testCaseList;
	}

	public void setTestCaseList(List<TestCaseVO> testCaseList) {
		this.testCaseList = testCaseList;
	}
	
	public void addTestCase(TestCaseVO testCaseVO) {
		if(this.testCaseList == null) {
			this.testCaseList = new ArrayList<>();
		}
		this.testCaseList.add(testCaseVO);
	}

	public String getTargetPackage() {
		return targetPackage;
	}

	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
