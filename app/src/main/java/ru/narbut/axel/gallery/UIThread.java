package ru.narbut.axel.gallery;

import io.reactivex.Scheduler;
import ru.narbut.axel.domain.executor.PostExecutionThread;
import io.reactivex.android.schedulers.AndroidSchedulers;

import javax.inject.Inject;

public class UIThread implements PostExecutionThread {

    @Inject
    UIThread() {}

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}