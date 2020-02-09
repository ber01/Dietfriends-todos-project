package me.kyunghwan.todos.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kyunghwan.todos.common.BaseTimeEntity;
import me.kyunghwan.todos.user.User;

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

    @ManyToOne
    private User owner;

    @Builder
    public Todo(String name, User owner, boolean completed) {
        this.name = name;
        this.owner = owner;
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
