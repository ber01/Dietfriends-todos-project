package me.kyunghwan.todos.todo;

import org.junit.Test;
import org.springframework.context.annotation.Description;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoTest {

    @Test
    @Description("객체 생성 테스트")
    public void test() {
        int id = 1;
        String name = "name";

        Todo todo = Todo.builder()
                .id(id)
                .name(name)
                .completed(false)
                .completeAt(LocalDateTime.now())
                .build();

        assertThat(todo).isNotNull();
        assertThat(todo.getId()).isEqualTo(id);
        assertThat(todo.getName()).isEqualTo(name);
    }

}