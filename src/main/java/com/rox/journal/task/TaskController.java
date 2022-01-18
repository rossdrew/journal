package com.rox.journal.task;

import com.rox.journal.PageWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public PageWrapper<Task> fetchTasks(){
        return taskService.list();
    }

    @PostMapping("/create")
    public String createEntry(@RequestBody Task task){
        taskService.append(task);
        return task.toString(); //TODO: What do we return here?
    }
}
