package com.example.todoapplication.service;

import com.example.todoapplication.dto.TodoDto;
import com.example.todoapplication.entity.Todo;
import com.example.todoapplication.entity.User;
import com.example.todoapplication.repository.TodoRepository;
import com.example.todoapplication.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserInfoService userInfoService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Autowired
    public TodoService(TodoRepository todoRepository, ModelMapper modelMapper
                        ,UserInfoService userInfoService, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userInfoService = userInfoService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public List<TodoDto> getAllTodoOfCurrentUser() {
        User user = userInfoService.getUserInfo();
        List<Todo> todoList = user.getTodos();
        List<TodoDto> todoDtoList = new ArrayList<>();
        for(Todo todo : todoList) {
            TodoDto temp = modelMapper.map(todo, TodoDto.class);
            todoDtoList.add(temp);
        }
        return todoDtoList;
    }

    public boolean addNewTodo(TodoDto newTodo) {
        User user = userInfoService.getUserInfo();
        Todo todo = new Todo();
        todo.setTodoName(newTodo.getTodoName());
        user.addNewTodo(todo);
        userRepository.save(user);
        return true;
    }

    public boolean changeStatus(Long todo_id) {
        User user = userInfoService.getUserInfo();
        Todo todo = todoRepository.findByIdAndUserId(todo_id, user.getId());
        if(todo == null) {
            return false;
        }
        else {
            todo.setState(!todo.isState());
            todoRepository.save(todo);
            return true;
        }
    }

    private Todo getTodoByTodoIdAndUserId(Long todo_id) {
        User user = userInfoService.getUserInfo();
        Todo todo = todoRepository.findByIdAndUserId(todo_id, user.getId());
        return todo;
    }

    public boolean deleteTodo(Long todo_id) {
        Todo todo = getTodoByTodoIdAndUserId(todo_id);
        if(todo == null) {
            return false;
        }
        else {
            todoRepository.deleteById(todo_id);
            return true;
        }
    }

    public boolean changeTodoName(Long todo_id, String todoName) {
        Todo todo = getTodoByTodoIdAndUserId(todo_id);
        if(todo==null) {
            return false;
        }
        else {
            todo.setTodoName(todoName);
            todoRepository.save(todo);
            return true;
        }
    }
}
