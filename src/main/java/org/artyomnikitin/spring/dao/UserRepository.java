package org.artyomnikitin.spring.dao;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.artyomnikitin.spring.dto.User;
import org.artyomnikitin.spring.dto.UserBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**1.1v Only Repository Needed*/

public interface UserRepository<T>{
    T save(T entity);

    T findOne(T entity);

    Iterable<T> findAll();

    T update(Object userBody);



    boolean delete(T entity);
}
