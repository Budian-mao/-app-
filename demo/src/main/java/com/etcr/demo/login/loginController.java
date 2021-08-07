package com.etcr.demo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/user")
public class loginController {
    @Autowired
    private LoginService loginService;

    @PostMapping(path="/register")
    public @ResponseBody  boolean register(@RequestParam String id, @RequestParam String password)
    {
        return loginService.register(id,password);
    }

    @PostMapping(path="/login")
    public @ResponseBody boolean login(@RequestParam String log_id, @RequestParam String log_password)
    {
        return loginService.login(log_id,log_password);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers()
    {
        return loginService.getAllUsers();
    }


    @PostMapping (path="/modify")
    public @ResponseBody boolean modify(@RequestParam String id, @RequestParam String password, @RequestParam String name, @RequestParam String depart, @RequestParam String phone, @RequestParam String qq)
    {
        return loginService.modify(id,password,name,depart,phone,qq);
    }

    @PostMapping (path="/read")
    public @ResponseBody User read(@RequestParam String id)
    {
        return loginService.read(id);
    }

//    @GetMapping(path = "/test1")
//    public @ResponseBody boolean test()
//    {
//        return loginService.test();
//    }

}
