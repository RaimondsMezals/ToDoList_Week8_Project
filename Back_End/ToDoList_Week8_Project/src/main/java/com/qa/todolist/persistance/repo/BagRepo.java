package com.qa.todolist.persistance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.todolist.persistance.domain.Bag;

@Repository
public interface BagRepo extends JpaRepository<Bag, Long> {

}
