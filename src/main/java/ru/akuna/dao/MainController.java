package ru.akuna.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/demo")
public class MainController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/add")
    public @ResponseBody String addNewCoin(@RequestParam String marketName, @RequestParam double belowPrice, @RequestParam double abovePrice)
    {
        BittrexMarket market = new BittrexMarket();
        market.setMarketName(marketName);
        market.setBelowPrice(belowPrice);
        market.setAbovePrice(abovePrice);

        userRepository.save(market);

        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<BittrexMarket> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
