package com.example.ticket.ticket.rest;

import com.example.ticket.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getCurrentUser() {
        return User.builder().id(2L).build();
    }
}
