package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

class DemoApplicationTests {

	@Nested
	@AutoConfigureMockMvc
	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
		"management.endpoints.web.exposure.include=health,channels"
	})
	class WithHealth {
		@Test
		void testWithHealth(@Autowired MockMvc mvc) throws Exception {
			mvc.perform(get("/actuator/channels"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.outputs").doesNotExist());
		}
	}

	@Nested
	@AutoConfigureMockMvc
	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
		"management.endpoints.web.exposure.include=channels"
	})
	class WithoutHealth {
		@Test
		void testWithoutHealth(@Autowired MockMvc mvc) throws Exception {
			mvc.perform(get("/actuator/channels"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.outputs").exists());
		}
	}
}
