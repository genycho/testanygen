package com.tag.common;

import java.io.File;

public class TagUtils {
	
	public static String getCurrentPath() {
		return new File("").getAbsolutePath();
	}

}
