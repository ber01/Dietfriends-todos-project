package me.kyunghwan.todos.todo;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.todos.todo.dto.TodoListResponseDto;
import me.kyunghwan.todos.todo.dto.TodoRequestDto;
import me.kyunghwan.todos.todo.dto.TodoResponseDto;
import me.kyunghwan.todos.user.User;
import me.kyunghwan.todos.user.UserAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RequiredArgsConstructor
@RequestMapping("/todos")
@RestController
public class TodoApiController {

    private final TodoService todoService;

    @GetMapping("/{id}")
    public TodoResponseDto lookup(@PathVariable Integer id) {
        return todoService.lookup(id);
    }

    @PostMapping
    public TodoResponseDto create(@RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal UserAdapter userAdapter) {
        System.out.println(requestDto.isCompleted());
        return new TodoResponseDto(todoService.create(requestDto, userAdapter.getUser()));
    }

    @PutMapping("/{id}")
    public TodoResponseDto update(@PathVariable Integer id, @RequestBody TodoRequestDto requestDto) {
        Integer updateId = todoService.update(id, requestDto);
        return lookup(updateId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        todoService.delete(id);
    }

    @GetMapping
    public List<TodoListResponseDto> index() {
        String url = linkTo(TodoApiController.class).toString();
        return todoService.findAllDesc(url);
    }

}
