package ru.akuna.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.akuna.logic.MarketJob;
import ru.akuna.logic.MarketService;

/**
 * Created by Los Pepes on 12/25/2017.
 */

/* Это некий спринговый листнер, который отрабатывает по завершении метода, описанного в аннотации.
* Это технология AOP
*/

@Aspect
@Component
public class MarketServiceAspect
{
    @Autowired
    private MarketJob marketJob;

    @AfterReturning("execution(* ru.akuna.logic.MarketService.updateMarkets())")
    public void updateMarkets(JoinPoint joinPoint)
    {
        marketJob.start();
    }

}
