package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
 
    private MessageRepository  messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
         this.messageRepository = messageRepository;
    }

    public Message save(Message message){
      Message mess = messageRepository.save(message);
      System.out.println("+++++++++++++++++++++++++++++++++"+mess + "---------------------------@@@@@@@@@@@@@@@@@@");
       return mess;

    }
    public Integer update(Integer messageId, String messageText){
        return  messageRepository.updateMessage(messageId,messageText);
    }
    public List<Message> findAllByPostedBy(Integer postedBy){
        return messageRepository.findMessagesByPostedBy(postedBy);
    }

    public Message findById(Integer id){
        return messageRepository.findById(id).isPresent() ? messageRepository.findById(id).get() : null ;
    }
    public List<Message>  getAllMessages(){
        return messageRepository.findAll();
    }
    public void deleteMessage(Integer id) {
         messageRepository.deleteById(id);
    }
    public Message  findMessageById(Integer messageId){
        return messageRepository.findById(messageId).isPresent() ? messageRepository.findById(messageId).get() : null;

    }

    
}
