package org.acme.service;


import org.acme.dao.User;

import java.util.List;
import java.util.Optional;


public interface UsersService {

    public List<User> list();
    public User create (User user);
    public Optional<User> findByUsername(String username);


}