package com.etcr.demo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Transactional
@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public boolean register(String cur_id, String cur_password)
    {
        User reg_user=new User();
        reg_user.setId(cur_id);
        reg_user.setPassword(cur_password);
        reg_user.setPhone(null);
        reg_user.setQq(null);
        reg_user.setName(null);
        reg_user.setDepart(null);
        loginRepository.save(reg_user);
        return true;
    }

    public boolean login(String cur_id, String cur_password)
    {
        User login_user;
        login_user=loginRepository.searchById(cur_id);
        return login_user.getPassword().equals(cur_password);
    }

    public Iterable<User> getAllUsers()
    {
        return loginRepository.findAll();
    }

    public boolean modify(String user_id, String new_password, String new_name, String new_depart, String new_phone, String new_qq)
    {
        String str="feihua";
        if(!(new_password.equals(str))) loginRepository.modifyPassword(new_password, user_id);
        if(!(new_name.equals(str))) loginRepository.modifyName(new_name,user_id);
        if(!(new_depart.equals(str))) loginRepository.modifyDepart(new_depart,user_id);
        if(!(new_phone.equals(str))) loginRepository.modifyPhone(new_phone,user_id);
        if(!(new_qq.equals(str))) loginRepository.modifyQq(new_qq,user_id);
        return true;
    }

    public User read(String id)
    {
        if(!(id.isEmpty()))
        return loginRepository.searchById(id);
        else return null;
    }

//    public boolean test()
//    {
//        User reg_user=new User();
//        reg_user.setId("191250038");
//        reg_user.setPassword("123456");
//        reg_user.setPhone(null);
//        reg_user.setQQ(null);
//        loginRepository.save(reg_user);
//        return true;
//    }
}
