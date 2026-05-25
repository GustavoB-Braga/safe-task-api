package br.com.safe.task.domain.task;

import br.com.safe.task.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Task(TaskRequestDto dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.completed = false;
    }

    public void toggleCompleted() {
        this.completed = !this.completed;
    }
}
