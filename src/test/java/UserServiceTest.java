import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.*;
import com.epam.project.service.IUserServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import java.sql.Timestamp;
import static org.junit.Assert.*;

public class UserServiceTest {

    private static IUserServ userService;
    private static User testUser;
    private final static String CORRECT_USER_NAME = "Yaroslav";
    private final static String CORRECT_USER_PASSWORD = "yaroslav";
    private final static String TEST_USER_NAME = "Somebody";
    private final static String TEST_USER_PASSWORD = "Else";

    private static final Logger log = Logger.getLogger(UserServiceTest.class);


    @BeforeClass
    public static void init() throws UnknownUserException  {
        log.info("Starting tests");
        userService = ServiceFactory.getUserService();
        testUser = createTestUser();
    }

    @AfterClass
    public static void close() throws ProductServiceException {
        try {
            userService.deleteUser(testUser);
        } catch (Exception e) {}
        log.info("Finishing tests");
        System.gc();
    }

    private static User createTestUser() {
        User user = new User(TEST_USER_NAME, TEST_USER_PASSWORD);
        user.setEmail(TEST_USER_NAME + "@gmail.com");
        user.setUserRole(UserRole.USER);
        user.setNotes("Created by " + UserServiceTest.class.getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        return user;
    }

    /** User Validator tests */


    /** Product CRUD operations tests */
    @Test
    @Ignore
    public void testFindUser1() throws UnknownUserException {
        User user = userService.findUser(CORRECT_USER_NAME, CORRECT_USER_PASSWORD);
        log.info(user);
        assertTrue(user.getName().equals("Yaroslav"));
    }

    @Test(expected = UnknownUserException.class)
    @Ignore
    public void testFindUser2() throws UnknownUserException {
        User user = userService.findUser(CORRECT_USER_NAME, "..");
        assertNotNull(user);
    }

    @Test
    @Ignore
    public void testAddUser() throws InterruptedException {
        boolean result = userService.addUser(testUser);
        assertTrue(result);
    }

    @Test
    @Ignore
    public void testUpdateUser() throws UnknownUserException {
        User user = userService.findUser(TEST_USER_NAME, TEST_USER_PASSWORD);
        user.setName("test");
        user.setPassword("test");
        user.setUserRole(UserRole.ADMIN);
        user.setNotes("Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        boolean result  = userService.updateUser(user);
        log.info(userService.findUser("Test", "test"));
        User updatedUser = userService.findUser("test", "test");
        if (result) {
            user.setName(testUser.getName());
            user.setPassword(testUser.getPassword());
            user.setUserRole(testUser.getUserRole());
            userService.updateUser(user);
        }
        assertTrue(result && updatedUser.getName().equals("test") && updatedUser.getPassword().equals("test"));
    }

    @Test
    @Ignore
    public void testDeleteUser() throws InterruptedException {
        boolean result  = userService.deleteUser(testUser);
        assertTrue(result);
    }
}
