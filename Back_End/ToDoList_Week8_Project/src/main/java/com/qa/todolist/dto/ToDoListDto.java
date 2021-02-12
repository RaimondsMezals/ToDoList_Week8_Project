package com.qa.todolist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToDoListDto {

	private Long id;
	private String listName;

	// private List<ItemsDto> items = new ArrayList<>();

	public ToDoListDto(Long id, String listName) {
		super();
		this.id = id;
		this.listName = listName;
	}

}
