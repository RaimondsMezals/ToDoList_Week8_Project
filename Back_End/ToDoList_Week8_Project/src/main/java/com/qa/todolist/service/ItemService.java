package com.qa.todolist.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.todolist.dto.ItemDto;
import com.qa.todolist.persistance.domain.Item;
import com.qa.todolist.persistance.repo.ItemRepo;
import com.qa.todolist.utils.SpringBeanUtil;

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

	public List<ItemDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());

	}

	public ItemDto readById(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow());
	}

	public ItemDto update(ItemDto itemDto, Long id) {

		Item toUpdate = this.repo.findById(id).orElseThrow();

		toUpdate.setItemName(itemDto.getItemName());

		SpringBeanUtil.mergeNotNull(itemDto, toUpdate);

		return this.mapToDTO(this.repo.save(toUpdate));
	}

	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
