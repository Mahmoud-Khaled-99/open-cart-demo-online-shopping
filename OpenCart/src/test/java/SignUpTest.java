import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;


public class SignUpTest {
    private static final String BASE_URL = "http://localhost/opencartproject/index.php?route=common/home&language=en-gb";
    private SignUp signUp;

    @BeforeMethod()
    public void setUp() {
        // start browser (adjust browser name as needed)
        SetupPage.BrowserRunner("chrome");
        // navigate to application
        SetupPage.driver.get(BASE_URL);
        // instantiate and wire page object
        signUp = new SignUp();
        PageFactory.initElements(SetupPage.driver, signUp);
    }

    @Test(description = "Verify signUp working fine (happy scenario)", priority = 1)
    public void VerifyRegisterAccountHS() {
            signUp.clickMyAccount();
            signUp.clickSignUp();
            signUp.setFirstName("Test");
            signUp.setLastName("User");
            signUp.setEmail("Mahmoud" + System.currentTimeMillis() + "@gmail.com");
            signUp.setPassword("Password123!");
            signUp.checkPrivacyPolicy();
            signUp.clickContinue();
            String actualMessage = signUp.getAccountCreatedMessage();
            System.out.println(actualMessage);
            Assert.assertTrue(
                actualMessage.contains("Register Account"),
                "Expected account creation message but got: " + actualMessage);
    }

    @Test(description = "Verify signUp with empty first name", priority = 1)
    public void VerifyInvalidFName() {

        signUp.clickMyAccount();
        signUp.clickSignUp();
        signUp.setFirstName("");   // empty first name
        signUp.setLastName("User");

        // unique email
        String email = "Mahmoud" + System.currentTimeMillis() + "@gmail.com";
        signUp.setEmail(email);

        signUp.setPassword("Password123!");
        signUp.checkPrivacyPolicy();
        signUp.clickContinue();
        String errorMessage = signUp.getFirstFieldError();
        Assert.assertTrue(
                errorMessage.contains("First Name must be between 1 and 32 characters!"),
                "Expected first name validation message but got: " + errorMessage
        );
    }


    @Test
    public void VerifyInvalidLName() {
        signUp.clickMyAccount();
        signUp.clickSignUp();
        signUp.setFirstName("User");   // empty first name
        signUp.setLastName("");
        String email = "Mahmoud" + System.currentTimeMillis() + "@gmail.com";
        signUp.setEmail(email);
        signUp.setPassword("Password123!");
        signUp.checkPrivacyPolicy();
        signUp.clickContinue();
        String errorMessage = signUp.getLastFieldError();
        Assert.assertTrue(
                errorMessage.contains("Last Name must be between 1 and 32 characters!"),
                "Expected first name validation message but got: " + errorMessage
        );
    }

    @Test()
    public void VerifyInvalidEmail() {
            signUp.clickMyAccount();
            signUp.clickSignUp();
            signUp.setFirstName("mahmoud");
            signUp.setLastName("user");
            String email = "Mahmoud" + System.currentTimeMillis() + "@gmail.com";
            signUp.setEmail(email);
            signUp.setPassword("Password123!");
            signUp.checkPrivacyPolicy();
            signUp.clickContinue();
            Assert.assertNotEquals(SetupPage.driver.getCurrentUrl(),
                    "http://localhost/opencartproject/index.php?route=account/success",
                    "User should NOT be registered with invalid email!"
            );
    }

    @Test()
    public void VerifyInvalidPassword() {
            signUp.clickMyAccount();
            signUp.clickSignUp();
            signUp.setFirstName("mahmoud");
            signUp.setLastName("user");
            String email = "Mahmoud" + System.currentTimeMillis() + "@gmail.com";
            signUp.setEmail(email);
            signUp.setPassword("++6");
            signUp.checkPrivacyPolicy();
            signUp.clickContinue();
            Assert.assertTrue(signUp.getPasswordFieldError().contains("Password must be between 6 and 40 characters!"), "Email validation message");
    }

    @AfterMethod
    public void tearDown() {
        // quit browser
        signUp.quitDriver();

    }
}
