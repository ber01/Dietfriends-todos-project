package me.kyunghwan.todos.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kyunghwan.todos.config.BaseTimeEntity;

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
        this.completed = completed;
        this.completeAt = completed ? LocalDateTime.now() : null;
    }

    public void update(String name, boolean completed) {
        this.name = name;
        complete(completed);
        this.updatedAt = LocalDateTime.now();
    }

    public void complete(boolean completed) {
        this.completed = completed;
        completeAt = completed ? LocalDateTime.now() : null;
    }

}
