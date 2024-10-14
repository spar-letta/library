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

	public String accessToken = "eyJraWQiOiJiNmNlOWZiNi1iYWVhLTQ5NjktYjM4OS1jMzAzZjVhZWRjZjkiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm93c2VyLWNsaWVudCIsImF1ZCI6ImJyb3dzZXItY2xpZW50IiwibmJmIjoxNzI4ODk3NzY2LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwidXNlclB1YmxpY0lkIjoiOGQyYzRmODUtNzFjZS00MmZkLWJiMTMtYzVlMjU0NDAzMmUzIiwiZXhwIjoxNzI4OTg0MTY2LCJpYXQiOjE3Mjg4OTc3NjYsImp0aSI6ImZmNDRhYWEzLWNkYmYtNGZmYi04ZWY0LWU0YzhiMzc5Yzk0NiIsImF1dGhvcml0aWVzIjpbIlJFQURfVVNFUlMiLCJDUkVBVEVfVVNFUiIsIkRFTEVURV9VU0VSIiwiVVBEQVRFX1VTRVIiLCJERUxFVEVfQk9PSyIsIlJFQURfQk9PS1MiLCJDUkVBVEVfQk9PSyIsIlVQREFURV9CT09LIl0sInVzZXJuYW1lIjoiamF2ZW5vY2tBZG1pbiJ9.yH0K_GEvK6LRCiAvjnJvjvZBMzPokvJYO0LrHW1oTuCsJBHiApcMBmM6W2ZIVT97Nsd8VxlqGQ6cRrkIcTi5PoN1zTu_FPXYn-EfBV1LdK_V4Az0FZLG_wVLvvSrPvM0ZTBYQOtgLS8KIxtgKjeijRqL1SZ-Ho3IYkPfIGT_yYhtlOIRbWUw_uWtEWcwg0KYM_iomvQlZ6xcmEibh-noN44e5ymOGftjWYE8WqaL3wHR8sEEZhX-4wtDqrQv_ZtgtaLeULFKU74gntNZTOPeFTmmOj4y9LEAllqNgqPGM-rUXMbGJyFypoJDbpie_K1v60qVHzpAodL-XMYYJ7MVxg";

	@Autowired
	private WebApplicationContext context;

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
