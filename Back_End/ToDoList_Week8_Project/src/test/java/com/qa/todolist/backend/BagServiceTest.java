package com.qa.todolist.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.todolist.dto.BagDto;
import com.qa.todolist.persistance.domain.Bag;
import com.qa.todolist.persistance.repo.BagRepo;
import com.qa.todolist.service.BagService;

@SpringBootTest
@ActiveProfiles("dev")
public class BagServiceTest {

	@MockBean
	private ModelMapper mapper;
	@MockBean
	private BagRepo repo;

	@Autowired
	private BagService service;

	private static final ModelMapper mapperTest = new ModelMapper();

	private BagDto mapToDTO(Bag bag) {
		return this.mapperTest.map(bag, BagDto.class);
	}

	private BagDto mapToDTO2(List<Bag> listOfBags2) {
		return this.mapperTest.map(listOfBags2, BagDto.class);
	}

	private BagDto mapToDTO3(List<BagDto> listOfBags3) {
		return this.mapperTest.map(listOfBags3, BagDto.class);
	}

	private final Bag TEST_BAG_1 = new Bag(1L, "ShoppingList");
	private final Bag TEST_BAG_2 = new Bag(2L, "RandomItemsList");
	private final Bag TEST_BAG_3 = new Bag(3L, "BasketList");
	private final Bag TEST_BAG_4 = new Bag(4L, "ToolList");

	private final List<Bag> listOfBags = List.of(TEST_BAG_1, TEST_BAG_2, TEST_BAG_3, TEST_BAG_4);

	@Test
	void createBagTest() {

		Bag testBag = new Bag(1L, "ShoppingList", new ArrayList<>());
		BagDto testBagDto = mapToDTO(testBag);

		when(repo.save(testBag)).thenReturn(testBag);
		when(mapper.map(testBag, BagDto.class)).thenReturn(testBagDto);

		BagDto result = service.create(testBag);

		assertEquals(testBagDto, result);
		verify(this.repo, atLeastOnce()).save(testBag);
		verify(this.mapper, atLeastOnce()).map(testBag, BagDto.class);
	}

	@Test
	void readByIdBagTest() {

		Bag testBag = new Bag(1L, "ShoppingList", new ArrayList<>());
		Optional<Bag> testBag2 = Optional.of(testBag);
		BagDto testBagDto = mapToDTO(testBag);
		testBagDto.setId(1L);
		when(repo.findById(1L)).thenReturn(testBag2);
		when(mapper.map(testBag, BagDto.class)).thenReturn(testBagDto);

		BagDto result = service.readById(testBag.getId());

		assertEquals(testBagDto, result);
		assertEquals(true, testBag2.isPresent());
		verify(this.repo, atLeastOnce()).findById(testBag.getId());
		verify(this.mapper, atLeastOnce()).map(testBag, BagDto.class);
	}

	@Test
	void readAllTest() {

		when(repo.findAll()).thenReturn(listOfBags);
		// when(mapper.map(listOfBags, BagDto.class))
		// .thenReturn(mapToDTO2(List.of(TEST_BAG_1, TEST_BAG_2, TEST_BAG_3,
		// TEST_BAG_4)));

		BagDto result = mapToDTO3(service.readAll());

		assertEquals(mapToDTO2(listOfBags), result);

		verify(this.repo, atLeastOnce()).findAll();
		// verify(this.mapper, atLeastOnce()).map(listOfBags, BagDto.class);
	}

	@Test
	void updateBagTest() {

		Bag testBag = new Bag(1L, "ShoppingListUpdated", new ArrayList<>());
		BagDto testBagDto = mapToDTO(testBag);
		Optional<Bag> testBag2 = Optional.of(testBag);
		testBagDto.setId(1L);

		when(repo.findById(1L)).thenReturn(testBag2);
		when(repo.save(testBag)).thenReturn(testBag);
		when(mapper.map(testBag, BagDto.class)).thenReturn(testBagDto);

		BagDto result = service.update(testBagDto, 1L);

		assertEquals(testBagDto, result);
		assertEquals(true, testBag2.isPresent());

		verify(this.repo, atLeastOnce()).save(testBag);
		verify(this.mapper, atLeastOnce()).map(testBag, BagDto.class);
	}

	@Test
	void deleteTest() {

		Bag testBag = new Bag(1L, "ShoppingList", new ArrayList<>());

		Boolean result = service.delete(1L);

		assertEquals(true, result);
		verify(this.repo, atLeastOnce()).deleteById(testBag.getId());
	}

}
