package com.techproeducation.backendproject.initialwork.repository;

import com.techproeducation.backendproject.initialwork.dto.ContactMessageDTO;
import com.techproeducation.backendproject.initialwork.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage> findBySubject(String subject);



    ContactMessage save(ContactMessage contactMessage);

    List<ContactMessage> findAllByEmail(String email);

    ContactMessage getByEmail(String email);

    @Query("SELECT new com.techproeducation.backendproject.initialwork.dto.ContactMessageDTO (c) FROM ContactMessage c WHERE c.id=:id")
    Optional<ContactMessageDTO> findContactMessageByDTO(@Param("id") Long id);




    //List<ContactMessage> findByCreationDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

}
