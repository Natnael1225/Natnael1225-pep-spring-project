package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Integer> {
    
    Message save(Message message);
    
    @Query("SELECT m FROM Message m WHERE m.posted_by = :postedBy")
    List<Message> findMessagesByPostedBy(Integer postedBy);


}
