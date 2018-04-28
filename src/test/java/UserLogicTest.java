import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.dao.IUserDao;
import com.epam.project.domain.User;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;
import com.epam.project.logic.UserLogic;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserLogicTest {

    static User correctUser;
    static User uncorrectUser;
    static IUserDao userDao;

    @BeforeClass
    public static void init() throws
            DataBaseNotSupportedException,
            IncorrectPropertyException,
            DataBaseConnectionException,
            DataNotFoundException {
        userDao = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL).getUserDao();
        correctUser = userDao.findUserByName("Yarik");
        uncorrectUser = userDao.findUserByName("Yarik");
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
}
