package com.etcr.demo.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/comment")
public class commentController {
    @Autowired
    private CommentService commentService;

    @PostMapping(path = "/add")
    public @ResponseBody boolean add(@RequestParam String userId, @RequestParam String itemId, @RequestParam String comment,@RequestParam String time)
    {
        return commentService.add(userId,itemId,comment,time);
    }

    @PostMapping(path= "/list")
    public @ResponseBody Iterable<Comment> getById(@RequestParam String itemId)
    {
        return commentService.getById(itemId);
    }
}
