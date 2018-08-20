package com.zingoer.todolist.repository;

import com.zingoer.todolist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByIsFinished(boolean isFinished);
}
