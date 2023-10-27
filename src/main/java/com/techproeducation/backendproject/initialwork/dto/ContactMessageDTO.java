package com.techproeducation.backendproject.initialwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techproeducation.backendproject.initialwork.entity.ContactMessage;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageDTO {
    @Id
    private Long id;

    @NotNull(message = "Name cannot be null!")
    @NotBlank(message="Name cannot have ne wide space!")
    private String name;

    @Column(nullable = false)
    @Email(message = "Please provide a valid email..")
    private String email;

    @Column(nullable=false)
    private String subject;

    @Column(nullable=false)
    private String message;

    @Setter(AccessLevel.NONE)
    private LocalDateTime contactDate=LocalDateTime.now();


    public ContactMessageDTO(ContactMessage contactMessage) {
    this.id=contactMessage.getId();
    this.name=contactMessage.getName();
    this.email=contactMessage.getEmail();
    this.subject=contactMessage.getSubject();
    this.message=contactMessage.getMessage();
    }
}
