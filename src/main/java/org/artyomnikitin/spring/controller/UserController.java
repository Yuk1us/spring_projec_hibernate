package org.artyomnikitin.spring.controller;


import org.artyomnikitin.spring.dto.User;
import org.artyomnikitin.spring.dto.UserBody;
import org.artyomnikitin.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService hibernateService) {
        this.userService = hibernateService;
    }


    /**
     * Create
     * <br>
     * Регистрация пользователя в сети
     */
    @PostMapping("/signUp")
    public User signUpUser(@RequestBody User user){
        return userService.saveUser(user);
    }


    /**
     * Create
     * <br>
     * Вход пользователя в систему
     */
    @PostMapping("/logInto")
    public User logInto(@RequestBody User user){
        return userService.logInto(user);
    }


    /**Read All Users
     * <br>
     * @return LOGIN всех пользователей в системе*/
    @GetMapping("/users")
    @RequestMapping("/users")
    public List<String> getAllUsers(){
        return userService.getAllUsers();
    }


    /**
     * Update
     * <br>
     * Update Password
     */
    @PutMapping("/update")
    public User changePassword(@RequestBody UserBody userBody){
        return userService.update(userBody);
    }



    /**Update
     * <br>
     * Update Password*/
    @DeleteMapping("/delete")
    public String deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

}
