package com.zingoer.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zingoer.todolist.exception.TodoNotFoundException;
import com.zingoer.todolist.model.Todo;
import com.zingoer.todolist.service.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void shouldGetSingleTodoItem() throws Exception{
        Todo todo = new Todo(1, "programming", false);
        given(todoService.getItemDetails(anyLong())).willReturn(todo);

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("item").value(todo.getItem()))
                .andExpect(jsonPath("finished").value(todo.getIsFinished()));
    }

    @Test
    public void shouldThrowExceptionWhenSingleTodoItemNotFound() throws Exception{
        given(todoService.getItemDetails(anyLong())).willThrow(new TodoNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateSingleTodoItem() throws Exception{
        Todo todo = new Todo(4, "dinner", true);
        given(todoService.createItem(any(Todo.class))).willReturn(todo);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(todo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(todo.getId()))
                .andExpect(jsonPath("item").value(todo.getItem()))
                .andExpect(jsonPath("finished").value(todo.getIsFinished()));
    }

    @Test
    public void shouldFetchAllTodos() throws Exception{
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1, "program", true));
        todos.add(new Todo(2, "dinner", false));

        given(todoService.fetchAllTodos()).willReturn(todos);

        mockMvc.perform(MockMvcRequestBuilders.get("/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(1))
                .andExpect(jsonPath("[1].item").value("dinner"));
    }

    @Test
    public void shouldFetchAllFinishedTodos() throws Exception{
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1, "program", true));
        todos.add(new Todo(2, "dinner", false));
        todos.add(new Todo(3, "lunch", true));

        given(todoService.fetchAllTodosByFinished(anyBoolean())).willReturn(todos);

        mockMvc.perform(MockMvcRequestBuilders.get("/todo?getIsFinished=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
