
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import passwordUtil.PasswordUtil;
public class PasswordUtilTest {

    @Test
    void hashIsNotPlaintext() {
        String hash = PasswordUtil.hash("mypassword");
        assertNotEquals("mypassword", hash);
    }

    @Test
    void correctPasswordVerifies() {
        String hash = PasswordUtil.hash("mypassword");
        assertTrue(PasswordUtil.verify("mypassword", hash));
    }

    @Test
    void wrongPasswordFails() {
        String hash = PasswordUtil.hash("mypassword");
        assertFalse(PasswordUtil.verify("wrongpassword", hash));
    }

    @Test
    void samePasswordGivesDifferentHashes() {
        // BCrypt uses a random salt so two hashes of the same
        // password should never be identical
        String hash1 = PasswordUtil.hash("mypassword");
        String hash2 = PasswordUtil.hash("mypassword");
        assertNotEquals(hash1, hash2);
    }
}