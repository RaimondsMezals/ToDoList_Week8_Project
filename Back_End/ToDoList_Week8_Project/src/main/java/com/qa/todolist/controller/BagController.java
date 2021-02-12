package com.qa.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.todolist.dto.BagDto;
import com.qa.todolist.persistance.domain.Bag;
import com.qa.todolist.service.BagService;

@RestController
@CrossOrigin
@RequestMapping("/bag")
public class BagController {

	private BagService service;

	@Autowired
	public BagController(BagService service) {
		super();
		this.service = service;
	}

	@PostMapping("/create")
	public ResponseEntity<BagDto> create(@RequestBody Bag bag) {
		BagDto created = this.service.create(bag);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

}
