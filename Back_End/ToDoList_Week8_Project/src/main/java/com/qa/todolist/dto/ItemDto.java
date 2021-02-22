package com.qa.todolist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {

	private Long id;
	private String itemName;
	private double price;

}
