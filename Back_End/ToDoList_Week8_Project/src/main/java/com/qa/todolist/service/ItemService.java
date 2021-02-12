package com.qa.todolist.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.todolist.dto.ItemDto;
import com.qa.todolist.persistance.domain.Item;
import com.qa.todolist.persistance.repo.ItemRepo;

@Service
public class ItemService {

	private ItemRepo repo;

	private ModelMapper mapper;

	private ItemDto mapToDTO(Item item) {
		return this.mapper.map(item, ItemDto.class);
	}

	@Autowired
	public ItemService(ItemRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	public ItemDto create(Item item) {
		return this.mapToDTO(this.repo.save(item));
	}

}
