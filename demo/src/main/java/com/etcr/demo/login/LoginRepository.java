package com.etcr.demo.login;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends CrudRepository<User, String> {
    @Query(value= "select * from user where id=?1" , nativeQuery = true)
    public User searchById(String id);

    @Modifying
    @Query(value = "update user set password=?1 where id=?2", nativeQuery = true)
    public void modifyPassword(String password, String id);

    @Modifying
    @Query(value = "update user set name=?1 where id=?2", nativeQuery = true)
    public void modifyName(String name, String id);

    @Modifying
    @Query(value = "update user set depart=?1 where id=?2", nativeQuery = true)
    public void modifyDepart(String depart, String id);

    @Modifying
    @Query(value = "update user set phone=?1 where id=?2", nativeQuery = true)
    public void modifyPhone(String phone, String id);

    @Modifying
    @Query(value = "update user set qq=?1 where id=?2", nativeQuery = true)
    public void modifyQq(String qq, String id);
}
