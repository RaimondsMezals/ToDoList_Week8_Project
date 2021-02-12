package com.qa.todolist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BagDto {

	private Long id;
	private String listName;

	// private List<ItemsDto> items = new ArrayList<>();

	public BagDto(Long id, String listName) {
		super();
		this.id = id;
		this.listName = listName;
	}

	public BagDto(String listName) {
		super();
		this.listName = listName;
	}

}
