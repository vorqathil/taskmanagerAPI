package com.vorqathil.taskmanager.services;

import com.vorqathil.taskmanager.exceptions.UserNotFoundException;
import com.vorqathil.taskmanager.exceptions.UserOperationException;
import com.vorqathil.taskmanager.models.Task;
import com.vorqathil.taskmanager.models.User;
import com.vorqathil.taskmanager.repositories.TaskRepository;
import com.vorqathil.taskmanager.exceptions.TaskNotFoundException;
import com.vorqathil.taskmanager.util.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void createTask(Task task, User user) {
        enrichTask(task, user);
        taskRepository.save(task);
    }

    public List<Task> getTasks(User user) {
        return taskRepository.findAllByUser(user).orElseThrow(UserNotFoundException::new);
    }

    public Task getTask(int id, User user) {
        Optional<Task> foundTask = taskRepository.findByIdAndUser(id, user);
        return foundTask.orElseThrow(TaskNotFoundException::new);
    }

    @Transactional
    public void updateTask(int id, Task task, User user) {
        Task existingTask = taskRepository.findByIdAndUser(id, user).orElseThrow(TaskNotFoundException::new);
        task.setId(id);
        task.setUser(user);
        task.setCreatedAt(existingTask.getCreatedAt());
        if (task.getStatus() == null){
            task.setStatus(existingTask.getStatus());
        }
        taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(int id, User user) {
        Task task = taskRepository.findByIdAndUser(id, user).orElseThrow(TaskNotFoundException::new);
        if (!task.getUser().equals(user)) {
            throw new UserOperationException();
        }
        taskRepository.deleteById(id);
    }

    private void enrichTask(Task task, User user) {
        task.setStatus(Status.OPEN);
        task.setUser(user);
    }
}
