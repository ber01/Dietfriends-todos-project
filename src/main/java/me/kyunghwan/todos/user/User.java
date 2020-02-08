package me.kyunghwan.todos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kyunghwan.todos.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.Set;

@Getter
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
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @Builder
    public User(String email, String password, String name, Set<UserRole> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }
}
