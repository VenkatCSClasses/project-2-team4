package service;

import database.UserRepo;
import model.User;
import passwordUtil.PasswordUtil;


public class Authentication {
    private final UserRepo userRepo;

    public Authentication(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User login(String username, String password) throws Exception {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new Exception("User not found"));
        if (!PasswordUtil.verify(password, user.getPasswordHash())) {
            throw new Exception("Incorrect password");
        }
        return user;
    }

    public User register(String username, String password) throws Exception {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new Exception("Username already taken");
        }
        User newUser = new User(0, username, PasswordUtil.hash(password), "USER");
        userRepo.save(newUser);
        return userRepo.findByUsername(username).get();
    }
}
