package com.qa.todolist.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.todolist.dto.BagDto;
import com.qa.todolist.persistance.domain.Bag;
import com.qa.todolist.persistance.domain.Item;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Sql(scripts = { "classpath:ToDoL-schema.sql",
		"classpath:toDoL-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class BagControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private BagDto mapToDTO(Bag bag) {
		return this.mapper.map(bag, BagDto.class);
	}

	private final Bag TEST_BAG_1 = new Bag(1L, "ShoppingList");
	private final Item TEST_ITEM_1 = new Item(1L, "Sausage", 11.11, TEST_BAG_1);

	private final String URI = "/bag";

	@Test
	void createTest() throws Exception {
		// RESOURCES
		Bag testDomain = new Bag("ShoppingList", new ArrayList<>());
		BagDto testDto = mapToDTO(testDomain);
		testDto.setId(5L);
		RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON)
				.content(this.jsonifier.writeValueAsString(testDomain));
		// ASSERTIONS
		ResultMatcher checkStatus = status().isCreated();
		ResultMatcher checkBody = content().json(this.jsonifier.writeValueAsString(testDto));
		// ACTION
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void readAllTest() throws Exception {
		// RESOURCES
		RequestBuilder request = get(URI + "/read", List.of(TEST_ITEM_1));

		// ASSERTIONS
		ResultMatcher checkStatus = status().isOk();

		// ACTION
		this.mvc.perform(request).andExpect(checkStatus);
	}

	@Test
	void readByIdTest() throws Exception {
		// RESOURCES
		Bag testDomain = new Bag(1L, "ShoppingList", List.of(TEST_ITEM_1));
		BagDto testDto = mapToDTO(testDomain);
		testDto.setId(1L);
		RequestBuilder request = get(URI + "/read" + "/1");

		// ASSERTIONS
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkBody = content().json(this.jsonifier.writeValueAsString(testDto));

		// ACTION
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void updateTest() throws Exception {
		// RESOURCES

		Bag testDomain = new Bag("ShoppingListUpdated", List.of(TEST_ITEM_1));
		BagDto testDto = mapToDTO(testDomain);
		testDto.setId(1L);
		RequestBuilder request = put(URI + "/update" + "/1").contentType(MediaType.APPLICATION_JSON)
				.content(this.jsonifier.writeValueAsString(testDomain));
		// ASSERTIONS
		ResultMatcher checkStatus = status().isAccepted();
		ResultMatcher checkBody = content().json(this.jsonifier.writeValueAsString(testDto));
		// ACTION
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void deleteTest() throws Exception {
		// RESOURCES
		RequestBuilder request = delete(URI + "/delete" + "/1");
		// ASSERTIONS
		ResultMatcher checkStatus = status().isNoContent();
		// ACTION
		this.mvc.perform(request).andExpect(checkStatus);
	}

}
