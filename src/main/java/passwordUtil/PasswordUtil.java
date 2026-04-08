package passwordUtil;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hash(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }
    public static boolean verify(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}