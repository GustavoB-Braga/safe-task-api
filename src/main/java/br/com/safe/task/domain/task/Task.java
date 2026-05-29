package br.com.safe.task.domain.task;

import br.com.safe.task.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "Task")
@Table(name = "tasks")
@EqualsAndHashCode(of = "id")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean completed;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Task(TaskRequestDto dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.completed = false;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    public void toggleCompleted() {
        this.completed = !this.completed;
    }

    public void update(TaskUpdateDto dto) {
        this.title = dto.title();
        this.description = dto.description();
    }
}
