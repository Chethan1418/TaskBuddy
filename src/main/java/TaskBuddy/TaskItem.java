package TaskBuddy;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="tasks")
public class TaskItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=140)
    private String title;
    @Column(length=2000)
    private String description;
    @Column(nullable=false, length=30)
    private String category;
    @Column(nullable=false, length=12)
    private String priority;
    private LocalDate dueDate;
    @Column(nullable=false)
    private boolean completed=false;
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private UserAccount owner;

    protected TaskItem() {}
    public Long getId(){return id;}
    public String getTitle(){return title;}
    public void setTitle(String v){title=v;}
    public String getDescription(){return description;}
    public void setDescription(String v){description=v;}
    public String getCategory(){return category;}
    public void setCategory(String v){category=v;}
    public String getPriority(){return priority;}
    public void setPriority(String v){priority=v;}
    public LocalDate getDueDate(){return dueDate;}
    public void setDueDate(LocalDate v){dueDate=v;}
    public boolean isCompleted(){return completed;}
    public void setCompleted(boolean v){completed=v;}
    public UserAccount getOwner(){return owner;}
    public void setOwner(UserAccount v){owner=v;}
}
