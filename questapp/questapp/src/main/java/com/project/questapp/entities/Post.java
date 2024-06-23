package com.project.questapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment id's in the database
    long id;

    @ManyToOne (fetch = FetchType.EAGER) //many posts for one user
    @JoinColumn(name = "user_id", nullable = false) //foreign keyler bu şekilde saklanabilir
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

    String title;
    @Lob //large object
    @Column (columnDefinition = "text") //it's a text, not varchar(255)
    String text;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;
}
