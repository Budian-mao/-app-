package com.etcr.demo.message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, String > {
    @Query(value = "select send_id from message where receive_id=?1",nativeQuery = true)
    public List<String> searchBySend(String id);

    @Query(value = "select receive_id from message where send_id=?1",nativeQuery = true)
    public List<String> searchByReceive(String id);

    @Query(value = "select * from message where ((send_id=?1 and receive_id=?2) OR (send_id=?2 and receive_id=?1)) ORDER BY time",nativeQuery = true)
    public List<Message> searchBychat(String id1, String id2);
}
