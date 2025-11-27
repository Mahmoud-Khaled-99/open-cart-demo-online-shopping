// java
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LogOutTest extends SetupPage {
    private LogInPage loginPage;
    private LogOut logoutPage;

    @BeforeMethod
    public void setUp() {
        // Launch browser
        BrowserRunner(Browser);

        // Navigate to login page
        SetupPage.driver.get(BASE_URL);

        // Initialize login page
        loginPage = new LogInPage();
        PageFactory.initElements(SetupPage.driver, loginPage);

        // Log in
        loginPage.setUsername(Email);
        loginPage.setPassword(Password);
        loginPage.clickLogin();

        // Wait until account page is loaded
        WebDriverWait wait = new WebDriverWait(SetupPage.driver, DEFAULT_WAIT);
        wait.until(ExpectedConditions.urlContains("route=account/account"));

        // Initialize logout page AFTER successful login (elements are present only when logged in)
        logoutPage = new LogOut();
        PageFactory.initElements(SetupPage.driver, logoutPage);
    }

    // ===============================================================
    // Test Case 1: Verify user can successfully log out (Happy Scenario)
    // ===============================================================
    @Test(description = "Verify logout works (happy scenario)", priority = 1)
    public void verifyLogoutSuccess() {
        // Perform logout
        logoutPage.performLogout();

        // Wait for redirect to home page
        WebDriverWait wait = new WebDriverWait(SetupPage.driver, DEFAULT_WAIT);
        wait.until(ExpectedConditions.urlContains("route=common/home"));

        String currentUrl = SetupPage.driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("route=common/home"),
                "Expected to be on Home Page after logout but was: " + currentUrl
        );
    }

    // ===============================================================
    // Test Case 2: Ensure user cannot access My Account after logout
    // ===============================================================
    @Test(description = "Verify user cannot access account page after logout", priority = 2)
    public void verifyUserCannotAccessAccountAfterLogout() {
        // Ensure logged in test starts by logging out
        logoutPage.performLogout();

        // Wait for logout to complete (home)
        WebDriverWait wait = new WebDriverWait(SetupPage.driver, DEFAULT_WAIT);
        wait.until(ExpectedConditions.urlContains("route=common/home"));

        // Try navigating to My Account directly
        SetupPage.driver.get(BASE_URL);

        // Expect redirection to login page
        wait.until(ExpectedConditions.urlContains("route=account/login"));
        Assert.assertTrue(
                SetupPage.driver.getCurrentUrl().contains("route=account/login"),
                "User was able to access account page after logout!"
        );
    }


    @AfterMethod
    public void tearDown() {
        try {
            if (SetupPage.driver != null) {
                SetupPage.driver.quit();
            }
        } catch (Exception ignored) {
        } finally {
            SetupPage.driver = null;
        }
    }
}
