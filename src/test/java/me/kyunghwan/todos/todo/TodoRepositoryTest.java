package me.kyunghwan.todos.todo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @Test
    @Description("Todo 저장, 조회 테스트")
    public void TodoSaveAndLoad() {
        // given
        LocalDateTime now = LocalDateTime.now();

        IntStream.rangeClosed(1, 3).forEach(i ->
                todoRepository.save(Todo.builder()
                        .name("name" + i)
                        .completed(false)
                        .build())
        );

        // when
        List<Todo> all = todoRepository.findAll();

        // then
        assertThat(all.get(0).getName()).isEqualTo("name1");
        assertThat(all.get(1).getName()).isEqualTo("name2");
        assertThat(all.get(2).getName()).isEqualTo("name3");

        assertThat(all.get(0).getCreatedAt()).isAfter(now);
        assertThat(all.get(0).getUpdatedAt()).isAfter(now);
    }

}