package br.com.safe.task.controller;

import br.com.safe.task.domain.task.TaskRequestDto;
import br.com.safe.task.domain.task.TaskResponseDto;
import br.com.safe.task.domain.task.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    @Transactional
    public ResponseEntity<TaskResponseDto> registerTask(@RequestBody @Valid TaskRequestDto dto) {
        return ResponseEntity.created(null).body(service.register(dto)); //todo: Ajustar retorno da uri;
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getAllTasks(@PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(service.getTasks(pageable));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<TaskResponseDto>> getTaskByUser(@PathVariable Long id) {
        var response = service.getByUser(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/complete")
    @Transactional
    public ResponseEntity<TaskResponseDto> concludeTask(@PathVariable Long id) {
        var response = service.switchStatus(id);
       return ResponseEntity.ok(response);
    }

}
