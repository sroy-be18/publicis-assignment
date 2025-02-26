package com.example.publicis.assignment.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Table(name = "user_table")
@Indexed
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GenericField
    private Long id;
    @FullTextField
    private String firstName;
    @FullTextField
    private String lastName;
    private int age;
    private String gender;
    @GenericField
    private String email;
    private String phone;
    private String birthDate;
    @FullTextField
    private String ssn;
}

