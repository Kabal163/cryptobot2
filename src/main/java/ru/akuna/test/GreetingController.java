package ru.akuna.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value="/test", method= RequestMethod.GET)
    public String handlePost(@RequestParam String action, Model m) {
        if( action.equals("save") ){
            System.out.println("Save button");
        }
        else if( action.equals("renew") ){
            //handle renew
            System.out.println("Save renew");

        }
        m.addAttribute("name", "change");
        return "test";
    }

}