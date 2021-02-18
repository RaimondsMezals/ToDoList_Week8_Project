package com.qa.todolist.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

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

	private final List<Bag> listOfBags = List.of(TEST_BAG_1, TEST_BAG_2, TEST_BAG_3, TEST_BAG_4);

	@Test
	void createBagTest() throws Exception {
		when(this.service.create(TEST_BAG_1)).thenReturn(this.mapToDTO(TEST_BAG_1));
		assertEquals(new ResponseEntity<BagDto>(this.mapToDTO(TEST_BAG_1), HttpStatus.CREATED),
				(this.controller.create(TEST_BAG_1)));
		verify(this.service, atLeastOnce()).create(TEST_BAG_1);
	}

	@Test
	void readByIdBagTest() throws Exception {
		when(this.service.readById(1L)).thenReturn(this.mapToDTO(TEST_BAG_1));
		assertEquals(new ResponseEntity<BagDto>(this.mapToDTO(TEST_BAG_1), HttpStatus.OK),
				(this.controller.readOne(1L)));
		verify(this.service, atLeastOnce()).readById(1L);
	}

	@Test
	void readAllTest() throws Exception {
		when(this.service.readAll()).thenReturn(listOfBags.stream().map(this::mapToDTO).collect(Collectors.toList()));
		ResponseEntity<List<BagDto>> expected = new ResponseEntity<List<BagDto>>(
				listOfBags.stream().map(this::mapToDTO).collect(Collectors.toList()), HttpStatus.OK);
		assertEquals((expected), (this.controller.read()));
		verify(this.service, atLeastOnce()).readAll();
	}

	@Test
	void updateTest() throws Exception {
		when(this.service.update(this.mapToDTO(TEST_BAG_1), 1L)).thenReturn(this.mapToDTO(TEST_BAG_1));
		assertEquals(new ResponseEntity<BagDto>(this.mapToDTO(TEST_BAG_1), HttpStatus.ACCEPTED),
				(this.controller.update(1L, this.mapToDTO(TEST_BAG_1))));
		verify(this.service, atLeastOnce()).update(this.mapToDTO(TEST_BAG_1), 1L);
	}

	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(1L)).thenReturn(true);
		assertEquals(new ResponseEntity<>(null, HttpStatus.NO_CONTENT), (this.controller.delete(1L)));
		assertEquals(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR), (this.controller.delete(99L)));
		verify(this.service, atLeastOnce()).delete(1L);
	}

}
