import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.dao.IUserDao;
import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.*;
import com.epam.project.logic.UserLogic;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserLogicTest {

    private static User correctUser;
    private static User uncorrectUser;
    private final static String TEST_USER_NAME = "Somebody";

    @BeforeClass
    public static void init() throws
            DataBaseNotSupportedException,
            IncorrectPropertyException,
            DataBaseConnectionException,
            DataNotFoundException {
    }

    private User createTestUser() {
        User user = new User("Somebody", "Else");
        user.setUserRole(UserRole.USER);
        user.setNotes("Created by " + this.getClass().getSimpleName());
        return user;
    }

    /** User Validator tests */

    @Test
    public void testUserValidator() {
        assertTrue(UserLogic.validateUserData(correctUser));
    }

    @Test
    public void testUserValidator_1() {
        uncorrectUser.setName(null);
        assertFalse(UserLogic.validateUserData(uncorrectUser));
    }

    @Test
    public void testUserValidator_2() {
        uncorrectUser.setName("");
        assertFalse(UserLogic.validateUserData(uncorrectUser));
    }

    @Test
    public void testUserValidator_3() {
        uncorrectUser.setPassword(null);
        assertFalse(UserLogic.validateUserData(uncorrectUser));
    }

    @Test
    public void testUserValidator_4() {
        uncorrectUser.setPassword("");
        assertFalse(UserLogic.validateUserData(uncorrectUser));
    }

    @Test
    public void testUserValidator_5() {
        uncorrectUser.setUserRole(null);
        assertFalse(UserLogic.validateUserData(uncorrectUser));
    }

    /** Product CRUD operations tests */
    @Test
    public void testFindUser() throws DataBaseNotSupportedException,
            IncorrectPropertyException,
            DataBaseConnectionException,
            UnknownUserException {
        User user = UserLogic.findUser("Yaroslav", "yaroslav");
        assertTrue(user.getName().equals("Yaroslav"));
    }
}
