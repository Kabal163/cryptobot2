package ru.akuna.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Ticker;
import ru.akuna.strategy.stockService.TickerService;
import ru.akuna.tools.properties.ApplicationProperties;

import java.util.IdentityHashMap;
import java.util.Map;

@Component
public class DemoAccount
{
    @Autowired
    public DemoAccount(ApplicationProperties demoAccountProperties)
    {
        initialBtcBalance = demoAccountProperties.getDoubleProperty("initial.btc.balance");
        balance.put(BTC, initialBtcBalance);
    }

    public Double getBalance(String currency)
    {
        synchronized (balance)
        {
            return balance.get(currency);
        }
    }

    public Double withdraw(String currency, Double amountToWithdraw)
    {
        synchronized (balance)
        {
            Double currentBalance = balance.get(currency);

            if (currentBalance >= amountToWithdraw)
            {
                return balance.merge(currency, amountToWithdraw, (a, b) -> a - b);
            }

            return null;
        }
    }

    public Double deposit(String currency, Double amountToDeposit)
    {
        synchronized (balance)
        {
            return balance.merge(currency, amountToDeposit, (a, b) -> a + b);
        }
    }

    public double getTotalBalanceInBtc()
    {
        Double totalBtcBalance = 0.0;

        for (Map.Entry<String, Double> pair : balance.entrySet())
        {
            String currency = pair.getKey();
            Double amount = getBalance(currency);

            if (BTC.equals(currency))
            {
                totalBtcBalance += amount;
            }
            else
            {
                Ticker ticker = tickerService.getTicker(BTC_MARKET + currency);
                Double currentCurrencyPrice = ticker.getLast();
                totalBtcBalance += amount * currentCurrencyPrice;
            }
        }

        return totalBtcBalance;
    }

    public Double getChangeOfBalance()
    {
        return getTotalBalanceInBtc() - initialBtcBalance;
    }

    public Double getChangeOfBalanceInPercent()
    {
        return (getTotalBalanceInBtc() - initialBtcBalance) / initialBtcBalance * 100;
    }

    @Autowired
    private TickerService tickerService;

    private static Map<String, Double> balance = new IdentityHashMap<>();
    private String BTC = "BTC";
    private String BTC_MARKET = "BTC-";
    private double initialBtcBalance;

}
