package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Integer> {
    
    Message save(Message message);
    
    @Query("SELECT m FROM Message m WHERE m.posted_by = :postedBy")
    List<Message> findMessagesByPostedBy(Integer postedBy);

    @Query("DELETE FROM Message m WHERE m.id = :id")
    void deleteMessageById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.message_text = :message_text WHERE m.message_id = :message_id")
    int updateMessage(@Param("message_id") Integer message_id, @Param("message_text") String message_text);


}
