package com.project.questapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity //it's an entity in the database
@Table(name="user") //this table is created in the database
@Data //getters and setters are generated automatically
public class User {
    @Id
    Long id;
    String userName;
    String password;
}
