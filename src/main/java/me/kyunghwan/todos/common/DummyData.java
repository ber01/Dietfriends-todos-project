package me.kyunghwan.todos.common;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.todos.user.User;
import me.kyunghwan.todos.user.UserRepository;
import me.kyunghwan.todos.user.UserRole;
import me.kyunghwan.todos.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DummyData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();

        User user = User.builder()
                .email("diet@email.com")
                .password("friends")
                .name("khmin")
                .roles(UserRole.USER)
                .build();

        userService.saveUser(user);

        User dummy = User.builder()
                .email("dummy@email.com")
                .password("dummy")
                .name("dummy")
                .roles(UserRole.USER)
                .build();

        userService.saveUser(dummy);
    }

}
