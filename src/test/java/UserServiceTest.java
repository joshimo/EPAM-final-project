import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.*;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.Timestamp;
import static org.junit.Assert.*;

public class UserServiceTest {

    private static User correctUser;
    private static User uncorrectUser;
    private static User testUser;
    private final static String CORRECT_USER_NAME = "Yaroslav";
    private final static String CORRECT_USER_PASSWORD = "yaroslav";
    private final static String TEST_USER_NAME = "Somebody";
    private final static String TEST_USER_PASSWORD = "Else";

    private static final Logger log = Logger.getLogger(UserServiceTest.class);


    @BeforeClass
    public static void init() throws UnknownUserException  {
        log.info("Starting tests");
        correctUser = UserService.findUser(CORRECT_USER_NAME, CORRECT_USER_PASSWORD);
        uncorrectUser = UserService.findUser(CORRECT_USER_NAME, CORRECT_USER_PASSWORD);
        testUser = createTestUser();
    }

    @AfterClass
    public static void close() throws ProductServiceException {
        try {
            //UserService.deleteUser(testUser);
        } catch (Exception e) {}
        log.info("Finishing tests");
        correctUser = null;
        uncorrectUser = null;
        System.gc();
    }

    private static User createTestUser() {
        User user = new User(TEST_USER_NAME, TEST_USER_PASSWORD);
        user.setUserRole(UserRole.USER);
        user.setNotes("Created by " + UserService.class.getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        return user;
    }

    /** User Validator tests */

    @Test
    public void testUserValidator() {
        assertTrue(UserService.validateUserData(correctUser));
    }

    @Test
    public void testUserValidator_1() {
        uncorrectUser.setName(null);
        assertFalse(UserService.validateUserData(uncorrectUser));
    }

    @Test
    public void testUserValidator_2() {
        uncorrectUser.setName("");
        assertFalse(UserService.validateUserData(uncorrectUser));
    }

    @Test
    public void testUserValidator_3() {
        uncorrectUser.setPassword(null);
        assertFalse(UserService.validateUserData(uncorrectUser));
    }

    @Test
    public void testUserValidator_4() {
        uncorrectUser.setPassword("");
        assertFalse(UserService.validateUserData(uncorrectUser));
    }

    @Test
    public void testUserValidator_5() {
        uncorrectUser.setUserRole(null);
        assertFalse(UserService.validateUserData(uncorrectUser));
    }

    /** Product CRUD operations tests */
    @Test
    public void testFindUser1() throws UnknownUserException {
        User user = UserService.findUser(CORRECT_USER_NAME, CORRECT_USER_PASSWORD);
        assertTrue(user.getName().equals("Yaroslav"));
    }

    @Test(expected = UnknownUserException.class)
    public void testFindUser2() throws UnknownUserException {
        User user = UserService.findUser(CORRECT_USER_NAME, "..");
        assertNotNull(user);
    }

    @Test
    public void testAddUser() throws InterruptedException {
        boolean result  = UserService.addUser(testUser);
        assertTrue(result);
    }

    @Test
    public void testUpdateUser() throws InterruptedException, UnknownUserException {
        User user = UserService.findUser(TEST_USER_NAME, TEST_USER_PASSWORD);
        user.setName("test");
        user.setPassword("test");
        user.setUserRole(UserRole.ADMIN);
        user.setNotes("Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        boolean result  = UserService.updateUser(user);
        User updatedUser = UserService.findUser("test", "test");
        if (result) {
            user.setName(testUser.getName());
            user.setPassword(testUser.getPassword());
            user.setUserRole(testUser.getUserRole());
            UserService.updateUser(user);
        }
        assertTrue(result && updatedUser.getName().equals("test") && updatedUser.getPassword().equals("test"));
    }

    @Test
    public void testDeleteUser() throws InterruptedException {
        boolean result  = UserService.deleteUser(testUser);
        assertTrue(result);
    }
}
