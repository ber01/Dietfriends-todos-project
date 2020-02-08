package me.kyunghwan.todos.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kyunghwan.todos.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Todo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private boolean completed;

    @Column
    private LocalDateTime completeAt;

    @Builder
    public Todo(String name, boolean completed) {
        this.name = name;
        complete(completed);
    }

    public void update(String name, boolean completed) {
        this.name = name;
        complete(completed);
    }

    public void complete(boolean completed) {
        this.completed = completed;
        this.completeAt = completed ? LocalDateTime.now() : null;
    }

}
