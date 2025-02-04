package com.emmizo.service;

import com.emmizo.exception.UserException;
import com.emmizo.modal.User;
import com.emmizo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws Exception {
        Optional<User> otp=userRepository.findById(id);
        if(otp.isPresent()){
            return otp.get();
        }else{
            throw new Exception("User not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) throws UserException {
        Optional<User> otp = userRepository.findById(id);
        if(otp.isEmpty()){
            throw new UserException("User not found "+id);
        }
        userRepository.deleteById(otp.get().getId());

    }

    @Override
    public User updateUser(Long id, User user) throws UserException {
        Optional<User> otp =userRepository.findById(id);
        if(otp.isEmpty()){
            throw new UserException("user not found "+id);
        }
        User existingUser = otp.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }
}
