import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test class responsible for validating all Sign-Up scenarios.
 * Uses TestNG + Page Object Model + explicit waits (inside SignUp class).
 */
public class SignUpTest {

    // Base URL for homepage
    private static final String BASE_URL =
            "http://localhost/opencartproject/index.php?route=common/home&language=en-gb";

    private SignUp signUp;

    @BeforeMethod
    public void setUp() {
        // Initialize browser (choose chrome/firefox/edge)
        SetupPage.BrowserRunner("chrome");

        // Navigate to AUT homepage
        SetupPage.driver.get(BASE_URL);

        // Initialize Page Object using PageFactory
        signUp = new SignUp();
        PageFactory.initElements(SetupPage.driver, signUp);
    }

    /**
     * Utility method to generate unique emails every test run.
     */
    private String getUniqueEmail() {
        return "Mahmoud" + System.currentTimeMillis() + "@gmail.com";
    }

    // -------------------------------------------------------------------------
    // ✔ Test Case 1 — Happy Path: Valid Registration
    // -------------------------------------------------------------------------
    @Test(description = "Verify that the sign-up process works with valid data", priority = 1)
    public void VerifyRegisterAccountHS() {

        // Navigate to registration
        signUp.clickMyAccount();
        signUp.clickSignUp();

        // Fill form with valid details
        signUp.setFirstName("Test");
        signUp.setLastName("User");
        signUp.setEmail(getUniqueEmail());
        signUp.setPassword("Password123!");
        signUp.checkPrivacyPolicy();
        signUp.clickContinue();

        // Assert success message
        String actualMessage = signUp.getAccountCreatedMessage();
        Assert.assertTrue(
                actualMessage.contains("Register Account"),
                "Expected successful account creation message, but got: " + actualMessage
        );
    }

    // -------------------------------------------------------------------------
    // ❌ Test Case 2 — Empty First Name
    // -------------------------------------------------------------------------
    @Test(description = "Verify validation message for empty First Name", priority = 2)
    public void VerifyInvalidFName() {

        signUp.clickMyAccount();
        signUp.clickSignUp();

        // First Name intentionally left empty
        signUp.setFirstName("");
        signUp.setLastName("User");
        signUp.setEmail(getUniqueEmail());
        signUp.setPassword("Password123!");
        signUp.checkPrivacyPolicy();
        signUp.clickContinue();

        String errorMessage = signUp.getFirstFieldError();

        Assert.assertTrue(
                errorMessage.contains("First Name must be between 1 and 32 characters!"),
                "Expected First Name error message, but got: " + errorMessage
        );
    }

    // -------------------------------------------------------------------------
    // ❌ Test Case 3 — Empty Last Name
    // -------------------------------------------------------------------------
    @Test(description = "Verify validation message for empty Last Name", priority = 3)
    public void VerifyInvalidLName() {

        signUp.clickMyAccount();
        signUp.clickSignUp();

        signUp.setFirstName("User");
        signUp.setLastName("");  // last name empty
        signUp.setEmail(getUniqueEmail());
        signUp.setPassword("Password123!");
        signUp.checkPrivacyPolicy();
        signUp.clickContinue();

        String errorMessage = signUp.getLastFieldError();

        Assert.assertTrue(
                errorMessage.contains("Last Name must be between 1 and 32 characters!"),
                "Expected Last Name error message, but got: " + errorMessage
        );
    }

    // -------------------------------------------------------------------------
    // ❌ Test Case 4 — Invalid Email Format
    // -------------------------------------------------------------------------
    @Test(description = "Verify registration fails with invalid email format", priority = 4)
    public void VerifyInvalidEmail() {

        signUp.clickMyAccount();
        signUp.clickSignUp();

        signUp.setFirstName("Mahmoud");
        signUp.setLastName("User");

        // Email format invalid intentionally
        signUp.setEmail("invalidEmailFormat");
        signUp.setPassword("Password123!");
        signUp.checkPrivacyPolicy();
        signUp.clickContinue();

        // Ensure user was NOT redirected to success page
        Assert.assertNotEquals(
                SetupPage.driver.getCurrentUrl(),
                "http://localhost/opencartproject/index.php?route=account/success",
                "User should NOT be registered with invalid email!"
        );
    }

    // -------------------------------------------------------------------------
    // ❌ Test Case 5 — Invalid Password
    // -------------------------------------------------------------------------
    @Test(description = "Verify validation message for weak/short password", priority = 5)
    public void VerifyInvalidPassword() {

        signUp.clickMyAccount();
        signUp.clickSignUp();

        signUp.setFirstName("Mahmoud");
        signUp.setLastName("User");
        signUp.setEmail(getUniqueEmail());

        // Intentionally weak password
        signUp.setPassword("++6");
        signUp.checkPrivacyPolicy();
        signUp.clickContinue();

        Assert.assertTrue(
                signUp.getPasswordFieldError().contains("Password must be between 6 and 40 characters!"),
                "Expected password validation message but got something else!"
        );
    }

    @AfterMethod
    public void tearDown() {
        // Quit browser to clean up resources
        signUp.quitDriver();
    }
}
