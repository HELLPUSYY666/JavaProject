// User Controller
package org.animeapi.controller;

import org.animeapi.model.MyUser;
import org.animeapi.repository.UserRepository;
import org.animeapi.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private UserRepository userRepository;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<MyUser> getUserById(@PathVariable Integer id) {
        return userRepository.getMyUserByUserId(id);
    }

    @PostMapping
    public MyUser createUser(@RequestBody MyUser myUser) {
        return userRepository.save(myUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
}