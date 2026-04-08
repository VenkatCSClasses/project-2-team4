import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import model.User;
import database.DatabaseManager;
import database.UserRepo;
import passwordUtil.PasswordUtil;


public class UserRepoTest {

    private UserRepo userRepo;

    @BeforeEach
    void setup() throws Exception {
        // fresh in-memory DB for every test
        Connection memConn = DriverManager.getConnection("jdbc:sqlite::memory:");
        DatabaseManager.setConnection(memConn);
        DatabaseManager.initialize(); // creates tables
        userRepo = new UserRepo();
    }

    @Test
    void saveAndFindUser() throws Exception {
        User user = new User(0, "alice", PasswordUtil.hash("pass"), "USER");
        userRepo.save(user);

        Optional<User> found = userRepo.findByUsername("alice");
        assertTrue(found.isPresent());
        assertEquals("alice", found.get().getUsername());
        assertEquals("USER", found.get().getRole());
    }

    @Test
    void findByUsernameReturnsEmptyIfNotFound() throws Exception {
        Optional<User> found = userRepo.findByUsername("nobody");
        assertFalse(found.isPresent());
    }

    @Test
    void findAllReturnsAllUsers() throws Exception {
        userRepo.save(new User(0, "alice", "hash1", "USER"));
        userRepo.save(new User(0, "bob", "hash2", "LIBRARIAN"));

        List<User> users = userRepo.findAll();
        assertEquals(2, users.size());
    }

    @Test
    void duplicateUsernameThrows() {
        assertThrows(Exception.class, () -> {
            userRepo.save(new User(0, "alice", "hash1", "USER"));
            userRepo.save(new User(0, "alice", "hash2", "USER"));
        });
    }
}