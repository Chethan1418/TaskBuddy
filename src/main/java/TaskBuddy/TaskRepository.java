package TaskBuddy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface TaskRepository extends JpaRepository<TaskItem,Long> {
    List<TaskItem> findAllByOwnerEmailOrderByCompletedAscDueDateAsc(String email);
    Optional<TaskItem> findByIdAndOwnerEmail(Long id,String email);
    long countByOwnerEmail(String email);
    long countByOwnerEmailAndCompleted(String email,boolean completed);
}
