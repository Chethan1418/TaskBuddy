package TaskBuddy;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
public class TaskController {
    private final TaskRepository tasks;
    private final UserRepository users;
    public TaskController(TaskRepository tasks,UserRepository users){this.tasks=tasks;this.users=users;}

    @GetMapping("/dashboard") public String dashboard(Authentication auth,Model model){
        String email=auth.getName();
        var list=tasks.findAllByOwnerEmailOrderByCompletedAscDueDateAsc(email);
        model.addAttribute("tasks",list);
        model.addAttribute("totalTasks",tasks.countByOwnerEmail(email));
        model.addAttribute("completedTasks",tasks.countByOwnerEmailAndCompleted(email,true));
        model.addAttribute("pendingTasks",tasks.countByOwnerEmailAndCompleted(email,false));
        model.addAttribute("today",LocalDate.now());
        model.addAttribute("displayName",users.findByEmailIgnoreCase(email).map(UserAccount::getDisplayName).orElse(email));
        return "dashboard";
    }
    @PostMapping("/tasks") public String add(@RequestParam String title,@RequestParam(required=false) String description,
        @RequestParam String category,@RequestParam String priority,@RequestParam(required=false) LocalDate dueDate,Authentication auth){
        TaskItem t=new TaskItem();
        t.setTitle(title.trim()); t.setDescription(description); t.setCategory(category); t.setPriority(priority);
        t.setDueDate(dueDate); t.setOwner(users.findByEmailIgnoreCase(auth.getName()).orElseThrow());
        tasks.save(t); return "redirect:/dashboard";
    }
    @PostMapping("/tasks/{id}/complete") public String complete(@PathVariable Long id,Authentication auth){
        TaskItem t=owned(id,auth); t.setCompleted(true); tasks.save(t); return "redirect:/dashboard";
    }
    @PostMapping("/tasks/{id}/delete") public String delete(@PathVariable Long id,Authentication auth){
        tasks.delete(owned(id,auth)); return "redirect:/dashboard";
    }
    @PostMapping("/tasks/{id}/edit") public String edit(@PathVariable Long id,@RequestParam String title,
        @RequestParam(required=false) String description,@RequestParam String category,@RequestParam String priority,
        @RequestParam(required=false) LocalDate dueDate,Authentication auth){
        TaskItem t=owned(id,auth); t.setTitle(title.trim()); t.setDescription(description); t.setCategory(category);
        t.setPriority(priority); t.setDueDate(dueDate); tasks.save(t); return "redirect:/dashboard";
    }
    private TaskItem owned(Long id,Authentication auth){
        return tasks.findByIdAndOwnerEmail(id,auth.getName()).orElseThrow(()->new IllegalArgumentException("Task not found"));
    }
}
