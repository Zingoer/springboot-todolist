package com.zingoer.todolist.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String item;
    private boolean isFinished;

    public Todo(){}

    public Todo(long id, String item, boolean isFinished) {
        super();
        this.id = id;
        this.item = item;
        this.isFinished = isFinished;
    }

    public Todo(String item, boolean isFinished) {
        super();
        this.item = item;
        this.isFinished = isFinished;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean finished) {
        isFinished = finished;
    }
}
