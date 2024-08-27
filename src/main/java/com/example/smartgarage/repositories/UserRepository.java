package com.example.smartgarage.repositories;

import com.example.smartgarage.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> getAll();

    User getById(int id);

    User getByUsername(String username);

    User create(User user);

    void delete(int id);

    User makeEmployee(int userId);

    User removeEmployee(int userId);


}
