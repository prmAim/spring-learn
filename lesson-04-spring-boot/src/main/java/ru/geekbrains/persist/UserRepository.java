package ru.geekbrains.persist;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final Map<Long, User> userMap = new ConcurrentHashMap<>();

    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        this.save(new User( "User 1"));
        this.save(new User( "User 2"));
        this.save(new User( "User 3"));
        this.save(new User( "User 4"));
        this.save(new User( "User 5"));
    }

    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    public User findById(long id) {
        return userMap.get(id);
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(identity.incrementAndGet());
        }
        userMap.put(user.getId(), user);
        return user;
    }

    public void delete(long id) {
        userMap.remove(id);
    }

}
