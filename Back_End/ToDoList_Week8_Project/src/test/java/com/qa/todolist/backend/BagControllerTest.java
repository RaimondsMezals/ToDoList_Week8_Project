package com.qa.todolist.backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.qa.todolist.controller.BagController;
import com.qa.todolist.dto.BagDto;
import com.qa.todolist.persistance.domain.Bag;
import com.qa.todolist.service.BagService;

@SpringBootTest
@ActiveProfiles("dev")
public class BagControllerTest {

	@Autowired
	private BagController controller;

	@MockBean
	private BagService service;

	@Autowired
	private ModelMapper mapper;

	private BagDto mapToDTO(Bag bag) {
		return this.mapper.map(bag, BagDto.class);
	}

	private final Bag TEST_BAG_1 = new Bag(1L, "ShoppingList");
	private final Bag TEST_BAG_2 = new Bag(2L, "RandomItemsList");
	private final Bag TEST_BAG_3 = new Bag(3L, "BasketList");
	private final Bag TEST_BAG_4 = new Bag(4L, "ToolList");

	private final List<Bag> LISTOFBARNS = List.of(TEST_BAG_1, TEST_BAG_2, TEST_BAG_3, TEST_BAG_4);

	@Test
	void createBagTest() throws Exception {
		when(this.service.create(TEST_BAG_1)).thenReturn(this.mapToDTO(TEST_BAG_1));
		assertThat(new ResponseEntity<BagDto>(this.mapToDTO(TEST_BAG_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_BAG_1));
		verify(this.service, atLeastOnce()).create(TEST_BAG_1);
	}

}
