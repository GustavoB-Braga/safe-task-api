package br.com.safe.task.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    public UserResponseDto register(UserRequestDto dto) {

        String encryptedPassword = passwordEncoder.encode(dto.password());

        User user = new User(dto, encryptedPassword);
        var savedUser = repository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getLogin());
    }

    public List<UserResponseDto> getUsers() {

        List<UserResponseDto> listUsers = repository.findAll().stream().map(u -> new UserResponseDto(u.getId(), u.getName(), u.getLogin())).toList();

        return listUsers;
    }
}
