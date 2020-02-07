package me.kyunghwan.todos.todo;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.todos.todo.dto.TodoListResponseDto;
import me.kyunghwan.todos.todo.dto.TodoRequestDto;
import me.kyunghwan.todos.todo.dto.TodoResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public Integer update(Integer id, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("[" + id + "]에 해당하는 todo 없음"));
        todo.update(requestDto.getName(), requestDto.isCompleted());
        return id;
    }

    @Transactional
    public void delete(Integer id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("[" + id + "]에 해당하는 todo 없음"));
        todoRepository.delete(todo);
    }

    @Transactional(readOnly = true)
    public List<TodoListResponseDto> findAllDesc(String url) {
        return todoRepository.findAllDesc().stream()
                .map(todo -> new TodoListResponseDto(todo, url))
                .collect(Collectors.toList());
    }
}
