package com.tag.common;

/**
 * 
 *	This is for separating the exceptions involved testing from the target's logical exception
 */
public class TAGRunimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8263566215387270242L;

//	  public GUITestRunimeException() {
//	        super();
//	    }

	    public TAGRunimeException(String message) {
	        super(message);
	    }

	    public TAGRunimeException(String message, Throwable cause) {
	        super(message, cause);
	    }

}
