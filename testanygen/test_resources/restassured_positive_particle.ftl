	@Test
	public void test${testSuiteVO.targetName}_${testCaseVO.name}() throws Exception {
		
		JSONObject inputJSON = null;//TODO	TestDataPrepare
		
		RequestSpecification requestSpec = getDefaultBasicRequestSpec("Authrization", 
				inputJSON.toJSONString());
		requestSpec.log().all();
		
		Response response = RestAssured
		.given()
			.spec(requestSpec)
		.expect()
			.statusCode(201)
			.log().all()
		.when()
			.post(getApiPath())
			.andReturn();
		
		/* 3. response printing & detail assertions */
		JsonPath jsonResponse = new JsonPath(response.asString());
	}
