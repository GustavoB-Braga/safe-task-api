package br.com.safe.task.controller;

import br.com.safe.task.domain.user.UserRequestDto;
import br.com.safe.task.domain.user.UserResponseDto;
import br.com.safe.task.domain.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @Transactional
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserRequestDto dto) {
        return ResponseEntity.ok(service.register(dto));// todo: Provisório, ajustar para created e inserir a URI Location;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(service.getUsers());
    }
}
