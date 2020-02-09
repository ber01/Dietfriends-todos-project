package me.kyunghwan.todos.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class UserAdapter extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserAdapter(User user) {
        super(user.getEmail(), user.getPassword(), authorities(user.getRoles()));
        this.user = user;
    }

    private static Collection<? extends GrantedAuthority> authorities(UserRole r) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + r));
        return roles;
    }

}
