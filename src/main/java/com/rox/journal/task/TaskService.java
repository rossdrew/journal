package com.rox.journal.task;

import com.rox.journal.PageWrapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class TaskService {
    //TODO This becomes a database at some point
    private List<Task> tasks = new ArrayList<>();

    private static final Pattern starting = Pattern.compile("_([^_]*)_");
    private static final Pattern completing = Pattern.compile("--([^--]*)--");

    public boolean append(Task entry){
        return tasks.add(entry);
    }

    public PageWrapper<Task> list(){
        return PageWrapper.around(tasks).fromPoolOf(tasks.size());
    }
}
