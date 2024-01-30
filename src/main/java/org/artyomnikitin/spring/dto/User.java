package org.artyomnikitin.spring.dto;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor

@Getter
@Setter
@ToString


@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq",  allocationSize=1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Integer id;
    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
