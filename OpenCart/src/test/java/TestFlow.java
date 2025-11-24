import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestFlow {/*
    private static final String BASE_URL = "http://your-app-url/";
    private static final String USER_EMAIL = "user@example.com";
    private static final String USER_PASSWORD = "Password123!";
    private LogInPage logInPage;
    private LogOut logOut;

    @BeforeClass
    public void setUp() {
        SetupPage.BrowserRunner("chrome");
        SetupPage.driver.get(BASE_URL);
        // LogInPage has a constructor that wires PageFactory when passing driver
        logInPage = new LogInPage(SetupPage.driver);
        // LogOut constructor initializes its elements; ensure it's wired
        logOut = new LogOut();
        PageFactory.initElements(SetupPage.driver, logOut);
    }

    /*@org.testng.annotations.Test
    public void testLoginAndLogout() {
        try {
            boolean loginResult = logInPage.login(USER_EMAIL, USER_PASSWORD);
            Assert.assertTrue(loginResult, "login(...) returned false.");

            boolean logoutResult = logOut.performLogout();
            Assert.assertTrue(logoutResult, "performLogout() returned false.");
        } catch (Exception e) {
            Assert.fail("Auth flow failed with exception: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        new SetupPage().quitDriver();
    }*/
}
