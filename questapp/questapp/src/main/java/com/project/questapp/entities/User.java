package com.project.questapp.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity //it's an entity in the database
@Table(name="user") //this table is created in the database
@Data //getters and setters are generated automatically
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment id's in the database
    Long id;
    String userName;
    String password;
    int avatar;
}
