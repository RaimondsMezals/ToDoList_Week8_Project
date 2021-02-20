package com.qa.todolist.persistance.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String itemName;
	@NotNull
	private double price;

	@ManyToOne
	private Bag bag;

	public Item(String itemName, double price, Bag bag) {
		super();
		this.itemName = itemName;
		this.price = price;
		this.bag = bag;
	}

}
