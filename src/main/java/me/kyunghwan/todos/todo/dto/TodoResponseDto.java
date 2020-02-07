package me.kyunghwan.todos.todo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kyunghwan.todos.todo.Todo;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TodoResponseDto {

    private Integer id;
    private String name;
    private boolean completed;
    private LocalDateTime completeAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder

    public TodoResponseDto(Todo entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.completed = entity.isCompleted();
        this.completeAt = entity.getCompleteAt();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
