package com.tag.common;

/**
 * 
 *	This is for separating the exceptions involved testing from the target's logical exception 
 */
public class TAGException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6515943846099646022L;

//	public GUITestException() {
//		super();
//	}

	public TAGException(String message) {
		super(message);
	}

	public TAGException(String message, Throwable cause) {
		super(message, cause);
	}

}
