package com.tag.common;

import java.io.File;

public class TestUtils {
	
	public static String getCurrentPath() {
		return new File("").getAbsolutePath();
	}

}
