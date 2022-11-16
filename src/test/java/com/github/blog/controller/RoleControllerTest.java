package com.github.blog.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.blog.payload.RoleDto;
import com.github.blog.security.CustomUserDetailsService;
import com.github.blog.service.impl.RoleServiceImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RoleControllerTest {

	@MockBean
	private RoleServiceImpl roleService;

	@Autowired
    private WebApplicationContext context;

	@Autowired
	private MockMvc mockMvc;

	/*@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(springSecurity())
          .build();
    }*/

	@WithMockUser(value = "john")
	@Test
	void testCreate() throws Exception {
		String roleName = "admin";
		RoleDto roleDto = RoleDto.builder().name(roleName).build();
		when(roleService.createRole(roleDto)).thenReturn(roleDto);

		System.out.println(roleDto);

		mockMvc.perform(post("/api/roles")
			.contentType(MediaType.APPLICATION_JSON)
			.content(String.format("{\"name\": \"%s\"}", roleName)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value(roleName))
			.andExpect(jsonPath("$.id").value(0))
			.andDo(print());
	}
}
