package by.tms.springsecurity.service;

import by.tms.springsecurity.entity.User;
import by.tms.springsecurity.mapper.UserMapper;
import by.tms.springsecurity.model.UserRegistrationModel;
import by.tms.springsecurity.repository.UserRepository;
import by.tms.springsecurity.service.exception.UsernameIsBusyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> fundUser = userRepository.findUserByUsername(username);
        return fundUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void create(UserRegistrationModel userRegistrationModel) throws UsernameIsBusyException {
        User user = userMapper.convertToUser(userRegistrationModel);
        System.out.println(user);
        if (userRepository.findUserByUsername(user.getUsername()).isPresent()) throw new UsernameIsBusyException();
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }
}
