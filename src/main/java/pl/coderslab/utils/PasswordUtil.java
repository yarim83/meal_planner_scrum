package pl.coderslab.utils;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dao.AdminDao;

public final class PasswordUtil {
    public static String createHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public static boolean checkPasswordMatch(String password, String storedHash){
        return BCrypt.checkpw(password, storedHash);
    }
}
