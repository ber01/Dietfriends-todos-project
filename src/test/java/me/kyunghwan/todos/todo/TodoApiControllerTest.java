package me.kyunghwan.todos.todo;

import me.kyunghwan.todos.todo.dto.TodoResponseDto;
import me.kyunghwan.todos.todo.dto.TodoRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    TodoRepository todoRepository;

    @After
    public void setUp() {
        this.todoRepository.deleteAll();
    }

    @Test
    @Description("Todo를 등록하는 테스트")
    public void todoCreateTest() {
        // given
        String name = "name";
        boolean completed = false;
        TodoRequestDto requestDto = TodoRequestDto.builder()
                .name(name)
                .completed(completed)
                .build();

        String url = "http://localhost:" + port + "/todos";

        // when
        ResponseEntity<TodoResponseDto> responseEntity = testRestTemplate.postForEntity(url, requestDto, TodoResponseDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Todo> all = todoRepository.findAll();
        assertThat(all.get(0).getId()).isGreaterThan(0);
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).isCompleted()).isEqualTo(completed);
        assertThat(all.get(0).getCompleteAt()).isNull();
        assertThat(all.get(0).getCreatedAt()).isNotNull();
        assertThat(all.get(0).getUpdatedAt()).isNotNull();
    }

    @Test
    @Description("Todo를 하나 조회하는 테스트")
    public void todoLookupTest() {
        // given
        String name = "name";
        boolean completed = false;
        Todo todo = todoRepository.save(Todo.builder()
                .name(name)
                .completed(completed)
                .build());

        String url = "http://localhost:" + port + "/todos/" + todo.getId();

        // when
        ResponseEntity<TodoResponseDto> responseEntity = testRestTemplate.getForEntity(url, TodoResponseDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Todo target = todoRepository.findById(todo.getId()).get();
        assertThat(target.getId()).isEqualTo(todo.getId());
        assertThat(target.getName()).isEqualTo(name);
        assertThat(target.isCompleted()).isEqualTo(false);
    }

}