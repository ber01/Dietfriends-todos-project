package me.kyunghwan.todos.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Query("SELECT t FROM Todo t ORDER BY t.id DESC")
    List<Todo> findAllDesc();

    Todo findByName(String name);

}
