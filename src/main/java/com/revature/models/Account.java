package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "accounts")
@Data
@ToString(exclude= {"user"})
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    Double balance;

    @Lob
    String description;
    Instant creationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    User user;

    public Account(String name, String description, Instant creationDate,
                       User user) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.user = user;
    }
}
