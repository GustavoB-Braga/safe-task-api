package br.com.safe.task.domain.task;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDto(
        @NotBlank String title,
        @NotBlank String description) {
}
