import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisteredUserCheckoutTest {

    @BeforeMethod
    public void setUp() {
        // Start browser (Edge) and maximize
        SetupPage.BrowserRunner("edge");
        SetupPage.driver.manage().window().maximize();
    }

    @Test(description = "Registered user full checkout flow")
    public void registeredUserCheckout() {
        RegisteredUserCheckout checkout = new RegisteredUserCheckout(SetupPage.driver);

        boolean success = checkout.performFullCheckout(
                "mahmoudkhaled99@gmail.com",
                "123456789"
        );

        Assert.assertTrue(success, "Registered checkout flow failed or confirmation message not found.");
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
