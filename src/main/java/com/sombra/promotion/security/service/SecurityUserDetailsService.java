package com.sombra.promotion.security.service;

import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.repository.manyToMany.UserRoleRepository;
import com.sombra.promotion.security.model.SecurityAuthority;
import com.sombra.promotion.security.model.SecurityUser;
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

        User user = userRepository.requiredByUsername(username);
        List<Role> roles = userRoleRepository.findByFirstId(user.getId());

        return new SecurityUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                roles.stream()
                        .map(Role::getName)
                        .map(SecurityAuthority::new)
                        .collect(toList())
        );
    }

}
