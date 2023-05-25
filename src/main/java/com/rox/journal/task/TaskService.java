package com.rox.journal.task;

import com.rox.journal.PageWrapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskService {
    //TODO This becomes a database at some point
    private List<Task> tasks = new ArrayList<>();

    public TaskService(){

    }

    public boolean append(Task entry){
        return tasks.add(entry);
    }

    public PageWrapper<Task> list(){
        return PageWrapper.around(tasks).fromPoolOf(tasks.size());
    }
}
