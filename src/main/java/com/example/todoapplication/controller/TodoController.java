package com.example.todoapplication.controller;

import com.example.todoapplication.dto.TodoDto;
import com.example.todoapplication.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity getAllTodos() {
        List<TodoDto> list = todoService.getAllTodoOfCurrentUser();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity addTodo(@RequestBody TodoDto todoDto) {
        final boolean addSuccessfully = todoService.addNewTodo(todoDto);
        if (addSuccessfully) {
            return ResponseEntity.ok().body("Successfully added");
        }
        else {
            return ResponseEntity.internalServerError().body("Failed to add");
        }
    }

    @PutMapping("/state/{todo_id}")
    public ResponseEntity changeStatus(@PathVariable("todo_id") Long todo_id) {
        final boolean changeSuccessfully = todoService.changeStatus(todo_id);
        if (changeSuccessfully) {
            return ResponseEntity.ok().body("Successfully changed");
        }
        else {
            return ResponseEntity.status(404).body("Todo not exists with current user");
        }
    }

    @DeleteMapping("/{todo_id}")
    public ResponseEntity deleteTodo(@PathVariable("todo_id") Long todo_id) {
        final boolean deleteSuccessfully = todoService.deleteTodo(todo_id);
        if (deleteSuccessfully) {
            return ResponseEntity.ok().body("Successfully deleted");
        }
        else {
            return ResponseEntity.status(404).body("Todo not exists with current user");
        }
    }

    @PutMapping("/name/{todo_id}")
    public ResponseEntity changeTodoName(@PathVariable("todo_id") Long todo_id, @RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        final boolean updateNameSuccessfully = todoService.changeTodoName(todo_id, name);
        if (updateNameSuccessfully) {
            return ResponseEntity.ok().body("Successfully changed");
        }
        else {
            return ResponseEntity.status(404).body("Todo not exists with current user");
        }
    }
}
