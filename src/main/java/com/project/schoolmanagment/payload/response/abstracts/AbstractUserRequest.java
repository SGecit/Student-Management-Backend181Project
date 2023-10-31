package com.project.schoolmanagment.payload.response.abstracts;

import com.project.schoolmanagment.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractUserRequest {

    private String username;

    private String name;

    private String surname;

    private LocalDate birthDay;

    private String ssn;

    private String birthPlace;

    private String phoneNumber;

    private Gender gender;

    private String email;



}
