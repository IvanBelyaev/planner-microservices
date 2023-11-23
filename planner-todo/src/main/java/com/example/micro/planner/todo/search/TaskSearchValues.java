package com.example.micro.planner.todo.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskSearchValues {
    private Long userId;
    private String title;
    private Date dateFrom;
    private Date dateTo;
    private Boolean completed;
    private Long priorityId;
    private Long categoryId;

    private Integer pageNumber;
    private Integer pageSize;

    private String direction;
    private String sortField;
}
