package com.zingoer.todolist.repository;

import com.zingoer.todolist.model.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void shouldFindAllTodo() throws Exception{
        Todo todo1 = new Todo("programming", false);
        Todo todo2 = new Todo("dinner", true);

        testEntityManager.persist(todo1);
        testEntityManager.persist(todo2);
        testEntityManager.flush();

        List<Todo> result = todoRepository.findAll();

        assertThat(result.size()).as("The todo items size is wrong: ").isEqualTo(2);
        assertThat(result.get(0).getItem()).as("Item description is wrong").isEqualTo(todo1.getItem());
        assertThat(result.get(1).getIsFinished()).as("getIsFinished is wrong").isEqualTo(todo2.getIsFinished());
    }

    @Test
    public void shouldFindAllFinishedTodo() throws Exception{
        Todo todo1 = new Todo("programming", false);
        Todo todo2 = new Todo("dinner", true);

        testEntityManager.persist(todo1);
        testEntityManager.persist(todo2);
        testEntityManager.flush();

        List<Todo> result = todoRepository.findByIsFinished(true);

        assertThat(result.size()).as("The todo items size is wrong: ").isEqualTo(1);
        assertThat(result.get(0).getItem()).as("getItem is wrong").isEqualTo(todo2.getItem());
        assertThat(result.get(0).getIsFinished()).as("getIsFinished is wrong").isEqualTo(todo2.getIsFinished());
    }

    @Test
    public void shouldFindTodo() throws Exception{
        Todo todo = new Todo("programming", false);
        Todo savedTodo = testEntityManager.persistFlushFind(todo);
        Optional<Todo> result = todoRepository.findById(1l);

        assertThat(result.isPresent()).as("The todo item should be able to find").isEqualTo(true);
        Todo actual = result.get();
        assertThat(actual.getItem()).as("Item description is wrong").isEqualTo(savedTodo.getItem());
        assertThat(actual.getIsFinished()).as("getIsFinished is wrong").isEqualTo(savedTodo.getIsFinished());
    }

    @Test
    public void shouldSaveTodo() throws Exception{
        Todo todo = new Todo("dinner", true);
        Todo savedTodo = todoRepository.save(todo);
        testEntityManager.flush();

        assertThat(savedTodo.getItem()).as("Saved item should be returned as same").isEqualTo(todo.getItem());
        assertThat(savedTodo.getIsFinished()).as("Saved finish flag should be returned as same").isEqualTo(todo.getIsFinished());
    }
}