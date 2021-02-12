package com.qa.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.todolist.dto.ToDoListDto;
import com.qa.todolist.persistance.domain.ToDoList;
import com.qa.todolist.service.ToDoListService;

@RestController
@CrossOrigin
@RequestMapping("/todolist")
public class ToDoListController {

	private ToDoListService service;

	@Autowired
	public ToDoListController(ToDoListService service) {
		super();
		this.service = service;
	}

	@PostMapping("/create")
	public ResponseEntity<ToDoListDto> create(@RequestBody ToDoList tDList) {
		ToDoListDto created = this.service.create(tDList);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

}
