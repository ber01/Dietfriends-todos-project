package me.kyunghwan.todos.todo;

import org.junit.Test;
import org.springframework.context.annotation.Description;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoTest {

    @Test
    @Description("객체 생성 테스트, completed가 false면 complteAt값 null")
    public void newTodoCompleteFalse() {
        String name = "name";

        Todo todo = Todo.builder()
                .name(name)
                .completed(false)
                .build();

        assertThat(todo).isNotNull();
        assertThat(todo.getName()).isEqualTo(name);
        assertThat(todo.getCompleteAt()).isNull();
    }

    @Test
    @Description("객체 생성 테스트, completed가 false면 complteAt값 존재")
    public void newTodoCompleteTrue() {
        String name = "name";

        Todo todo = Todo.builder()
                .name(name)
                .completed(true)
                .build();

        assertThat(todo).isNotNull();
        assertThat(todo.getName()).isEqualTo(name);
        assertThat(todo.getCompleteAt()).isNotNull();
    }

}