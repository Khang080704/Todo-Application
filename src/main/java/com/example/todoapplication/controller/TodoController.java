package com.example.todoapplication.controller;

import com.example.todoapplication.dto.TodoDto;
import com.example.todoapplication.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{todo_id}")
    public ResponseEntity changeStatus(@PathVariable("todo_id") Long todo_id) {
        final boolean changeSuccessfully = todoService.changeStatus(todo_id);
        if (changeSuccessfully) {
            return ResponseEntity.ok().body("Successfully changed");
        }
        else {
            return ResponseEntity.internalServerError().body("Failed to change");
        }
    }
}
