package com.github.aksakalli.todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * A Thing for your todo list
 */
@Entity
@Table(name = "thing")
public class Thing extends AbstractEntity implements Serializable {

    @Column(name = "title", length = 255)
    private String title;


    @Column(name = "content")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
