import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SignUp extends SetupPage {

    public SignUp() {
        super();
    }

    // -------------------------------------------------------------
    // Locators (kept EXACTLY as you requested)
    // -------------------------------------------------------------

    private final By SignUpBtn = By.xpath("//*[@id='top']//a[text()='Register']");
    private final By firstName = By.id("input-firstname");
    private final By lastName = By.id("input-lastname");
    private final By email = By.id("input-email");
    private final By password_new = By.id("input-password");
    private final By privacyPolicyCheckbox = By.xpath("//input[@type='checkbox' and @name='agree']");
    private final By continueBtn = By.xpath("//form[@id='form-register']//button[text()='Continue']");
    private final By agreeSignup = By.xpath("/html/body/div/main/div[2]/div/div/div/a");
    private final By accountCreatedMessage = By.xpath("/html/body/div/main/div[2]/div/div/h1");
    private final By emailError = By.id("error-email");
    private final By repeatedEmailError = By.xpath("//*[@id=\"alert\"]/div");
    private final By passwordError = By.id("error-password");

    // -------------------------------------------------------------
    // Actions
    // -------------------------------------------------------------

    public void clickSignUp() {

        clickWhenClickable(driver.findElement(SignUpBtn) , 10);
    }

    public void setFirstName(String fname) {
        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys(fname);
    }

    public void setLastName(String lname) {
        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(lname);
    }

    public void setEmail(String mail) {
        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(mail);
    }

    public void setPassword(String pass) {
        driver.findElement(password_new).clear();
        driver.findElement(password_new).sendKeys(pass);
    }

    public void checkPrivacyPolicy() {
        WebElement checkbox = driver.findElement(privacyPolicyCheckbox);
        scrollTo(checkbox);
        clickJS(checkbox);
    }

    public void clickContinue() {
        WebElement btn = driver.findElement(continueBtn);
        scrollTo(btn);
        clickJS(btn);
    }

    public void clickAgreeSignup() {
        WebElement btn = driver.findElement(agreeSignup);
        scrollTo(btn);
        clickJS(btn);
    }

    // -------------------------------------------------------------
    // Registration workflow
    // -------------------------------------------------------------

    public void registerAccount(String fname, String lname, String userEmail, String pass) {
        setFirstName(fname);
        setLastName(lname);
        setEmail(userEmail);
        setPassword(pass);
        checkPrivacyPolicy();
        clickContinue();
    }

    // -------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------

    public String getAccountCreatedMessage() {
        return driver.findElement(accountCreatedMessage).getText().trim();
    }

    public String getFirstFieldError() {

        scrollTo(driver.findElement(firstName));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("error-firstname")
        ));
        return error.getText().trim();
    }

    public String getLastFieldError() {

        scrollTo(driver.findElement(lastName));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("error-lastname")
        ));
        return error.getText().trim();
    }

    public String getEmailFieldError() {
        return driver.findElement(emailError).getText().trim();
    }

    public String getRepeatedEmailError() {

        WebElement alertBox = driver.findElement(repeatedEmailError);
        scrollTo(alertBox);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(alertBox));

        return alertBox.getText().trim();
    }

    public String getPasswordFieldError() {

        WebElement element = driver.findElement(passwordError);
        scrollTo(element);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(element));

        return element.getText().trim();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // -------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------

    private void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void clickJS(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
}
