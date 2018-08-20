package com.zingoer.todolist.service;

import com.zingoer.todolist.exception.TodoNotFoundException;
import com.zingoer.todolist.model.Todo;
import com.zingoer.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo getItemDetails(long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        return todo.orElseThrow(TodoNotFoundException::new);
    }

    public Todo createItem(Todo todo) {
        return todoRepository.save(todo);
    }

    public List<Todo> fetchAllTodos() {
        return todoRepository.findAll();
    }

    public List<Todo> fetchAllTodosByFinished(boolean isFinished) {
        return todoRepository.findByIsFinished(isFinished);
    }
}

