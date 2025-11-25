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

    @FindBy(xpath = "//*[@id='top']//a[text()='Logout']")
    private WebElement logoutBtn;

    @FindBy(xpath = "//*[@id='content']//a[text()='Continue']")
    private WebElement confirmLogoutBtn;
    // ===========================
    // Actions
    // ===========================

    /**
     * Waits for the Logout button to be visible & clickable, then clicks it.
     */
    public void clickLogout() {
        waitForVisibility(logoutBtn);
        clickWhenClickable(logoutBtn, DEFAULT_TIMEOUT);
    }

    /**
     * Waits for the confirmation Continue button to be visible & clickable, then clicks it.
     */
    public void clickConfirmLogout() {
        waitForVisibility(confirmLogoutBtn);
        clickWhenClickable(confirmLogoutBtn, DEFAULT_TIMEOUT);
    }

    /**
     * Complete logout flow:
     * 1. Click Logout
     * 2. Explicitly wait for the confirmation page
     * 3. Click Continue
     */
    public void performLogout() {
        clickLogout();                                 // Step 1
        waitForVisibility(confirmLogoutBtn, 10);        // Step 2 (safe wait for transition)
        clickConfirmLogout();                           // Step 3
    }

    // ===========================
    // Verification Helpers
    // ===========================

    /**
     * Returns true if the Logout button is visible.
     */
    public boolean isLogoutButtonVisible() {
        return isElementVisible(logoutBtn, 5);
    }

    /**
     * Returns true if the Continue button is visible on the confirmation page.
     */
    public boolean isConfirmLogoutVisible() {
        return isElementVisible(confirmLogoutBtn, 5);
    }
}
