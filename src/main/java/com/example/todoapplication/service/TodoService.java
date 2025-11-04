package com.example.todoapplication.service;

import com.example.todoapplication.dto.TodoDto;
import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.entity.Todo;
import com.example.todoapplication.entity.User;
import com.example.todoapplication.repository.TodoRepository;
import com.example.todoapplication.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
}
