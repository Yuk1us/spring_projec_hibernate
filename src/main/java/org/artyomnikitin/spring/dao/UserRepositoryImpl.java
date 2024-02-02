package org.artyomnikitin.spring.dao;


import org.artyomnikitin.spring.dto.User;
import org.artyomnikitin.spring.dto.UserBody;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository<User>{

    private final SessionFactory sessionFactory;
    @Autowired
    public UserRepositoryImpl(final SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    /**Saves new User to DataBase
     * @return User user
     * @throws org.hibernate.exception.ConstraintViolationException if login already exists
     * */
    @Override
    public User save(User user){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }

        return user;
    }
    /**@param user User to Find
     * @return ResponseBody{User user}
     * @throws jakarta.persistence.NoResultException if no matches where found*/
    @Override
    public User findOne(User user) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM User WHERE login =: userLogin AND password= :userPassword", User.class)
                .setParameter("userLogin", user.getLogin())
                .setParameter("userPassword", user.getPassword())
                .getSingleResult();

    }

    /**Select every entry
     * @return All Users inside DB even if Size is 0*/
    @Override
    public Iterable<User> findAll() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("FROM User ", User.class).getResultList();
        }
    }


    /**Updates Password if login/password is correct
     * @param obj -masked UserBody{login, oldPass, newPass}
     * @return User user with new Password
     * @throws jakarta.persistence.NoResultException if no matches where found*/
    @Override
    public User update(Object obj) {
        UserBody userBody = (UserBody) obj;
        Session session = sessionFactory.getCurrentSession();
        User user = session.createQuery("FROM User WHERE login= :userLogin AND password= : userOldPassword", User.class)
                .setParameter("userLogin", userBody.getLogin())
                .setParameter("userOldPassword", userBody.getOldPassword())
                .getSingleResult();
        user.setPassword(userBody.getNewPassword());
        return user;
    }



    /**Should Delete One User from DB
     * @return TRUE if Deleted or
     * @throws jakarta.persistence.NoResultException if no matches*/
    @Override
    public boolean delete(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(findOne(user));
        transaction.commit();
        session.close();
        return true;
    }

}
