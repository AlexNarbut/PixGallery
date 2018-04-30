package ru.narbut.axel.domain.executor;


import io.reactivex.Scheduler;

public interface PostExecutionThread {
    Scheduler getScheduler();

}
