package com.qa.todolist.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.todolist.dto.ItemDto;
import com.qa.todolist.persistance.domain.Bag;
import com.qa.todolist.persistance.domain.Item;
import com.qa.todolist.persistance.repo.ItemRepo;
import com.qa.todolist.service.ItemService;

@SpringBootTest
@ActiveProfiles("dev")
public class ItemServiceTest {

	@MockBean
	private ModelMapper mapper;
	@MockBean
	private ItemRepo repo;

	@Autowired
	private ItemService service;

	private static final ModelMapper mapperTest = new ModelMapper();

	private ItemDto mapToDTO(Item item) {
		return this.mapperTest.map(item, ItemDto.class);
	}

	private ItemDto mapToDTO2(List<Item> listOfItems2) {
		return this.mapperTest.map(listOfItems2, ItemDto.class);
	}

	private ItemDto mapToDTO3(List<ItemDto> listOfItems3) {
		return this.mapperTest.map(listOfItems3, ItemDto.class);
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
	void createItemTest() {

		Item testItem = new Item(1L, "Sausage", 11.11, TEST_BAG_1);
		ItemDto testItemDto = mapToDTO(testItem);

		when(repo.save(testItem)).thenReturn(testItem);
		when(mapper.map(testItem, ItemDto.class)).thenReturn(testItemDto);

		ItemDto result = service.create(testItem);

		assertEquals(testItemDto, result);
		verify(this.repo, atLeastOnce()).save(testItem);
		verify(this.mapper, atLeastOnce()).map(testItem, ItemDto.class);
	}

	@Test
	void readByIdItemTest() {

		Item testItem = new Item(1L, "Sausage", 11.11, TEST_BAG_1);
		Optional<Item> testItem2 = Optional.of(testItem);
		ItemDto testItemDto = mapToDTO(testItem);
		testItemDto.setId(1L);
		when(repo.findById(1L)).thenReturn(testItem2);
		when(mapper.map(testItem, ItemDto.class)).thenReturn(testItemDto);

		ItemDto result = service.readById(testItem.getId());

		assertEquals(testItemDto, result);
		assertEquals(true, testItem2.isPresent());
		verify(this.repo, atLeastOnce()).findById(testItem.getId());
		verify(this.mapper, atLeastOnce()).map(testItem, ItemDto.class);
	}

	@Test
	void readAllTest() {

		when(repo.findAll()).thenReturn(listOfItems);
		// when(mapper.map(listOfBags, BagDto.class))
		// .thenReturn(mapToDTO2(List.of(TEST_BAG_1, TEST_BAG_2, TEST_BAG_3,
		// TEST_BAG_4)));

		ItemDto result = mapToDTO3(service.readAll());

		assertEquals(mapToDTO2(listOfItems), result);

		verify(this.repo, atLeastOnce()).findAll();
		// verify(this.mapper, atLeastOnce()).map(listOfBags, BagDto.class);
	}

	@Test
	void updateItemTest() {

		Item testItem = new Item(1L, "Sausage", 11.11, TEST_BAG_1);
		ItemDto testItemDto = mapToDTO(testItem);
		Optional<Item> testItem2 = Optional.of(testItem);
		testItemDto.setId(1L);

		when(repo.findById(1L)).thenReturn(testItem2);
		when(repo.save(testItem)).thenReturn(testItem);
		when(mapper.map(testItem, ItemDto.class)).thenReturn(testItemDto);

		ItemDto result = service.update(testItemDto, 1L);

		assertEquals(testItemDto, result);
		assertEquals(true, testItem2.isPresent());

		verify(this.repo, atLeastOnce()).save(testItem);
		verify(this.mapper, atLeastOnce()).map(testItem, ItemDto.class);
	}

	@Test
	void deleteTest() {

		Item testItem = new Item(1L, "Sausage", 11.11, TEST_BAG_1);

		Boolean result = service.delete(1L);

		assertEquals(true, result);
		verify(this.repo, atLeastOnce()).deleteById(testItem.getId());
	}

}
