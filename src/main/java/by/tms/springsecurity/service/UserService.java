package by.tms.springsecurity.service;

import by.tms.springsecurity.entity.User;
import by.tms.springsecurity.model.UserRegistrationModel;
import by.tms.springsecurity.service.exception.UsernameIsBusyException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    void create(UserRegistrationModel userRegistrationModel) throws UsernameIsBusyException;
    Optional<User> getUser(long id);
}
