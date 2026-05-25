package br.com.safe.task.domain.task;

public record TaskResponseDto(Long id, String title, String description, Boolean completed) {

    public TaskResponseDto(Task task) {
        this(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }
}
