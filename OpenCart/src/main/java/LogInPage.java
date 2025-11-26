import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page object representing the Login page and related actions (with stable waits).
 */
public class LogInPage extends SetupPage {
    public LogInPage() {
        super();
    }
    // Consistent default timeout for waits
    private static final int DEFAULT_TIMEOUT = 10;

    // Locators
    @FindBy(id = "input-email")
    private WebElement username;

    @FindBy(id = "input-password")
    private WebElement password;

    @FindBy(xpath = "//*[@id='form-login']/div[3]/button")
    private WebElement loginBtn;

    @FindBy(xpath = "//*[@id=\"content\"]/h1")
    private WebElement loginHeader;



    // -------------------------------------------------------------
    // ⭐ Reusable Wait Helpers
    // -------------------------------------------------------------


    private WebElement waitForClickable(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    private void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete")
        );
    }

    // -------------------------------------------------------------
    // ⭐ Field Actions with Waits
    // -------------------------------------------------------------
    public void setUsername(String name) {
        if (name == null) throw new IllegalArgumentException("username must not be null");

        waitForVisibility(username).clear();
        username.sendKeys(name);
    }

    public void setPassword(String pass) {
        if (pass == null) throw new IllegalArgumentException("password must not be null");

        waitForVisibility(password).clear();
        password.sendKeys(pass);
    }

    public void clickLogin() {
        waitForClickable(loginBtn).click();
        waitForPageLoad(); // ensure navigation or validation completes
    }

    // -------------------------------------------------------------
    //  High-Level Login Action
    // -------------------------------------------------------------
    public boolean login(String name, String pass) {
        setUsername(name);
        setPassword(pass);
        clickLogin();
        return true;
    }

    // -------------------------------------------------------------
    //  Title of the Page Methods
    // -------------------------------------------------------------
    public String getTitle() {
        return driver != null ? driver.getTitle() : "";
    }

    public boolean isLoginButtonDisplayed() {
        try {
            waitForVisibility(loginBtn);
            return loginBtn.isDisplayed() && loginBtn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    // Extract login error message for assertions
    public String getLoginErrorMessage() {
        try {
            WebElement alert = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".alert-danger")));
            return alert.getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public String getLoginHeaderText() {
        waitForVisibility(loginHeader);
        return loginHeader.getText();
    }
}
