import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class SignUp extends SetupPage {

    public SignUp() {
        super();
    }

    @FindBy(xpath = "//*[@id='top']//a[text()='Register']")
    private WebElement SignUpBtn;

    @FindBy(id = "input-firstname")
    private WebElement firstName;

    @FindBy(id = "input-lastname")
    private WebElement lastName;

    @FindBy(id = "input-email")
    private WebElement email;

    @FindBy(id = "input-password")
    private WebElement password_new;

    @FindBy(xpath = "//input[@type='checkbox' and @name='agree']")
    private WebElement privacyPolicyCheckbox;

    @FindBy(xpath = "//form[@id='form-register']//button[text()='Continue']")
    WebElement continueBtn;

    // This element's XPath looks like it might be for a success message or a link after registration.
    // If it's a link to agree to terms *before* registration, it should be handled differently.
    // Assuming it's a confirmation link after successful registration for now.
    @FindBy(xpath = "/html/body/div/main/div[2]/div/div/div/a")
    private WebElement agreeSignup;

    @FindBy(xpath = "//*[@id=\"content\"]/h1")
    private WebElement accountCreatedMessage;

    @FindBy(id = "error-firstname")
    private WebElement firstNameError;

    @FindBy(id = "error-lastname")
    private WebElement lastNameError;

    @FindBy(id = "error-email")
    private WebElement emailError;

    @FindBy(xpath = "//*[@id=\"alert\"]/div")
    private WebElement repeatedEmailError;

    @FindBy(id = "error-password")
    private WebElement passwordError;
    /**
     * Clicks the initial Sign Up button to navigate to the registration form.
     */
    public void clickSignUp() {
        clickWhenClickable(SignUpBtn, 10); // Using clickWhenClickable for robustness
    }

    /**
     * Enters the first name into the first name field.
     * @param fname The first name to enter.
     */
    public void setFirstName(String fname) {
        firstName.clear();
        firstName.sendKeys(fname);
    }

    /**
     * Enters the last name into the last name field.
     * @param lname The last name to enter.
     */
    public void setLastName(String lname) {
        lastName.clear();
        lastName.sendKeys(lname);
    }

    /**
     * Enters the email address into the email field.
     * @param mail The email address to enter.
     */
    public void setEmail(String mail) {
        email.clear();
        email.sendKeys(mail);
    }

    /**
     * Enters the password into the password field.
     * @param pass The password to enter.
     */
    public void setPassword(String pass) {
        password_new.clear();
        password_new.sendKeys(pass);
    }

    /**
     * Clicks the privacy policy checkbox.
     */
    public void checkPrivacyPolicy() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", privacyPolicyCheckbox);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", privacyPolicyCheckbox);
    }

    /**
     * Clicks the Continue button on the registration form.
     */
    public void clickContinue() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
    }

    /**
     * Clicks the 'Agree' link/button, typically found after successful registration.
     */
    public void clickAgreeSignup() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", agreeSignup);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", agreeSignup);
    }

    /**
     * Performs the complete account registration process.
     * @param firstName The first name for the new account.
     * @param lastName The last name for the new account.
     * @param email The email address for the new account.
     * @param password The password for the new account.
     */
    public void registerAccount(String firstName, String lastName, String email, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        checkPrivacyPolicy();
        clickContinue();
    }

    public String getAccountCreatedMessage() {
        return accountCreatedMessage.getText();
    }
    public String getFirstFieldError(){
        return firstNameError.getText();
    }
    public String getLastFieldError(){
        return lastNameError.getText();
    }
    public String getEmailFieldError(){
        return emailError.getText();
    }
    public String getRepeatedEmailError(){
        return repeatedEmailError.getText();
    }
    public String getPasswordFieldError(){
        return passwordError.getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}