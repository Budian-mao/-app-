package com.etcr.demo.comment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    public boolean add(String userid,String itemid,String comment, String time)
    {
        Comment new_com=new Comment();
        new_com.setUserid(userid);
        new_com.setComment(comment);
        new_com.setItemid(itemid);
        new_com.setTime(time);
        commentRepository.save(new_com);
        return true;
    }

    public Iterable<Comment> getById(String itemid)
    {
        return commentRepository.searchByItem(itemid);
    }
}
