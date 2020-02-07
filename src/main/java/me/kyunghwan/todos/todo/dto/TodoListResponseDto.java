package me.kyunghwan.todos.todo.dto;

import lombok.Getter;
import me.kyunghwan.todos.todo.Todo;

import java.io.File;
import java.time.LocalDateTime;

@Getter
public class TodoListResponseDto {

    private Integer id;
    private String name;
    private boolean completed;
    private LocalDateTime completedAt;
    private String url;

    public TodoListResponseDto(Todo todo, String url) {
        this.id = todo.getId();
        this.name = todo.getName();
        this.completed = todo.isCompleted();
        this.completedAt = todo.getCompleteAt();
        this.url = url + File.separator + todo.getId();
    }

}
