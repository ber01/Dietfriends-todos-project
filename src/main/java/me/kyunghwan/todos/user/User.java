package me.kyunghwan.todos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kyunghwan.todos.common.BaseTimeEntity;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Table(name = "USERS")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole roles;

    @Builder
    public User(String email, String password, String name, UserRole roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

}
