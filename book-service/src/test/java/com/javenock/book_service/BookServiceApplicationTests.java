package com.javenock.book_service;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.servlet.Filter;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class BookServiceApplicationTests {

	@Value("${local.server.port}")
	public int port;

	@Autowired
	private WebApplicationContext context;

//	@Autowired
//	private Filter springSecurityFilterChain;

	public MockMvc mvc;

	@Before
	public void setUpGlobal() throws IOException {
		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;

		mvc = MockMvcBuilders
				.webAppContextSetup(context)
//				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

}
