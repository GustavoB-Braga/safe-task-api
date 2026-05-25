package br.com.safe.task.domain.task;

import br.com.safe.task.domain.user.User;
import br.com.safe.task.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserRepository userRepository;

    public TaskResponseDto register(TaskRequestDto dto) {

        User user = userRepository.findById(dto.userId()).orElseThrow(EntityNotFoundException::new);

        Task task = new Task(dto);
        var savedTask = repository.save(task);
        task.setUser(user);

        return new TaskResponseDto(savedTask.getId(), savedTask.getTitle(), savedTask.getDescription(), savedTask.isCompleted());
    }

    public Page<TaskResponseDto> getTasks(Pageable pageable) {

//        List<TaskResponseDto> listTasks = repository.findAll().stream()
//                .map(t -> new TaskResponseDto(t.getId(), t.getTitle(), t.getDescription(), t.isCompleted()))
//                .toList();

        return repository.findAll(pageable).map(TaskResponseDto::new);
    }

    public List<TaskResponseDto> getByUser(Long id) {

        return repository.findByUserId(id).stream()
                .map(task -> new TaskResponseDto(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted())).toList();
    }

    public TaskResponseDto switchStatus(Long id) {
        var task = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        task.toggleCompleted();

        return new TaskResponseDto(task);
    }
}
