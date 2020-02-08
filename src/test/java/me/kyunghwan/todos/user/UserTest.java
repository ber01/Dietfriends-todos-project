package me.kyunghwan.todos.user;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    public void userTest() {
        String email = "email@email.com";
        String password = "password";
        String name = "name";

        User user = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getName()).isEqualTo(name);
    }

}