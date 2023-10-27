package com.techproeducation.backendproject.initialwork.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="US")
    @Column(name= "date_time")
    private LocalDateTime contactDate=LocalDateTime.now();


//     public TemporalAccessor getCreationDateTime() {
//    }
}
