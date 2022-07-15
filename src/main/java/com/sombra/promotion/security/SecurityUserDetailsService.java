package com.sombra.promotion.security;

import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.repository.UserRoleRepository;
import com.sombra.promotion.tables.pojos.Role;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.selectUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);

        List<Role> roles = userRoleRepository.selectRolesByUsername(username);

        return new SecurityUser(
                user.getUsername(),
                user.getPassword(),
                roles.stream().map(SecurityRole::new).collect(toList()),
                user.getSalt()
        );
    }

}
