package com.techproeducation.backendproject.initialwork.service;

import com.techproeducation.backendproject.initialwork.dto.ContactMessageDTO;
import com.techproeducation.backendproject.initialwork.entity.ContactMessage;
import com.techproeducation.backendproject.initialwork.exceptions.ConflictException;
import com.techproeducation.backendproject.initialwork.exceptions.ResourceNotFoundException;
import com.techproeducation.backendproject.initialwork.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactMessage save(@Valid ContactMessage contactMessage) {
        ContactMessage existingContact = contactRepository.getByEmail(contactMessage.getEmail());

        if (existingContact != null && !existingContact.getId().equals(contactMessage.getId())) {
            throw new ConflictException("Email already exists for another user");
        }

        return contactRepository.save(contactMessage);
    }




    public List<ContactMessage> getAllContactMessage() {
        List<ContactMessage> messagesList = contactRepository.findAll();
        return messagesList;
    }

    public List<ContactMessage> getContactMessagesBySubject(String subject) {
        List<ContactMessage> sbj1 = contactRepository.findBySubject(subject);
        if (sbj1.isEmpty()) {
            throw new ResourceNotFoundException("Subject " + subject + " does not exist.");
        }
        return sbj1;
    }
    public List<ContactMessage> getAllByEmail(String email) {
        List<ContactMessage> contactMessages = contactRepository.findAllByEmail(email);

        if (contactMessages.isEmpty()) {
            return null;
        } else if (contactMessages.size() == 1) {
            return contactMessages;
        } else {
            throw new ConflictException("Multiple contacts found with email: " + email);
        }
    }

//    public List<ContactMessage> getAllByEmail(String email) {
//        List<ContactMessage> findEmail = contactRepository.findAllByEmail(email);
//                if (findEmail.isEmpty()){
//                    throw new ResourceNotFoundException("Email " + email + " does not exist.");
//                };
//        return findEmail;}
//
//    public Page<ContactMessage> getAllContactMessagesWithPagination(Pageable pageable) {
//        return contactRepository.findAll(pageable);
//    }

    public void deleteMessage(Long id) {
        ContactMessage contactMessage = getContactMessageById(id);
            contactRepository.deleteById(id);


        }


        public void updateMessageById (Long id, ContactMessage contactMessage){
            ContactMessage updateEmail = contactRepository.getByEmail(contactMessage.getEmail());
            if (updateEmail != null && updateEmail.getId().equals(contactMessage.getId())) {
                throw new ConflictException("Email already exists for another user");
            }
        updateEmail.setName(contactMessage.getName());
        updateEmail.setEmail(contactMessage.getEmail());
        updateEmail.setSubject(contactMessage.getSubject());
        updateEmail.setMessage(contactMessage.getMessage());
        contactRepository.save(updateEmail);}



        public Page<ContactMessage> getAllStudentsWithPagination (Pageable pageable){
            return contactRepository.findAll(pageable);
        }

    public void updateMessageByIdDTO(Long id, ContactMessageDTO contactMessage) {
        ContactMessage existingContact = getContactMessageById(id);
        ContactMessage updateEmail = contactRepository.getByEmail(contactMessage.getEmail());
        if (updateEmail != null && !updateEmail.getId().equals(id)) {
            throw new ConflictException("Email already exists for another user");
        }
        existingContact.setName(contactMessage.getName());
        existingContact.setEmail(contactMessage.getEmail());
        existingContact.setSubject(contactMessage.getSubject());
        existingContact.setMessage(contactMessage.getMessage());

        contactRepository.save(existingContact);
    }


    private ContactMessage getContactMessageById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Contact message not found with "+ id));
    }

    public ContactMessageDTO getContactMessageByDTO(Long id) {

        return contactRepository.findContactMessageByDTO(id).
                orElseThrow(()-> new ResourceNotFoundException("The contact whose id " +id + " not found "));

    }
}



//    public List<ContactMessage> findByCreationDate(LocalDateTime start, LocalDateTime end) {
//        List<ContactMessage> allMessages = getAllContactMessage();
//        List<ContactMessage> betweenDateRange = new ArrayList<>();
//        for (ContactMessage each : allMessages) {
//            LocalDate eachCreationDate = LocalDate.from(each.getCreationDateTime());
//            if (eachCreationDate.isAfter(ChronoLocalDate.from(start)) && eachCreationDate.isBefore(ChronoLocalDate.from(end))) {
//                betweenDateRange.add(each);
//            }
//        }
//        if (betweenDateRange.isEmpty()) {
//            throw new ResourceNotFoundException("No messages is found between " + start + " and " + end);
//        }
//        return betweenDateRange;
//    }
//


//    public List<ContactMessage> findByCreationDate2(LocalDateTime startDate, LocalDateTime endDate) {
//            LocalDateTime startDateTime = startDate.atStartOfDay();
//            LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
//            return contactRepository.findByCreationDateTimeBetween(startDateTime, endDateTime);
//        }








