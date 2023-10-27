package com.techproeducation.backendproject.initialwork.controller;

import com.techproeducation.backendproject.initialwork.dto.ContactMessageDTO;
import com.techproeducation.backendproject.initialwork.entity.ContactMessage;

import com.techproeducation.backendproject.initialwork.exceptions.ResourceNotFoundException;
import com.techproeducation.backendproject.initialwork.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/contactmessages")
public class ContactMessageController {
    @Autowired
    private ContactService contactService;
    // Save the new contact message
   @PostMapping
  public ResponseEntity<Map<String, String>> createContactMessage(@Valid @RequestBody ContactMessage contactMessage) {

          contactService.save(contactMessage);

          Map<String, String> message = new HashMap<>();
          message.put("message", "Message has been saved successfully.");
          message.put("status", "true");

          return new ResponseEntity<>(message, HttpStatus.CREATED); // HTTP status 201 (Created)
      }



    //get all contact messages
    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAllContactMessages() {
        List<ContactMessage> messages = contactService.getAllContactMessage();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/query/dto")////http://localhost:8080/contactmessages/query/dto?id=1;

    public ResponseEntity<ContactMessageDTO> getContactMessageByDto(@RequestParam("id") Long id){
        ContactMessageDTO contactDto = contactService.getContactMessageByDTO(id);
        return ResponseEntity.ok(contactDto);
    }

    //get contact messages by page
    @GetMapping("/pagination") // http://localhost:3000/students/pagination?page=0&size=10&sort=name&direction=ASC
    public ResponseEntity<Page<ContactMessage>> getAllMessagesWithPagination(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("direction") Sort.Direction direction)
    {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Page<ContactMessage> pageOfStudents = contactService.getAllStudentsWithPagination(pageable);
        return ResponseEntity.ok(pageOfStudents);}

    //get contact messages by subject
    // @RequestParam: we can get more than one parameter after ? ,@PathVariable: we can get only one parameter
    @GetMapping("/{subject}")
    public ResponseEntity<List<ContactMessage>> getContactMessagesBySubject(@Valid @PathVariable String subject) {
        List<ContactMessage> subject1 = contactService.getContactMessagesBySubject(subject);
        return ResponseEntity.ok(subject1);
    }

    //get contact messages by email parameter
   @GetMapping("/email")
    public ResponseEntity<List<ContactMessage>> getAllByEmail(@Valid @RequestParam String email) {
       List<ContactMessage> getByEmail1 = contactService.getAllByEmail(email);

       return new ResponseEntity<>(getByEmail1, HttpStatus.OK);
    }
    //delete by id (path)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteById (@Valid @PathVariable("id") Long id){
        contactService.deleteMessage(id);
        Map<String, String> map=new HashMap<>();
        map.put("message","Contact message with "+ id+ " has been deleted successfully.");
        map.put("status","true");
        return ResponseEntity.ok(map);
    }
    //delete by id(parameter)
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteByRequestParam(@Valid @RequestParam Long id){
        contactService.deleteMessage(id);
        Map<String, String> map2=new HashMap<>();
        map2.put("message","Contact message with "+ id+ " has been deleted successfully.");
        map2.put("status","true");
        return ResponseEntity.ok(map2);
    }


    //put-update a contact message by id???
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,String>> updateMessageById(@Valid @PathVariable Long id ,@RequestBody ContactMessageDTO contactMessageDTO){
        contactService.updateMessageByIdDTO(id,contactMessageDTO);
        Map<String,String> updateMap=new HashMap<>();
        updateMap.put("message","Contact with id " + id + " has been updated successfully ");
        updateMap.put("status","True");
        return ResponseEntity.ok(updateMap);
    }

    //get contact messages by creation date
//    @GetMapping("/date")
//    public ResponseEntity<List<ContactMessage>> findByCreationDate(@Valid @RequestParam String startDate, @RequestParam String endDate)
//    {
//        LocalDateTime start = LocalDateTime.parse(startDate);
//        LocalDateTime end = LocalDateTime.parse(endDate);
//        List<ContactMessage> messages = contactService.findByCreationDate(start, end);
//        return ResponseEntity.ok(messages);
//    }
//    @GetMapping("/date2")
//    public ResponseEntity<List<ContactMessage>> findByCreationDate2(@RequestParam("startDate")  LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate) {
//        List<ContactMessage> contactMessageList = contactService.findByCreationDate2(startDate, endDate);
//        return ResponseEntity.ok(contactMessageList);

   // }

    //get contact messages by creation time



}







