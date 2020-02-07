package me.kyunghwan.todos.todo;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.todos.todo.dto.TodoRequestDto;
import me.kyunghwan.todos.todo.dto.TodoResponseDto;
import org.springframework.web.bind.annotation.*;

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
    public TodoResponseDto create(@RequestBody TodoRequestDto requestDto) {
        return new TodoResponseDto(todoService.create(requestDto));
    }

    @PutMapping("/{id}")
    public TodoResponseDto update(@PathVariable Integer id, @RequestBody TodoRequestDto requestDto) {
        return todoService.update(id, requestDto);
    }

}
