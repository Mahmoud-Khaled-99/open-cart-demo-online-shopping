import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogOut extends SetupPage {

    private static final int DEFAULT_TIMEOUT = 10;

    public LogOut() {
        super();
    }

    // ===========================
    // Locators
    // ===========================
    @FindBy(xpath = "//*[@id=\"top\"]/div/div/div[2]/ul/li[2]/div/a/span")
    private WebElement accountMenuBtn;

    @FindBy(xpath = "//*[@id=\"top\"]/div/div/div[2]/ul/li[2]/div/ul/li[5]/a")
    private WebElement logoutBtn;

    @FindBy(xpath = "//*[@id='content']//a[text()='Continue']")
    private WebElement confirmLogoutBtn;

    // ===========================
    // Actions (Enhanced with Waits)
    // ===========================

    /**
     * Clicks the account menu to reveal logout option.
     * Includes visibility + clickable waits.
     */
    public void clickLogOutMenu() {
        waitForVisibility(accountMenuBtn);
        clickWhenClickable(accountMenuBtn, DEFAULT_TIMEOUT);
    }

    /**
     * Clicks the initial Logout button after waiting for it.
     */
    public void clickLogOut() {
        waitForVisibility(logoutBtn);
        clickWhenClickable(logoutBtn, DEFAULT_TIMEOUT);
    }

    /**
     * Clicks the 'Continue' button on the logout confirmation page.
     */
    public void clickConfirmLogOut() {
        waitForVisibility(confirmLogoutBtn);
        clickWhenClickable(confirmLogoutBtn, DEFAULT_TIMEOUT);
    }

    /**
     * Performs the complete logout process with transition waits.
     */
    public void performLogout() {
        waitForVisibility(accountMenuBtn); // Ensure account menu is visible
        clickLogOutMenu();                                // Step 1
        waitForVisibility(logoutBtn);    // Ensure logout option is visible
        clickLogOut();                                    // Step 2

        waitForVisibility(confirmLogoutBtn); // Wait for confirmation page
        clickConfirmLogOut();                                 // Step 3
    }
}
