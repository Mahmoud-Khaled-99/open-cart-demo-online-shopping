import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogInTest {

    private static final String BASE_URL = "http://localhost/opencartproject/index.php?route=account/login&language=en-gb";
    private LogInPage loginPage;

    @BeforeMethod
    public void setUp() {
        // Start browser
        SetupPage.BrowserRunner("chrome");

        // Navigate to login page
        SetupPage.driver.get(BASE_URL);

        // Initialize the page object
        loginPage = new LogInPage();
        PageFactory.initElements(SetupPage.driver, loginPage);
    }

    // -------------------------------------------------------------
    // Valid Login
    // -------------------------------------------------------------
    @Test(description = "Verify login works with valid credentials Happy scenario")
    public void testValidLogin() {
        loginPage.setUsername("mahmoudkhaled99@gmail.com");
        loginPage.setPassword("123456789");
        loginPage.clickLogin();

        // After login, user should NOT stay on login page
        Assert.assertTrue(
                loginPage.getLoginHeaderText().contains("My Account"),
                "User is in My Account page after successful login!"
        );
    }

    // -------------------------------------------------------------
    // Test Case 2: Invalid Email Format
    // -------------------------------------------------------------
    @Test(description = "Verify login validation for invalid email format")
    public void testInvalidEmail() {
        loginPage.setUsername("invalidEmail");
        loginPage.setPassword("123456789");
        loginPage.clickLogin();

        // Check that login button is still displayed (meaning login failed)
        Assert.assertTrue(
                loginPage.isLoginButtonDisplayed(),
                "Login should fail and stay on the login page!"
        );
    }

    // -------------------------------------------------------------
    // Test Case 3: Invalid Password
    // -------------------------------------------------------------
    @Test(description = "Verify login fails with wrong password")
    public void testInvalidPassword() {
        loginPage.setUsername("mahmoudkhaled99@gmail.com");
        loginPage.setPassword("WrongPass!");
        loginPage.clickLogin();

        Assert.assertFalse(
                loginPage.isLoginButtonDisplayed(),
                "Login should fail when password is incorrect!"
        );
    }

    // -------------------------------------------------------------
    // Test Case 4: Empty Credentials
    // -------------------------------------------------------------
    @Test(description = "Verify login validation for empty email + password fields")
    public void testEmptyCredentials() {
        loginPage.setUsername("");
        loginPage.setPassword("");
        loginPage.clickLogin();

        Assert.assertTrue(
                loginPage.isLoginButtonDisplayed(),
                "Login should fail with empty input!"
        );
    }

    @AfterMethod
    public void tearDown() {
        loginPage.quitDriver();
    }
}
