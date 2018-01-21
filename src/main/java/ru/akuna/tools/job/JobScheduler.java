package ru.akuna.tools.job;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.akuna.strategy.job.CryptoJob;
import ru.akuna.strategy.job.TickerJob;

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

    public void stopJobById(Long id)
    {
        CryptoJob currentJob, jobToRemove = null;

        for (Map.Entry<Object, ScheduledFuture<?>> entrySet : scheduledJobs.entrySet())
        {
            currentJob = (CryptoJob) entrySet.getKey();

            if (currentJob.getId().equals(id))
            {
                entrySet.getValue().cancel(false);
                jobToRemove = currentJob;
            }
        }

        if (jobToRemove != null)
        {
            scheduledJobs.remove(jobToRemove);
        }
    }

    public CryptoJob getJobById(Long id)
    {
        for (Map.Entry<Object, ScheduledFuture<?>> entry : scheduledJobs.entrySet())
        {
            CryptoJob job = (CryptoJob) entry.getKey();
            if (job.getId().equals(id))
            {
                return job;
            }
        }

        return null;
    }

    private final Map<Object, ScheduledFuture<?>> scheduledJobs = new IdentityHashMap<>();
}
