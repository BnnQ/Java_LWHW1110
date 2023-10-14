package me.bnnq.services;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Task<T>
{
    private final FutureTask<T> task;
    private final Thread thread;

    public Task(Callable<T> callable)
    {
        this.task = new FutureTask<>(callable);
        this.thread = new Thread(task);
    }

    public void run()
    {
        thread.start();
    }

    public void join() throws InterruptedException
    {
        thread.join();
    }

    public T get() throws ExecutionException, InterruptedException
    {
        return task.get();
    }

}
