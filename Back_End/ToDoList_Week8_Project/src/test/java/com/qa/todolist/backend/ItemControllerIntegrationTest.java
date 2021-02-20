package com.qa.todolist.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.qa.todolist.dto.ItemDto;
import com.qa.todolist.persistance.domain.Bag;
import com.qa.todolist.persistance.domain.Item;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Sql(scripts = { "classpath:ToDoL-schema.sql",
		"classpath:toDoL-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ItemControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private ItemDto mapToDTO(Item item) {
		return this.mapper.map(item, ItemDto.class);
	}

	private final Bag TEST_BAG_1 = new Bag(1L, "ShoppingList");
	private final Bag TEST_BAG_2 = new Bag(2L, "RandomItemsList");
	private final Bag TEST_BAG_3 = new Bag(3L, "BasketList");
	private final Bag TEST_BAG_4 = new Bag(4L, "ToolList");

	private final Item TEST_ITEM_1 = new Item("Sausage", 11.11, TEST_BAG_1);
	private final Item TEST_ITEM_2 = new Item("Baseball", 22.22, TEST_BAG_2);
	private final Item TEST_ITEM_3 = new Item("Apple", 33.33, TEST_BAG_3);
	private final Item TEST_ITEM_4 = new Item("Wrench", 44.44, TEST_BAG_4);

	private final List<Item> listOfItems = List.of(TEST_ITEM_1, TEST_ITEM_2, TEST_ITEM_3, TEST_ITEM_4);

	private final String URI = "/item";

	@Test
	void createTest() throws Exception {
		// RESOURCES
		Item testDomain = new Item("Sausage", 11.11, TEST_BAG_1);
		ItemDto testDto = mapToDTO(testDomain);
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
		RequestBuilder request = get(URI + "/read");

		// ASSERTIONS
		ResultMatcher checkStatus = status().isOk();

		// ACTION
		this.mvc.perform(request).andExpect(checkStatus);
	}

	@Test
	void readByIdTest() throws Exception {
		// RESOURCES
		Item testDomain = new Item("Sausage", 11.11, TEST_BAG_1);
		ItemDto testDto = mapToDTO(testDomain);
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
		Item testDomain = new Item("SausageUpdated", 11.11, TEST_BAG_1);
		ItemDto testDto = mapToDTO(testDomain);
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
