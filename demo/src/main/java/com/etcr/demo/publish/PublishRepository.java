package com.etcr.demo.publish;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublishRepository extends CrudRepository<Merchandise,String> {
    @Query(value="select * from merchandise where post_userid=?1", nativeQuery=true)
    public List<Merchandise> findByUser(String user_id);

    @Query(value = "select * from merchandise where kind=?1", nativeQuery = true)
    public List<Merchandise> findBykind(String kind);

    @Modifying
    @Query(value = "delete from merchandise where title=?1", nativeQuery = true)
    public void deleteBytitle(String title);
}
