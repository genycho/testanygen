package com.tag.restapi.writer;

/**
 *	Sample 
 *<pre>
 *Profile:
    type: object
    properties:
      first_name:
        type: string
        description: First name of the Uber user.
      last_name:
        type: string
        description: Last name of the Uber user.
      email:
        type: string
        description: Email address of the Uber user
      picture:
        type: string
        description: Image URL of the Uber user.
      promo_code:
        type: string
        description: Promo code of the Uber user. 
 *</pre>
 */
public class RestAPIObjectParameter {
//	String type;
	String[] names;
	String[]	types;
	String[] descriptions;

	public RestAPIObjectParameter(int paramCount) {
		//TODO
	}
	
	public void addObjectParameter(String name, String type, String description) {
		
	}
	
	
}
