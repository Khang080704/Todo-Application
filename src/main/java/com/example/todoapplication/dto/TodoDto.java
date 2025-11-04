package com.example.todoapplication.dto;

import lombok.Data;

@Data
public class TodoDto {
    private Long id;
    private boolean state;
    private String todoName;
}
