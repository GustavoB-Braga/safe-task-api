package br.com.safe.task.domain.task;

import br.com.safe.task.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public TaskResponseDto register(TaskRequestDto dto, User user) {

        Task task = new Task(dto);
        task.setUser(user);
        var savedTask = repository.save(task);

        return new TaskResponseDto(savedTask);
    }

    public Page<TaskResponseDto> getTaskByUser(User user, Pageable pageable) {
        return repository.findByUser(user, pageable).map(TaskResponseDto::new);
    }


    public TaskResponseDto switchStatus(Long id, User user) {
        var task = repository.findByIdAndUser(id, user).orElseThrow(EntityNotFoundException::new);
        task.toggleCompleted();

        return new TaskResponseDto(task);
    }

    public TaskResponseDto updateTask(Long id, TaskUpdateDto dto, User user) {

        var task = repository.findByIdAndUser(id, user).orElseThrow(EntityNotFoundException::new);

        task.update(dto);

        return new TaskResponseDto(task);
    }

    public void deleteTask(Long id, User user) {
        var task = repository.findByIdAndUser(id,user).orElseThrow(EntityNotFoundException::new);
        repository.delete(task);
    }
}
