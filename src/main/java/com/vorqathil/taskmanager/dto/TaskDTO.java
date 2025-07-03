package com.vorqathil.taskmanager.dto;

import com.vorqathil.taskmanager.util.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private int id;

    @NotEmpty
    @Size(min = 2, max = 255, message = "Title should be between 2 and 255 characters")
    private String title;

    private String description;

    private Status status;
}
