package com.etcr.demo.comment;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {
    @Query(value = "SELECT * from comment where itemid=?1",nativeQuery = true)
    public Iterable<Comment> searchByItem(String itemid);
}
