package me.kyunghwan.todos.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Todo {

    private Integer id;
    private String name;
    private boolean completed;
    private LocalDateTime completeAt;

    @Builder
    public Todo(Integer id, String name, boolean completed, LocalDateTime completeAt) {
        this.id = id;
        this.name = name;
        this.completed = completed;
        this.completeAt = completeAt;
    }
}
