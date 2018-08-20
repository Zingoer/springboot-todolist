package com.zingoer.todolist.service;

import com.zingoer.todolist.exception.TodoNotFoundException;
import com.zingoer.todolist.model.Todo;
import com.zingoer.todolist.repository.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRespository;

    @InjectMocks
    private TodoService todoService;

    @Test
    public void shouldFindOneTodo(){
        Todo todo = new Todo(1, "programming", false);
        when(todoRespository.findById(anyLong())).thenReturn(of(todo));

        Todo result = todoService.getItemDetails(1);
        assertThat(result.getItem()).as("Item description is wrong").isEqualTo(todo.getItem());
        assertThat(result.getIsFinished()).as("getIsFinished is wrong").isEqualTo(todo.getIsFinished());
    }

    @Test(expected = TodoNotFoundException.class)
    public void shouldThrowExceptionWhenOneTodoNotFound() throws Exception{
        given(todoRespository.findById(anyLong())).willReturn(empty());

        todoService.getItemDetails(1);
    }

    @Test
    public void shouldCreateSingleTodo(){
        Todo todo = new Todo(1, "dinner", false);
        when(todoRespository.findById(anyLong())).thenReturn(of(todo));

        Todo result = todoService.getItemDetails(1);
        assertThat(result.getItem()).as("Item description is wrong").isEqualTo(todo.getItem());
        assertThat(result.getIsFinished()).as("getIsFinished is wrong").isEqualTo(todo.getIsFinished());
    }

//    @Test
//    public void should
}