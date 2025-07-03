package com.vorqathil.taskmanager.repositories;

import com.vorqathil.taskmanager.models.Task;
import com.vorqathil.taskmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<List<Task>> findAllByUser(User user);

    Optional<Task> findByIdAndUser(int id, User user);
}
