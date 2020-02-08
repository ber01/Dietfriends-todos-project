package me.kyunghwan.todos.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Description("User 객체 저장 후 불러오는 테스트")
    public void userSaveAndLoadTest() {
        // given
        String email = "email@email.com";
        String password = "password";
        String name = "name";

        User user = userRepository.save(User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());

        // when
        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("없음"));

        // then
        assertThat(findUser.getEmail()).isEqualTo(email);
        assertThat(findUser.getPassword()).isEqualTo(password);
        assertThat(findUser.getName()).isEqualTo(name);
    }

}