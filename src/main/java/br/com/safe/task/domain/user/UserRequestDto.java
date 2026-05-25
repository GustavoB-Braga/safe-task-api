package br.com.safe.task.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(@NotBlank String name,
                             @Email @NotBlank String login,
                             @NotBlank String password
) {
}
