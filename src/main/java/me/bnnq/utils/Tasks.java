package me.bnnq.utils;

import me.bnnq.services.Task;

import java.util.concurrent.Callable;

public class Tasks
{
    public static <T> Task<T> run(Callable<T> callable)
    {
        Task<T> task = new Task<>(callable);
        task.run();
        return task;
    }

    public static void waitAll(Task<?>... tasks) throws InterruptedException
    {
        for (Task<?> task : tasks)
        {
            task.join();
        }
    }
}