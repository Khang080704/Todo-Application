package com.example.todoapplication.repository;

import com.example.todoapplication.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    public List<Todo> findByCurrentUserId(Long user_id);
    @Query("select t from Todo t where t.id=:todo_id and t.currentUser.id = :user_id")
    public Todo findByIdAndUserId(@Param("todo_id") Long todo_id, @Param("user_id") Long user_id);
}
