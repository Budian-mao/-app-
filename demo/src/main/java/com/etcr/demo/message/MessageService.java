package com.etcr.demo.message;

import com.etcr.demo.File.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

@Transactional
@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public boolean new_message(String new_sendid, String new_receiveid, String new_text)
    {
        Message new_mes=new Message();
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String new_time=dateFormat.format(date);
        new_mes.setTime(new_time);
        new_mes.setSend_id(new_sendid);
        new_mes.setReceive_id(new_receiveid);
        new_mes.setText(new_text);
        messageRepository.save(new_mes);
        return true;
    }

    public List<String> search(String id)
    {
        FileOperation file=new FileOperation();
        List<String> str1=new ArrayList<String>();
        List<String> str2=new ArrayList<String>();
        str1=messageRepository.searchBySend(id);
        str2=messageRepository.searchByReceive(id);
        List<String> res=null;
        try {
            res = file.deepCopy(str1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        res.addAll(str2);
        List<String> listnew= new ArrayList<String>(new TreeSet<String>(res));
        return listnew;
    }

    public List<Message> chat(String id1, String id2)
    {
        return messageRepository.searchBychat(id1,id2);
    }
    public Iterable<Message> getAll()
    {
        return messageRepository.findAll();
    }
}
