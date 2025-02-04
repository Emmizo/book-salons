package com.emmizo.controller;

import com.emmizo.exception.UserException;
import com.emmizo.modal.User;
import com.emmizo.repository.UserRepository;
import com.emmizo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

//    @Autowired
//    private UserRepository userRepository;

    private final UserService userService;
    @PostMapping("api/v1/createUser")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){
        User createdUser = userService.createUser(user);
    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//        return userRepository.save(user);
    }
    @GetMapping("api/v1/users")
    public ResponseEntity<List<User>> getUser(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
//    return userRepository.findAll();
    }
    @GetMapping("/api/v1/users/{id}")
public ResponseEntity<User> getUserById(@PathVariable("id") Long id) throws Exception {
        User user=userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
//        Optional<User> otp=userRepository.findById(id);
//        if(otp.isPresent()){
//            return otp.get();
//        }else{
//           throw new Exception("User not found");
//        }
}
    @PutMapping("api/v1/updateUser/{id}")
    public ResponseEntity<User> updateUSer(@RequestBody User user, @PathVariable Long id) throws Exception {
    User updatedUser= userService.updateUser(id, user);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);

        //        Optional<User> otp =userRepository.findById(id);
//        if(otp.isEmpty()){
//            throw new UserException("user not found "+id);
//        }
//      User existingUser = otp.get();
//        existingUser.setFullName(user.getFullName());
//        existingUser.setEmail(user.getEmail());
//        existingUser.setRole(user.getRole());
//        existingUser.setPassword(user.getPassword());
//        return userRepository.save(existingUser);
    }

    @DeleteMapping("api/v1/deleteUser/{id}")
    public ResponseEntity<String> deleteUSer(@PathVariable Long id) throws Exception {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted",HttpStatus.OK);
//        Optional<User> otp = userRepository.findById(id);
//        if(otp.isEmpty()){
//            throw new UserException("User not found "+id);
//        }
//      userRepository.deleteById(id);
//        return "User deleted";
    }
}
