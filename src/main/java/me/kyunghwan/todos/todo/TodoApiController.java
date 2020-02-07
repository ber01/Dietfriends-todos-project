package me.kyunghwan.todos.todo;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.todos.todo.dto.TodoRequestDto;
import me.kyunghwan.todos.todo.dto.TodoCreateResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/todos")
@RestController
public class TodoApiController {

    private final TodoService todoService;

    @PostMapping
    public TodoCreateResponseDto create(@RequestBody TodoRequestDto createDto) {
        return new TodoCreateResponseDto(todoService.create(createDto));
    }

}
