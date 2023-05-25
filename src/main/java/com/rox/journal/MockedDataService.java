package com.rox.journal;

import com.rox.journal.entry.JournalEntriesService;
import com.rox.journal.entry.JournalEntry;
import com.rox.journal.task.Task;
import com.rox.journal.task.TaskService;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * FOR DEBUG ONLY
 *
 * Sets up some mock data in the services
 **/
@Repository
public class MockedDataService {
    private final JournalEntriesService journalEntriesService;
    private final TaskService taskService;


    public MockedDataService(final JournalEntriesService journalEntriesService,
                             final TaskService taskService) {
        this.journalEntriesService = journalEntriesService;
        this.taskService = taskService;
    }

    @PostConstruct
    public void initialize() throws InterruptedException {
        //Add some journal entries
        final JournalEntry journalEntry1 = JournalEntry.create("This is TEST journal entry No.1");
        journalEntriesService.append(journalEntry1);
        Thread.sleep(100);
        final JournalEntry journalEntry2 = JournalEntry.create("This is TEST journal entry No.2");
        journalEntriesService.append(journalEntry2);
        Thread.sleep(100);
        final JournalEntry journalEntry3 = JournalEntry.create("This is TEST journal entry No.3");
        journalEntriesService.append(journalEntry3);
        Thread.sleep(100);
        final JournalEntry journalEntry4 = JournalEntry.create("This is TEST journal entry No.4");
        journalEntriesService.append(journalEntry4);
        Thread.sleep(100);
        final JournalEntry journalEntry5 = JournalEntry.create("This is TEST journal entry No.5");
        journalEntriesService.append(journalEntry5);
        Thread.sleep(100);

        final Task sampleTask = new Task("Sample Task from 2-4");
        sampleTask.startedAt(journalEntry2).completeAsOf(journalEntry4);
        taskService.append(sampleTask);
    }
}
