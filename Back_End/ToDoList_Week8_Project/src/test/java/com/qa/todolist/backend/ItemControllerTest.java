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

import com.qa.todolist.controller.ItemController;
import com.qa.todolist.dto.ItemDto;
import com.qa.todolist.persistance.domain.Bag;
import com.qa.todolist.persistance.domain.Item;
import com.qa.todolist.service.ItemService;

@SpringBootTest
@ActiveProfiles("dev")
public class ItemControllerTest {

	@Autowired
	private ItemController controller;
	@MockBean
	private ItemService service;
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

	@Test
	void createItemTest() throws Exception {
		when(this.service.create(TEST_ITEM_1)).thenReturn(this.mapToDTO(TEST_ITEM_1));
		assertEquals(new ResponseEntity<ItemDto>(this.mapToDTO(TEST_ITEM_1), HttpStatus.CREATED),
				(this.controller.create(TEST_ITEM_1)));
		verify(this.service, atLeastOnce()).create(TEST_ITEM_1);
	}

	@Test
	void readByIdBagTest() throws Exception {
		when(this.service.readById(1L)).thenReturn(this.mapToDTO(TEST_ITEM_1));
		assertEquals(new ResponseEntity<ItemDto>(this.mapToDTO(TEST_ITEM_1), HttpStatus.OK),
				(this.controller.readOne(1L)));
		verify(this.service, atLeastOnce()).readById(1L);
	}

	@Test
	void readAllTest() throws Exception {
		when(this.service.readAll()).thenReturn(listOfItems.stream().map(this::mapToDTO).collect(Collectors.toList()));
		ResponseEntity<List<ItemDto>> expected = new ResponseEntity<List<ItemDto>>(
				listOfItems.stream().map(this::mapToDTO).collect(Collectors.toList()), HttpStatus.OK);
		assertEquals((expected), (this.controller.read()));
		verify(this.service, atLeastOnce()).readAll();
	}

	@Test
	void updateTest() throws Exception {
		when(this.service.update(this.mapToDTO(TEST_ITEM_1), 1L)).thenReturn(this.mapToDTO(TEST_ITEM_1));
		assertEquals(new ResponseEntity<ItemDto>(this.mapToDTO(TEST_ITEM_1), HttpStatus.ACCEPTED),
				(this.controller.update(1L, this.mapToDTO(TEST_ITEM_1))));
		verify(this.service, atLeastOnce()).update(this.mapToDTO(TEST_ITEM_1), 1L);
	}

	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(1L)).thenReturn(true);
		assertEquals(new ResponseEntity<>(null, HttpStatus.NO_CONTENT), (this.controller.delete(1L)));
		assertEquals(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR), (this.controller.delete(99L)));
		verify(this.service, atLeastOnce()).delete(1L);
	}

}
