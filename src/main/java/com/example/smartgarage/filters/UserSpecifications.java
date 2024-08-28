package com.example.smartgarage.filters;

import com.example.smartgarage.models.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {


    public static Specification<User> username(String username) {
        return (root, query, cb) -> cb.equal(root.get("username"), username);
    }

    public static Specification<User> email(String email) {
        return (root, query, cb) -> cb.equal(root.get("email"), email);
    }

    public static Specification<User> phoneNumber(String phoneNumber) {
        return (root, query, cb) -> cb.equal(root.get("phoneNumber"), phoneNumber);
    }
}
