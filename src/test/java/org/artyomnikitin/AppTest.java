package org.artyomnikitin;


import jakarta.persistence.NoResultException;
import org.artyomnikitin.spring.dao.UserRepositoryImpl;
import org.artyomnikitin.spring.dto.User;

import org.artyomnikitin.spring.dto.UserBody;
import org.artyomnikitin.spring.service.UserService;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.junit.*;


import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;

import org.mockito.junit.MockitoJUnitRunner;




import java.util.List;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AppTest{
    User user = new User("FakeUser", "FakePassword");
    UserBody userBody = new UserBody("FakeUser", "FakePassword", "NewFakePassword");

    User repUser = new User("FakeUser", "FakePassword");//Повторяющийся Логин
    User negativeUser = new User("FakeUser", "WrongFakePassword");//Неправильный Пароль

    User after = new User("FakeUser", "NewFakePassword");//Пользователь после обновления пароля
    UserBody negativeUserBody = new UserBody("FakeUser", "misspelledPass", "NewFakePassword");


    @Mock
    private UserRepositoryImpl userRepositoryImpl;
    @Mock
    Session session;

    @Mock
    Query query;
    @InjectMocks
    UserService userService;

    @Before
    public void setUp(){
        Mockito.when(userService.saveUser(user)).thenReturn(user);//SignUp/SaveUser
        Mockito.when(userService.logInto(user)).thenReturn(user);//LogInto with user
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user.getLogin()));//List of Logins
        Mockito.when(userService.update(userBody)).thenReturn(after);//UPDATE


        Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);

        Mockito.when(userService.saveUser(repUser)).thenThrow(new ConstraintViolationException("ALREADY EXISTS", null, null));//SignUp/SaveUser NEGATIVE
        Mockito.when(userService.logInto(negativeUser)).thenThrow(new NoResultException("WRONG Login/Password"));//LOGINTO NEGATIVE
        Mockito.when(userService.update(negativeUserBody)).thenThrow(new NoResultException("WRONG Login/Password"));//UPDATE



    }

    @After
    public void destroy(){
        userRepositoryImpl.delete(user);
    }

    /**(1) Test SighUp Logic*/
    @Test
    public void testSighUp(){
        assertEquals(user, userRepositoryImpl.save(user));
    }


    /**(2) Test LogIn Logic*/
    @Test
    public void testLogIn(){
        assertEquals(user, userRepositoryImpl.findOne(user));
    }

    /**(3) Test Read-All Logic*/
    @Test
    public void testGetAll(){
        System.out.println(userRepositoryImpl.findAll());
    }
    /**(4) Test Update-Password Logic*/
    @Test
    public void testUpdate(){
        assertEquals(after, userRepositoryImpl.update(userBody));
    }


    /**(1n) Test SighUp Logic*/
    @Test
    public void negativeTestSighUp(){
        ConstraintViolationException thrown = assertThrows(
                ConstraintViolationException.class, () -> userRepositoryImpl.save(repUser));
        assertTrue(thrown.getMessage().contains("ALREADY"));
    }


    /**(2n) Test LogIn Logic*/
    @Test
    public void negativeTestLogIn(){
        NoResultException thrown = assertThrows(
                NoResultException.class, () -> userRepositoryImpl.findOne(negativeUser));
        assertTrue(thrown.getMessage().contains("WRONG"));
    }


    /**(4n) Test Update-Password Logic*/
    @Test
    public void negativeTestUpdate(){
        assertEquals(after, userService.update(userBody));
    }

}
