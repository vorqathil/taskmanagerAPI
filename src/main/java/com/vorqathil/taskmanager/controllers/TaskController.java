package com.vorqathil.taskmanager.controllers;

import com.vorqathil.taskmanager.dto.TaskDTO;
import com.vorqathil.taskmanager.models.Task;
import com.vorqathil.taskmanager.services.TaskService;
import com.vorqathil.taskmanager.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, UserService userService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createTask(@RequestBody TaskDTO taskDTO, Principal principal) {
        taskService.createTask(converToTask(taskDTO), userService.findByUsername(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public List<TaskDTO> getTasks(Principal principal) {
        return taskService.getTasks(userService.findByUsername(principal.getName())).stream().map(this::converToTaskDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable("id") int id, Principal principal) {
        return converToTaskDTO(taskService.getTask(id, userService.findByUsername(principal.getName())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable("id") int id, @RequestBody Task task, Principal principal) {
        taskService.updateTask(id, task, userService.findByUsername(principal.getName()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") int id, Principal principal) {
        taskService.deleteTask(id, userService.findByUsername(principal.getName()));
        return ResponseEntity.noContent().build();
    }

    private Task converToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO converToTaskDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}
