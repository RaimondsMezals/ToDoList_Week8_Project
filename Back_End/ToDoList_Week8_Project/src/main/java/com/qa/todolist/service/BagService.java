package com.qa.todolist.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.todolist.dto.BagDto;
import com.qa.todolist.persistance.domain.Bag;
import com.qa.todolist.persistance.repo.BagRepo;

@Service
public class BagService {

	private BagRepo repo;

	private ModelMapper mapper;

	private BagDto mapToDTO(Bag bag) {
		return this.mapper.map(bag, BagDto.class);
	}

	@Autowired
	public BagService(BagRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	public BagDto create(Bag bag) {
		return this.mapToDTO(this.repo.save(bag));
	}

	public List<BagDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());

	}

}
