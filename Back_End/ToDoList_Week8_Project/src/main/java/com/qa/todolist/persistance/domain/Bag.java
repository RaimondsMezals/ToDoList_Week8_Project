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
public class Bag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String listName;

	public Bag(Long id, @NotNull String listName) {
		super();
		this.id = id;
		this.listName = listName;
	}

	public Bag(@NotNull String listName) {
		super();
		this.listName = listName;
	}

}
