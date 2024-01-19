package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private  AccountService accountService;
    private  MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
         this.accountService = accountService;
         this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Account accountFromDB = accountService.findByUsername(account.getUsername());
        if(null != accountFromDB){
           return new ResponseEntity<Account>(accountFromDB, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(accountService.save(account), HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account accountFromDB = accountService.findByUsername(account.getUsername());
        if(null != accountFromDB && account.getPassword().equals(accountFromDB.getPassword())){
           return new ResponseEntity<Account>(accountFromDB, HttpStatus.OK);
        }
        return new ResponseEntity<>(accountService.save(account), HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("/messages")
    public ResponseEntity<Message>  createMessage(@RequestBody Message message){
        if(message.getMessage_text().isBlank()){
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        if(message.getMessage_text().length() > 254){
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
        Optional<Account> account = accountService.findById(message.getPosted_by());
        if(!account.isPresent()){
            return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
        }
         return new ResponseEntity<Message>(messageService.save(message), HttpStatus.OK);  
    }
    @GetMapping("/messages")
    public ResponseEntity<List<Message>>  retrieveMessage(){
         List<Message> messages = messageService.getAllMessages();
         return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);  
    }
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message>  findAMessage(@PathVariable("messageId") Integer  messageId){
         Message message = null;
         message = messageService.findMessageById(messageId);
         if(null != message){
            return new ResponseEntity<Message>(message, HttpStatus.OK);  
         }
         return new ResponseEntity<Message>(message, HttpStatus.OK);  
    }

    @GetMapping("/accounts/{posted_by}/messages")
    public ResponseEntity<List<Message>> messagesByAUser(@PathVariable Integer posted_by){
        return new ResponseEntity<List<Message>>(messageService.findAllByPostedBy(posted_by), HttpStatus.OK);

    }
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Object> deleteMessage(@PathVariable Integer id) {
        Message message =  messageService.findById(id);
        Object count = 0;
        if(message!=null){
           count = 1;
           messageService.deleteMessage(id);
        }else{
            count = "";
        }      
        return  new ResponseEntity<Object>(count, HttpStatus.OK);
    }
    

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Object> patchMessage(@RequestBody Message message, @PathVariable Integer  messageId) {
       Message messageExisting = messageService.findById(messageId);
       HttpStatus statusCode = HttpStatus.OK;
       Object count = 0;
       String text = message.getMessage_text();
        if(messageExisting != null && !text.isEmpty() && text.length() <= 255){
             message.setMessage_id(messageId);
            count =  messageService.update(messageId, message.getMessage_text()); 
        }else{
            statusCode = HttpStatus.BAD_REQUEST;
            count = "";
        }
        
        return new ResponseEntity<Object>(count,statusCode);
    }

}
