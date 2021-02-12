package com.qa.todolist.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.todolist.dto.ToDoListDto;
import com.qa.todolist.persistance.domain.ToDoList;
import com.qa.todolist.persistance.repo.ToDoListRepo;

@Service
public class ToDoListService {

	private ToDoListRepo repo;

	private ModelMapper mapper;

	private ToDoListDto mapToDTO(ToDoList tDList) {
		return this.mapper.map(tDList, ToDoListDto.class);
	}

	@Autowired
	public ToDoListService(ToDoListRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	public ToDoListDto create(ToDoList tDList) {
		return this.mapToDTO(this.repo.save(tDList));
	}

}
