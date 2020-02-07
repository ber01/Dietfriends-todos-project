package me.kyunghwan.todos.todo;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.todos.todo.dto.TodoRequestDto;
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
}
