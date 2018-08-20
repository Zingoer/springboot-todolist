package com.zingoer.todolist;

import com.zingoer.todolist.model.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public class TodolistApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldCreateSingleTodo() throws Exception{
        // arrange
        Todo todo = new Todo(4, "dinner", true);
        // act
        ResponseEntity<Todo> response = restTemplate.postForEntity("/todo", todo, Todo.class);
        // assert
        assertThat(response.getStatusCode())
                .as("The created single todo status code is wrong: ")
                .isEqualTo(HttpStatus.CREATED);

        assertThat(response.getBody().getItem())
                .as("The created single todo item is wrong:")
                .isEqualTo(todo.getItem());

        assertThat(response.getBody().getIsFinished())
                .as("The created single todo finish flag is wrong:")
                .isEqualTo(todo.getIsFinished());
    }

    @Test
    public void shouldGetSingleTodo() throws Exception{
        // arrange
        // act
        ResponseEntity<Todo> response = restTemplate
                .getForEntity("/todo/1", Todo.class);

        // assert
        assertThat(response.getStatusCode())
                .as("The status code to fetch one is wrong: ")
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody().getItem())
                .isEqualTo("programming");

        assertThat(response.getBody().getIsFinished())
                .isEqualTo(true);
    }

    @Test
    public void shouldFetchAllTodo() throws Exception{
        ResponseEntity<List> response = restTemplate.getForEntity("/todo", List.class);

        assertThat(response.getStatusCode())
                .as("The /todo get all status code is wrong: ")
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody().size())
                .as("The /todo get all todo size is wrong: ")
                .isEqualTo(3);
    }

    @Test
    public void shouldFetchFinishedTodos() {
        ResponseEntity<List> response = restTemplate.getForEntity("/todo?isfinished=true", List.class);

        assertThat(response.getStatusCode())
                .as("The /todo?isfinished=true status code is wrong: ")
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody().size())
                .as("The /todo?isfinished=true todo size is wrong: ")
                .isEqualTo(1);
    }
}
