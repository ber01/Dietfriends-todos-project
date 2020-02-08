package me.kyunghwan.todos.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Description("정상적으로 유저를 불러오는 테스트")
    public void findByUsername() {
        // given
        String email = "email@email.com";
        String password = "password";
        String name = "name";

        this.userService.saveUser(User.builder()
                .email(email)
                .password(password)
                .name(name)
                .roles(UserRole.USER)
                .build());

        // when
        UserDetailsService userDetailsService = userService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // then
        assertThat(userDetails.getUsername()).isEqualTo(email);
        assertThat(this.passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test(expected = UsernameNotFoundException.class)
    @Description("없는 유저를 불러오면 실패하는 테스트")
    public void findByUsernameFail() {
        String email = "fail@email.com";
        userService.loadUserByUsername(email);
    }

}