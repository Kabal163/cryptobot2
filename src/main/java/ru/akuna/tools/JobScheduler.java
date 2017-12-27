package ru.akuna.tools;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by Los Pepes on 12/27/2017.
 */
@Component
public class JobScheduler extends ThreadPoolTaskScheduler
{
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable job, long period)
    {
        ScheduledFuture<?> future = super.scheduleAtFixedRate(job, period);
        scheduledJobs.put(job, future);
        return future;
    }

    public void stopJob(Object job)
    {
        scheduledJobs.get(job).cancel(false);
        scheduledJobs.remove(job);
    }

    private final Map<Object, ScheduledFuture<?>> scheduledJobs = new IdentityHashMap<>();
}
