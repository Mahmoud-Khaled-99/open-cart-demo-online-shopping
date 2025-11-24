import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;


public class SignUpTest {
    private static final String BASE_URL = "http://localhost/opencartproject/index.php?route=common/home&language=en-gb";
    private SignUp signUp;

    @BeforeTest()
    public void setUp() {
        // start browser (adjust browser name as needed)
        SetupPage.BrowserRunner("chrome");
        // navigate to application
        SetupPage.driver.get(BASE_URL);
        // instantiate and wire page object
        signUp = new SignUp();
        PageFactory.initElements(SetupPage.driver, signUp);
    }

    @Test()
    public void VerifyRegisterAccountHS() {
        try {
            signUp.clickMyAccount();
            signUp.clickSignUp();
            signUp.setFirstName("Test");
            signUp.setLastName("User");
            signUp.setEmail("Mahmouuuud@gamil.com");
            signUp.setPassword("Password123!");
            signUp.checkPrivacyPolicy();
            signUp.clickContinue();
            Assert.assertTrue(signUp.getAccountCreatedMessage().contains("Your Account Has Been Created!"), "Account creation message");
            signUp.clickAgreeSignup();
        } catch (Exception e) {
            Assert.fail("Registration failed with exception: " + e.getMessage());
        }
    }

    @Test
    public void VerifyInvalidFName() {
        try {
            signUp.clickMyAccount();
            signUp.clickSignUp();
            signUp.setFirstName("");
            signUp.setLastName("User");
            signUp.setEmail("Mahmouuuud@gamil.com");
            signUp.setPassword("Password123!");
            signUp.checkPrivacyPolicy();
            signUp.clickContinue();
            Assert.assertTrue(signUp.getFirstFieldError().contains("First Name must be between 1 and 32 characters!"), "First name validation message");
        } catch (Exception e) {
            Assert.fail("Registration failed with exception: " + e.getMessage());
        }
    }


    @Test
    public void VerifyInvalidLName() {
        try {
            signUp.clickMyAccount();
            signUp.clickSignUp();
            signUp.setFirstName("mahmoud");
            signUp.setLastName("");
            signUp.setEmail("Mahmouuuud@gamil.com");
            signUp.setPassword("Password123!");
            signUp.checkPrivacyPolicy();
            signUp.clickContinue();
            Assert.assertTrue(signUp.getLastFieldError().contains("Last Name must be between 1 and 32 characters!"), "Last name validation message");
        } catch (Exception e) {
            Assert.fail("Registration failed with exception: " + e.getMessage());
        }
    }

    @Test()
    public void VerifyInvalidEmail() {
        try {
            signUp.clickMyAccount();
            signUp.clickSignUp();
            signUp.setFirstName("mahmoud");
            signUp.setLastName("user");
            signUp.setEmail("Mahmoud");
            signUp.setPassword("Password123!");
            signUp.checkPrivacyPolicy();
            signUp.clickContinue();

            Assert.assertNotEquals(SetupPage.driver.getCurrentUrl(),
                    "http://localhost/opencartproject/index.php?route=account/success",
                    "User should NOT be registered with invalid email!"
            );
        } catch (Exception e) {
            Assert.fail("Registration failed with exception: " + e.getMessage());
        }
    }

    @Test()
    public void VerifyRepeatedEmail(){
        try {
            signUp.clickMyAccount();
            signUp.clickSignUp();
            signUp.setFirstName("mahmoud");
            signUp.setLastName("user");
            signUp.setEmail("mahmoudkhaled51299@gmail.com");
            signUp.setPassword("Password123!");
            signUp.checkPrivacyPolicy();
            signUp.clickContinue();
            Assert.assertTrue(signUp.getRepeatedEmailError().contains("E-Mail Address is already registered"), "Email validation message");
        } catch (Exception e) {
            Assert.fail("Registration failed with exception: " + e.getMessage());
        }
    }
    @Test()
    public void VerifyInvalidPassword() {
        try {
            signUp.clickMyAccount();
            signUp.clickSignUp();
            signUp.setFirstName("mahmoud");
            signUp.setLastName("user");
            signUp.setEmail("a@gmail.com");
            signUp.setPassword("++64");
            signUp.checkPrivacyPolicy();
            signUp.clickContinue();
            Assert.assertTrue(signUp.getPasswordFieldError().contains("Password must be between 6 and 40 characters!"), "Email validation message");
        } catch (Exception e) {
            Assert.fail("Registration failed with exception: " + e.getMessage());
        }
    }

    @AfterTest()
    public void tearDown() {
        // quit browser
        signUp.quitDriver();
    }
}
