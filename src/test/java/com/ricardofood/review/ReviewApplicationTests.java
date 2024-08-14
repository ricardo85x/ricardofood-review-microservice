package com.ricardofood.review;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@WebMvcTest(ReviewApplication.class)
@AutoConfigureMockMvc
@DisplayName("Review Application Test")class ReviewApplicationTests {

	@Test
	void contextLoads() {
	}

}
