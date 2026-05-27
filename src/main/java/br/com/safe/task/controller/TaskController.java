package br.com.safe.task.controller;

import br.com.safe.task.domain.task.TaskRequestDto;
import br.com.safe.task.domain.task.TaskResponseDto;
import br.com.safe.task.domain.task.TaskService;
import br.com.safe.task.domain.task.TaskUpdateDto;
import br.com.safe.task.domain.user.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    @Transactional
    public ResponseEntity<TaskResponseDto> registerTask(@RequestBody @Valid TaskRequestDto dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.created(null).body(service.register(dto, user)); //todo: Ajustar retorno da uri;
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getTaskByUser(Pageable pageable, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(service.getTaskByUser(user, pageable));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDto dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(service.updateTask(id, dto, user));
    }


    @PutMapping("/{id}/complete")
    @Transactional
    public ResponseEntity<TaskResponseDto> concludeTask(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        var response = service.switchStatus(id, user);
        return ResponseEntity.ok(response);
    }

}
