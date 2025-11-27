import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisteredUserCheckoutTest extends SetupPage {

    @BeforeMethod
    public void setUp() {
        SetupPage.BrowserRunner(Browser);
    }


    @Test(description = "Registered user full checkout flow", priority = 1)
    public void registeredUserCheckout() {
        RegisteredUserCheckout checkout = new RegisteredUserCheckout(SetupPage.driver);

        boolean success = checkout.performFullCheckout(Email,Password);
        Assert.assertTrue(success, "Registered checkout flow failed or confirmation message not found.");
    }

    @Test(description = "delete Order after checkout", priority = 2)
    public void deleteOrderAfterCheckout() {
        RegisteredUserCheckout checkout = new RegisteredUserCheckout(SetupPage.driver);
        boolean deleteOrder = checkout.dropOrder(Email, Password,ProductNumber);
        Assert.assertTrue(deleteOrder, "The order which deleted should be disappear from order history.");
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
