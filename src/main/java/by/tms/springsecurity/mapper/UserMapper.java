package by.tms.springsecurity.mapper;

import by.tms.springsecurity.entity.User;
import by.tms.springsecurity.entity.UserRoleEnum;
import by.tms.springsecurity.entity.UserStatusEnum;
import by.tms.springsecurity.model.UserRegistrationModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserMapper(@Lazy PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public User convertToUser(UserRegistrationModel userRegistrationModel) {
        User user = modelMapper.map(userRegistrationModel, User.class);
        user.setPassword(passwordEncoder.encode(userRegistrationModel.getPassword()));
        user.setRoles(Stream.of(UserRoleEnum.USER).collect(Collectors.toSet()));
        user.setStatus(UserStatusEnum.ACTIVE);
        return user;
    }
}
