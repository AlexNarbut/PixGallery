package ru.narbut.axel.data.executor;


import android.support.annotation.NonNull;

import javax.inject.Inject;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.domain.executor.ThreadExecutor;


@ApplicationScope
public class JobExecutor implements ThreadExecutor {

    private final int INITIAL_POOL_SIZE = 3;
    private final int MAX_POOL_SIZE = 5;
    private final int KEEP_ALIVE_TIME = 10;
    private final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final ThreadPoolExecutor threadPoolExecutor;

    @Inject
    JobExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(
                INITIAL_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT,
                new LinkedBlockingQueue<>(), new JobThreadFactory());
    }

    @Override public void execute(@NonNull Runnable runnable) {
        this.threadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }
}
