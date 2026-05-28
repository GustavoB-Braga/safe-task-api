package br.com.safe.task.domain.user;

public record UserResponseDto(Long id, String name, String login) {
    public UserResponseDto(User user) {
        this(user.getId(), user.getName(), user.getLogin());
    }
}
