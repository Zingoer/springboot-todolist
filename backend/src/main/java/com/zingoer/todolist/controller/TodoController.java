package com.zingoer.todolist.controller;

import com.zingoer.todolist.exception.TodoNotFoundException;
import com.zingoer.todolist.model.Todo;
import com.zingoer.todolist.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo")
    public List<Todo> findALl(@RequestParam(name = "isfinished", required = false) Boolean isFinished){
        if(isFinished != null){
            return todoService.fetchAllTodosByFinished(isFinished);
        }
        return todoService.fetchAllTodos();
    }

    @GetMapping("/todo/{id}")
    public Todo findOne(@PathVariable long id){
        return todoService.getItemDetails(id);
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> createOne(@RequestBody Todo todo){
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createItem(todo));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void todoNotFoundExceptionHandler(TodoNotFoundException todoNotFoundException){}
}
