package com.qa.todolist.backend;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.todolist.controller.BagController;
import com.qa.todolist.dto.ItemDto;
import com.qa.todolist.persistance.domain.Item;
import com.qa.todolist.service.BagService;

@SpringBootTest
@ActiveProfiles("dev")
public class ItemControllerTest {

	@Autowired
	private BagController controller;
	@MockBean
	private BagService service;
	@Autowired
	private ModelMapper mapper;

	private ItemDto mapToDTO(Item item) {
		return this.mapper.map(item, ItemDto.class);
	}

}
