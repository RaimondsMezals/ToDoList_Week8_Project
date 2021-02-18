package com.qa.todolist.backend;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.todolist.dto.BagDto;
import com.qa.todolist.persistance.domain.Bag;

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
	private final Bag TEST_BAG_2 = new Bag(2L, "RandomItemsList");
	private final Bag TEST_BAG_3 = new Bag(3L, "BasketList");
	private final Bag TEST_BAG_4 = new Bag(4L, "ToolList");

	private final List<Bag> LISTOFBAGS = List.of(TEST_BAG_1, TEST_BAG_2, TEST_BAG_3, TEST_BAG_4);

	private final String URI = "/bag";

	@Test
	void createTest() throws Exception {
		BagDto testDto = mapToDTO(new Bag("TestList"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDto);

		RequestBuilder request = MockMvcRequestBuilders.post(URI + "/create").content(testDTOAsJSON)
				.contentType(MediaType.APPLICATION_JSON);

		ResultMatcher checkStatus = status().isCreated();

		BagDto testSavedDto = mapToDTO(new Bag("TestList"));

		testSavedDto.setId(5L);
		String TestSavedDtoAsJson = this.jsonifier.writeValueAsString(testSavedDto);

		ResultMatcher checkBody = MockMvcResultMatchers.content().json(TestSavedDtoAsJson);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);

	}

}
