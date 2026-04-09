
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import service.Authentication;
import database.DatabaseManager;
import database.UserRepo;
import model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {

    private Authentication authService;

    @BeforeEach
    void setup() throws Exception {
        Connection memConn = DriverManager.getConnection("jdbc:sqlite::memory:");
        DatabaseManager.setConnection(memConn);
        DatabaseManager.initialize();
        authService = new Authentication(new UserRepo());
    }

    @Test
    void registerAndLoginSucceeds() throws Exception {
        authService.register("alice", "password123");
        User user = authService.login("alice", "password123");
        assertEquals("alice", user.getUsername());
        assertEquals("USER", user.getRole());
    }

    @Test
    void loginWithWrongPasswordThrows() throws Exception {
        authService.register("alice", "password123");
        assertThrows(Exception.class, () -> {
            authService.login("alice", "wrongpassword");
        });
    }

    @Test
    void loginWithUnknownUserThrows() {
        assertThrows(Exception.class, () -> {
            authService.login("nobody", "password");
        });
    }

    @Test
    void duplicateRegisterThrows() throws Exception {
        authService.register("alice", "password123");
        assertThrows(Exception.class, () -> {
            authService.register("alice", "otherpassword");
        });
    }

    @Test
    void registeredUserIsNotLibrarian() throws Exception {
        authService.register("alice", "password123");
        User user = authService.login("alice", "password123");
        assertFalse(user.isLibrarian());
    }
}