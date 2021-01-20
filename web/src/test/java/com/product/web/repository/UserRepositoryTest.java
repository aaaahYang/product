package com.product.web.repository;


import com.product.dao.repository.UserRepository;
import com.product.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired(required = true)
    UserRepository repository;

    @Test
    public void saveUser(){

        Optional<User> userOptional = repository.findById(1);
        User user = userOptional.get();

        user.setUserAccount("admin");
        user.setUserName("管理员");
        user.setUserPwd("1234");
        repository.save(user);

    }


}
