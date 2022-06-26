package com.sombra.promotion.service;

import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllUsersService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.selectAllUsers();
    }

}
