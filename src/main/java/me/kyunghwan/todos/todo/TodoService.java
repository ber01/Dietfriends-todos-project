package me.kyunghwan.todos.todo;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.todos.todo.dto.TodoRequestDto;
import me.kyunghwan.todos.todo.dto.TodoResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public Todo create(TodoRequestDto requestDto) {
        return todoRepository.save(requestDto.toEntity());
    }

    public TodoResponseDto lookup(Integer id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("[" + id + "]에 해당하는 todo 없음"));
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto update(Integer id, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("[" + id + "]에 해당하는 todo 없음"));
        todo.update(requestDto.getName(), requestDto.isCompleted());
        return new TodoResponseDto(todo);
    }

}
