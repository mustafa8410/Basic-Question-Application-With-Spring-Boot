package com.project.questapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    long id;

    @ManyToOne (fetch = FetchType.LAZY) //many posts for one user
    @JoinColumn(name = "user_id", nullable = false) //foreign keyler bu şekilde saklanabilir
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;

    String title;
    @Lob //large object
    @Column (columnDefinition = "text") //it's a text, not varchar(255)
    String text;
}
