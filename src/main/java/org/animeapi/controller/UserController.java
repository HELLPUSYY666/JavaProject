package org.animeapi.controller;

import org.animeapi.model.MyUser;
import org.animeapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<MyUser> getAllUsers() {
        return userService.getAllUsers(); // <-- добавлен метод в сервис
    }

    @GetMapping("/{id}")
    public Optional<MyUser> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id); // <-- добавлен метод в сервис
    }

    @PostMapping
    public MyUser createUser(@RequestBody MyUser myUser) {
        return userService.saveUser(myUser); // <-- используем сервис
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id); // <-- добавлен метод в сервис
    }
}
