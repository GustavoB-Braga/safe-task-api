package br.com.safe.task.domain.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequestDto(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull Long userId) {
}
