import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page object representing the Login page and related actions.
 */
public class LogInPage extends SetupPage {

    // Step 2: Consistent default timeout for waits (in seconds)
    private static final int DEFAULT_TIMEOUT = 10;

    // Step 3: clearer field names, keep FindBy locators
    @FindBy(id = "input-email")
    private WebElement username; // original name retained for compatibility with psi links

    @FindBy(id = "input-password")
    private WebElement password; // original name retained for compatibility with psi links

    @FindBy(xpath = "//*[@id='form-login']/div[3]/button")
    private WebElement loginBtn; // original name retained for compatibility with psi links

    // Step 1: default no-arg constructor (keeps existing behavior)
    public LogInPage() {
        super();
    }

    // Step 1: constructor that accepts WebDriver and initializes PageFactory (ensures elements are wired)
    public LogInPage(WebDriver driver) {
        super(driver); // initializes PageFactory in SetupPage constructor (Step 1)
        // PageFactory.initElements is called in SetupPage(WebDriver) so no need to call here
    }

    /**
     * Waits until the element is visible.
     * Used to harden interactions (Step 4).
     */
    private void waitForVisibility(WebElement element, int timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Enters the username into the username field.
     * Clears existing text and validates input. (Step 4)
     */
    public void setUsername(String name) {
        if (name == null) {
            throw new IllegalArgumentException("username must not be null");
        }
        waitForVisibility(username, DEFAULT_TIMEOUT);
        username.clear();
        username.sendKeys(name);
    }

    /**
     * Enters the password into the password field.
     * Clears existing text and validates input. (Step 4)
     */
    public void setPassword(String pass) {
        if (pass == null) {
            throw new IllegalArgumentException("password must not be null");
        }
        waitForVisibility(password, DEFAULT_TIMEOUT);
        password.clear();
        password.sendKeys(pass);
    }

    /**
     * Clicks the login button using the robust click method from SetupPage.
     * (Step 5)
     */
    public void clickLogin() {
        clickWhenClickable(loginBtn, DEFAULT_TIMEOUT);
    }

    /**
     * Performs login by setting username and password then clicking login.
     * This method throws IllegalArgumentException for null inputs and will wait for elements.
     * Returns true if the click action was performed without throwing exceptions. (Step 5)
     */
    public boolean login(String name, String pass) {
        setUsername(name);         // Step 4
        setPassword(pass);         // Step 4
        clickLogin();              // Step 5
        return true;
    }

    /**
     * Convenience: returns the current page title (useful for assertions).
     */
    public String getTitle() {
        return driver != null ? driver.getTitle() : "";
    }

    /**
     * Convenience: checks whether the login button is displayed.
     */
    public boolean isLoginButtonDisplayed() {
        try {
            waitForVisibility(loginBtn, DEFAULT_TIMEOUT);
            return loginBtn.isDisplayed() && loginBtn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
}