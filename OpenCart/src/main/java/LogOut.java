import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogOut extends SetupPage {
    public LogOut() {
        // Default constructor, implicitly calls SetupPage constructor
    }

    @FindBy(xpath = "//*[@id='top']//a[text()='Logout']")
    private WebElement logOutBtn;

    @FindBy(xpath = "//*[@id='content']//a[text()='Continue']")
    private WebElement confirmLogOutBtn;

    /**
     * Clicks the initial Logout button, typically found in the header or account menu.
     */
    public void clickLogOut() {
        clickWhenClickable(logOutBtn, 10);
    }

    /**
     * Clicks the 'Continue' button on the logout confirmation page.
     */
    public void clickConfirmLogOut() {
        clickWhenClickable(confirmLogOutBtn, 10);
    }

    /**
     * Performs the complete logout process, from clicking the logout link to confirming it.
     * This method assumes the user is already logged in and on a page where the logout link is visible.
     */
    public void performLogout() {
        clickLogOut(); // Clicks the 'Logout' link
        clickConfirmLogOut(); // Clicks the 'Continue' button on the logout confirmation page
    }
}
