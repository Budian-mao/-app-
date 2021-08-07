package com.etcr.demo.publish;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/merch")
public class publishController {
    @Autowired
    private  PublishService publishService;

    @PostMapping(path = "/publish")
    public @ResponseBody boolean publish(@RequestParam String new_name, @RequestParam String new_user_id, @RequestParam String new_type, @RequestParam String new_time, @RequestParam String new_money, @RequestParam String new_phone, @RequestParam String new_detail, @RequestParam String new_picture){
        return publishService.publish(new_name,new_user_id,new_type,new_time,new_money,new_phone, new_detail,new_picture);
    }

    @PostMapping(path = "/check")
    public @ResponseBody List<Merchandise> My_mer(@RequestParam String check_user_id)
    {
        return publishService.My_mer(check_user_id);
    }

    @PostMapping(path = "/delete")
    public @ResponseBody boolean delete(@RequestParam String title)
    {
        return publishService.delete(title);
    }

    @GetMapping(path = "/each")
    public @ResponseBody List<Merchandise> All_mer()
    {
        return publishService.All_mer();
    }

    @PostMapping(path ="/kind")
    public @ResponseBody List<Merchandise> kind_mer(String kind)
    {
        return publishService.kind_mer(kind);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Merchandise> getAllmer() {
        return  publishService.getAllmer();
    }
}
