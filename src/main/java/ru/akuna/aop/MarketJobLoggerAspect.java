package ru.akuna.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Los Pepes on 12/25/2017.
 */

@Aspect
@Component
public class MarketJobLoggerAspect
{
    @Before("execution(* ru.akuna.strategy.job.MarketJob.run())")
    public void logStartAnalysis(JoinPoint joinPoint)
    {
        LOG.info("Start Analysis");
    }

    @After("execution(* ru.akuna.strategy.job.MarketJob.run())")
    public void logFinishAnalysis(JoinPoint joinPoint)
    {
        LOG.info("Finish Analysis");
    }

    private static final Logger LOG = LoggerFactory.getLogger(MarketJobLoggerAspect.class);
}
