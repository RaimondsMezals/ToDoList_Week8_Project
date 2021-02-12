package com.qa.todolist.persistance.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ToDoList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String itemName;
	@NotNull
	private Double price;

	public ToDoList(Long id, @NotNull String itemName, @NotNull Double price) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.price = price;
	}

	public ToDoList(@NotNull String itemName, @NotNull Double price) {
		super();
		this.itemName = itemName;
		this.price = price;
	}

}
