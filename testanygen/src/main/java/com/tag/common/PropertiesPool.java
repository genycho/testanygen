package com.tag.common;

public class PropertiesPool {
	
	private static String outputPath = "";
	private static String templatePath = "";
	
	private static int testGenerateLevel = 1;
	
	public static String getOutputPath() {
		return outputPath;
	}

	public static String getTemplatePath() {
		return templatePath;
	}

	public static void setTemplatePath(String templatePath) {
		PropertiesPool.templatePath = templatePath;
	}

	public static void setOutputPath(String outputPath) {
		PropertiesPool.outputPath = outputPath;
	}

	public static int getTestGenerateLevel() {
		return testGenerateLevel;
	}

	public static void setTestGenerateLevel(int testGenerateLevel) {
		PropertiesPool.testGenerateLevel = testGenerateLevel;
	}

}
