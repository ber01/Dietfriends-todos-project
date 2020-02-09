package me.kyunghwan.todos.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kyunghwan.todos.common.AppProperties;
import me.kyunghwan.todos.todo.dto.TodoRequestDto;
import me.kyunghwan.todos.todo.dto.TodoResponseDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AppProperties appProperties;

    @After
    public void setUp() {
        this.todoRepository.deleteAll();
    }

    @Test
    @Description("Todo를 등록하는 테스트")
    @WithMockUser(roles = "USER")
    public void todoCreateTest() throws Exception {
        // given
        String name = "name";
        boolean completed = false;
        TodoRequestDto requestDto = TodoRequestDto.builder()
                .name(name)
                .completed(completed)
                .build();

        String url = "http://localhost:" + port + "/todos";

        // when
        // ResponseEntity<TodoResponseDto> responseEntity = testRestTemplate.postForEntity(url, requestDto, TodoResponseDto.class);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Todo> all = todoRepository.findAll();
        assertThat(all.get(0).getId()).isGreaterThan(0);
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).isCompleted()).isEqualTo(completed);
        assertThat(all.get(0).getCompleteAt()).isNull();
        assertThat(all.get(0).getCreatedAt()).isNotNull();
        assertThat(all.get(0).getUpdatedAt()).isNotNull();
    }

    @Test
    @Description("WithMockUser를 사용하지 않고 인증 토큰을 사용하는 테스트")
    public void todoCreateTest2() throws Exception {
        String name = "name";
        TodoRequestDto requestDto = TodoRequestDto.builder()
                .name(name)
                .completed(true)
                .build();

        String url = "http://localhost:" + port + "/todos";

        mockMvc.perform(post(url)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private Object getBearerToken() throws Exception {
        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", "diet@email.com")
                .param("password", "friends")
                .param("grant_type", "password"));

        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser jackson2JsonParser = new Jackson2JsonParser();
        String token = jackson2JsonParser.parseMap(contentAsString).get("access_token").toString();
        System.out.println(token);
        return "Bearer " + token;
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

    @Test
    @Description("Todo를 등록했을 때 completed가 true면 completedAt의 값이 존재하는 테스트")
    @WithMockUser(roles = "USER")
    public void todoCreateWithCompleteTrue() throws Exception {
        // given
        String name = "name";
        TodoRequestDto requestDto = TodoRequestDto.builder()
                .name(name)
                .completed(true)
                .build();

        String url = "http://localhost:" + port + "/todos";

        // when
        // ResponseEntity<TodoResponseDto> responseEntity = testRestTemplate.postForEntity(url, requestDto, TodoResponseDto.class);
         mockMvc.perform(post(url)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(requestDto)))
                 .andExpect(status().isOk());

        // then
        Todo todo = todoRepository.findByName(name);
        assertThat(todo.isCompleted()).isTrue();
        assertThat(todo.getCompleteAt()).isNotNull();
    }

    @Test
    @Description("Todo를 수정하는 테스트 complete가 false -> true 수정될 때, completeAt 값이 존재")
    @WithMockUser(roles = "USER")
    public void todoUpdateTest() throws Exception {
        // given
        Todo todo = todoRepository.save(Todo.builder()
                .name("name")
                .completed(false)
                .build());

        Integer updateId = todo.getId();
        String updateName = "Update_name";

        TodoRequestDto requestDto = TodoRequestDto.builder()
                .name(updateName)
                .completed(true)
                .build();

        String url = "http://localhost:" + port + "/todos/" + updateId;

        // HttpEntity<TodoRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        // ResponseEntity<TodoResponseDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, TodoResponseDto.class);
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Todo updateTodo = todoRepository.findById(updateId).orElseThrow(IllegalAccessError::new);
        assertThat(updateTodo.getName()).isEqualTo(updateName);
        assertThat(updateTodo.isCompleted()).isTrue();
        assertThat(updateTodo.getCompleteAt()).isNotNull();
        assertThat(updateTodo.getUpdatedAt()).isAfter(updateTodo.getCreatedAt());
    }

    @Test
    @Description("Todo를 수정하는 테스트 complete가 true -> false 수정될 때, completeAt가 null")
    @WithMockUser(roles = "USERS")
    public void todoUpdateTest2() throws Exception {
        // given
        Todo todo = todoRepository.save(Todo.builder()
                .name("name")
                .completed(true)
                .build());

        Integer updateId = todo.getId();
        String updateName = "Update_name";

        TodoRequestDto requestDto = TodoRequestDto.builder()
                .name(updateName)
                .completed(false)
                .build();

        String url = "http://localhost:" + port + "/todos/" + updateId;

        // HttpEntity<TodoRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        // ResponseEntity<TodoResponseDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, TodoResponseDto.class);
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Todo updateTodo = todoRepository.findById(updateId).orElseThrow(IllegalAccessError::new);
        assertThat(updateTodo.getName()).isEqualTo(updateName);
        assertThat(updateTodo.isCompleted()).isFalse();
        assertThat(updateTodo.getCompleteAt()).isNull();
        assertThat(updateTodo.getUpdatedAt()).isAfter(updateTodo.getCreatedAt());
    }

    @Test
    @Description("Todo를 정상적으로 삭제하는 테스트 코드")
    @WithMockUser(roles = "USERS")
    public void todoDelete() throws Exception {
        // given
        Todo todo = todoRepository.save(Todo.builder()
                .name("name")
                .completed(true)
                .build());

        Integer deleteId = todo.getId();

        String url = "http://localhost:" + port + "/todos/" + deleteId;
        assertThat(todoRepository.findById(deleteId)).isNotEmpty();

        // when
        // testRestTemplate.delete(url);
        mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        assertThat(todoRepository.findById(deleteId)).isEmpty();
    }

    @Test
    @Description("전체 조회 테스트")
    public void listTest() {
        // given
        int index = 5;
        IntStream.rangeClosed(1, index).forEach(i ->
                todoRepository.save(Todo.builder()
                        .name("name" + i)
                        .completed(false)
                        .build())
        );

        String url = "http://localhost:" + port + "/todos";

        // when
        ResponseEntity<List> list = testRestTemplate.getForEntity(url, List.class);

        // then
        assertThat(list.getBody().size()).isEqualTo(index);
    }

}