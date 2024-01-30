package org.artyomnikitin.spring.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.artyomnikitin.spring.dao.UserRepository;
import org.artyomnikitin.spring.dao.UserRepositoryImpl;
import org.artyomnikitin.spring.dto.User;
import org.artyomnikitin.spring.dto.UserBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepositoryImpl userRepositoryImpl;
    @Autowired
    public UserService(final UserRepositoryImpl userRepositoryImpl){
        this.userRepositoryImpl = userRepositoryImpl;
    }


    /**Create
     * <br>
     * Регистрация пользователя в сети
     * */
    @Transactional
    public User saveUser(User user){
        return userRepositoryImpl.save(user);
    }


    /**Create
     * <br>
     * Вход пользователя в систему
     * @return 1.1v Now Returns user OR
     * @throws EntityNotFoundException if login/password is wrong*/
    @Transactional
    public User logInto(User user){
        return userRepositoryImpl.findOne(user);
    }


    /**Read All Users
     * <br>
     * @return User's Login from DB*/
    @Transactional
    public List<String> getAllUsers(){
        List<String> logins = new ArrayList<>();
        userRepositoryImpl.findAll().forEach(us -> {
            logins.add(us.getLogin());
        });
        return logins;
    }


    /**Update
     * <br>
     * Update Password
     * <br>
     * 1.1v Updated Logic
     * @return True if the is changes in DB*/
    @Transactional
    public User update(UserBody userBody){
        return userRepositoryImpl.update(userBody);
    }



    /**Delete
     * <br>
     * Delete User from DB
     * @return True if the is changes in DB*/
    @Transactional
    public String deleteUser(User user){
        userRepositoryImpl.delete(user);
        return String.format("User: %s No Longer Exists", user.getLogin());
    }
}
