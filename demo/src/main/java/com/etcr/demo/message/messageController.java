package com.etcr.demo.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/message")
public class messageController {
    @Autowired
    MessageService messageService;

    @PostMapping(path = "/new")
    public @ResponseBody boolean new_message(@RequestParam String send_id, @RequestParam String receive_id, @RequestParam String text)
    {
        return messageService.new_message(send_id,receive_id,text);
    }

    @PostMapping(path = "/search")
    public @ResponseBody List<String> search(@RequestParam String id)
    {
        return messageService.search(id);
    }

    @PostMapping(path = "/chat")
    public @ResponseBody List<Message> chat(@RequestParam String id1, @RequestParam String id2)
    {
        return messageService.chat(id1,id2);
    }


    @GetMapping(path= "/all")
    public @ResponseBody Iterable<Message> getAll()
    {
        return messageService.getAll();
    }
}
