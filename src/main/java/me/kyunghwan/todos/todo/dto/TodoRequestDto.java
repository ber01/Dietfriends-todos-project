package me.kyunghwan.todos.todo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kyunghwan.todos.todo.Todo;
import me.kyunghwan.todos.user.User;

@Getter
@NoArgsConstructor
public class TodoRequestDto {

    private String name;
    private boolean completed;

    @Builder
    public TodoRequestDto(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
    }

    public Todo toEntity(User user) {
        return Todo.builder()
                .name(name)
                .completed(completed)
                .owner(user)
                .build();
    }
}
