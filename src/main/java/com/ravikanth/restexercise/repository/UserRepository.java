package com.ravikanth.restexercise.repository;

import com.ravikanth.restexercise.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class UserRepository {

    private static List<User> users= new ArrayList<>();
    private static int userCount = 3;

    static {

        users.add(new User(1,"ravikanth",new Date()));
        users.add(new User(2,"sasikanth",new Date()));
        users.add(new User(3,"srikanth",new Date()));
    }

    public List<User> findAll()
    {
        return users;
    }

    public User findUser(int id)
    {
        for(User user:users)
        {
            if(user.getId() == id) return user;
        }
        return null;
    }

    public User addUser(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User deleteById(int id) {
        Iterator<User> usersIterator = users.iterator();
        while (usersIterator.hasNext()) {
            User user = usersIterator.next();
            if (user.getId() == id) {
                usersIterator.remove();
                return user;
            }
        }
        return null;
    }

}




