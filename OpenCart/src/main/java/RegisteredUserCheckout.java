import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class RegisteredUserCheckout extends SetupPage {
    private static final Duration DEFAULT_WAIT = Duration.ofSeconds(15);
    private static final String BASE_URL = "http://localhost/opencartproject/index.php?route=account/login&language=en-gb";
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public RegisteredUserCheckout(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_WAIT);
        this.js = (JavascriptExecutor) driver;
    }

    // ========================================================================================================
    // Helper Waits
    // ========================================================================================================
    private WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void waitForStability(By locator) {
        WebElement element = waitForVisibility(locator);

        // Wait until element stops moving or refreshing
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver -> {
            try {
                Point p1 = element.getLocation();
                Thread.sleep(150); // micro-stability check
                Point p2 = element.getLocation();
                return p1.equals(p2);
            } catch (StaleElementReferenceException | InterruptedException e) {
                return false;
            }
        });
    }

    public void waitForAjax() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver -> (Boolean) ((JavascriptExecutor) driver).executeScript("return window.jQuery != null && jQuery.active === 0;"));
    }

    // ========================================================================================================
    // Helper Actions
    // ========================================================================================================
    private void safeClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (Exception e) {
            // fallback to JS click
            js.executeScript("arguments[0].click();", element);
        }
    }

    private boolean isElementVisible(By locator, int timeoutSeconds) {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return true;   // Element became visible
    }

    private void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // ========================================================================================================
    // Main Checkout Flow
    // ========================================================================================================
    public boolean performFullCheckout(String email, String password) {
        try {
            driver.get(BASE_URL);
            // Login flow
            safeClick(waitForClickable(By.xpath("(//span[normalize-space()='My Account'])[1]")));
            safeClick(waitForClickable(By.xpath("(//a[normalize-space()='Login'])[1]")));

            WebElement emailInput = waitForVisibility(By.id("input-email"));
            emailInput.clear();
            emailInput.sendKeys(email);

            WebElement passwordInput = waitForVisibility(By.id("input-password"));
            passwordInput.clear();
            passwordInput.sendKeys(password);

            safeClick(waitForClickable(By.xpath("(//button[normalize-space()='Login'])[1]")));

            // Wait until account/home page
            wait.until(ExpectedConditions.urlContains("route=account/account"));

            // Go to home page
            WebElement homebutton = waitForClickable(By.xpath("//a[i[@class='fas fa-home']]"));
            safeClick(homebutton);

            // Add first product to cart
            WebElement addToCart = waitForClickable(By.xpath("/html/body/div/main/div[2]/div/div/div[2]/div[2]/div/div[2]/form/div/button[1]"));
            scrollIntoView(addToCart);
            safeClick(addToCart);

            // Open shopping cart
            WebElement shoppingCart = waitForClickable(By.xpath("(//span[normalize-space()='Shopping Cart'])[1]"));
            safeClick(shoppingCart);

            // Click Checkout
            WebElement checkoutBtn = waitForClickable(By.xpath("//a[@title='Checkout']"));
            safeClick(checkoutBtn);

            // Use new shipping address
            safeClick(waitForClickable(By.id("input-shipping-new")));

            // Fill shipping details
            waitForVisibility(By.id("input-shipping-firstname")).clear();
            waitForVisibility(By.id("input-shipping-firstname")).sendKeys("Mahmoud");

            waitForVisibility(By.id("input-shipping-lastname")).clear();
            waitForVisibility(By.id("input-shipping-lastname")).sendKeys("Khaled");

            waitForVisibility(By.id("input-shipping-company")).clear();
            waitForVisibility(By.id("input-shipping-company")).sendKeys("FUE");

            waitForVisibility(By.id("input-shipping-address-1")).clear();
            waitForVisibility(By.id("input-shipping-address-1")).sendKeys("New Cairo First Settlement");

            waitForVisibility(By.id("input-shipping-address-2")).clear();
            waitForVisibility(By.id("input-shipping-address-2")).sendKeys("Address 2");

            waitForVisibility(By.id("input-shipping-city")).clear();
            waitForVisibility(By.id("input-shipping-city")).sendKeys("New Cairo");

            waitForVisibility(By.id("input-shipping-postcode")).clear();
            waitForVisibility(By.id("input-shipping-postcode")).sendKeys("11865");

            // Select country
            WebElement countryDropdown = waitForClickable(By.id("input-shipping-country"));
            new Select(countryDropdown).selectByVisibleText("Egypt");

            // Wait until region options populated
            wait.until(driver -> {
                List<WebElement> regions = driver.findElements(By.cssSelector("#input-shipping-zone option"));
                return regions.size() > 1;
            });

            // Select region
            WebElement regionDropdown = waitForClickable(By.id("input-shipping-zone"));
            new Select(regionDropdown).selectByVisibleText("Qina");

            // Continue from address
            WebElement continueBtn = waitForClickable(By.xpath("(//button[normalize-space()='Continue'])[1]"));
            scrollIntoView(continueBtn);
            safeClick(continueBtn);
            waitForVisibility(By.xpath("//*[@id=\"input-shipping-existing\"]"));
            waitForClickable(By.xpath("//*[@id=\"input-shipping-existing\"]"));
            // =======================================================================================================
            // SHIPPING METHOD
            // =======================================================================================================
            By shippingChooseBtn = By.xpath("(//button[normalize-space()='Choose'])[1]");

            waitForVisibility(shippingChooseBtn);
            waitForClickable(shippingChooseBtn);
            waitForStability(shippingChooseBtn);

            safeClick(waitForClickable(shippingChooseBtn));
            waitForStability(shippingChooseBtn);

            // Click "Continue"
            By shippingContinueBtn = By.id("button-shipping-method");

            waitForVisibility(shippingContinueBtn);
            waitForClickable(shippingContinueBtn);
            waitForStability(shippingContinueBtn);

            safeClick(waitForClickable(shippingContinueBtn));


            // ====================================================================================================
            // PAYMENT METHOD
            // ====================================================================================================
            By paymentChooseBtn = By.xpath("(//button[normalize-space()='Choose'])[2]");

            waitForVisibility(paymentChooseBtn);
            waitForClickable(paymentChooseBtn);
            waitForStability(paymentChooseBtn);

            safeClick(waitForClickable(paymentChooseBtn));


            // Wait for payment modal
            By paymentContinueBtn = By.xpath("/html/body/div[3]/div/div/div[2]/form/div[2]/button");

            waitForVisibility(paymentContinueBtn);
            waitForClickable(paymentContinueBtn);
            waitForStability(paymentContinueBtn);

            safeClick(waitForClickable(paymentContinueBtn));

            // Wait for modal to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.modal.show")));


            // ====================================================================================================
            // CONFIRM ORDER
            // ====================================================================================================
            By confirmOrderBtn = By.xpath("//button[normalize-space()='Confirm Order']");

            waitForVisibility(confirmOrderBtn);
            waitForClickable(confirmOrderBtn);
            waitForStability(confirmOrderBtn);

            safeClick(waitForClickable(confirmOrderBtn));
            // ====================================================================================================
            // VALIDATION
            // ====================================================================================================
            By successMessage = By.id("content");
            waitForAjax();
            waitForVisibility(successMessage);
            waitForStability(successMessage);
            WebElement success = driver.findElement(successMessage);
            //String successText = success.getText();
            //System.out.println("Success Message: " + successText);
            return success.getText().contains("Your order has been placed");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dropOrder(String email, String password, int orderNumber) {
        try {
            driver.get(BASE_URL);
            // Login flow
            safeClick(waitForClickable(By.xpath("(//span[normalize-space()='My Account'])[1]")));
            safeClick(waitForClickable(By.xpath("(//a[normalize-space()='Login'])[1]")));

            WebElement emailInput = waitForVisibility(By.id("input-email"));
            emailInput.clear();
            emailInput.sendKeys(email);

            WebElement passwordInput = waitForVisibility(By.id("input-password"));
            passwordInput.clear();
            passwordInput.sendKeys(password);

            safeClick(waitForClickable(By.xpath("(//button[normalize-space()='Login'])[1]")));

            // Wait until account/home page
            wait.until(ExpectedConditions.urlContains("route=account/account"));

            // Go to home page
            WebElement homebutton = waitForClickable(By.xpath("//a[i[@class='fas fa-home']]"));
            safeClick(homebutton);

            //go to product Order
            safeClick(waitForClickable(By.xpath("(//span[normalize-space()='My Account'])[1]")));
            safeClick(waitForClickable(By.xpath("(//a[normalize-space()='Order History'])[1]")));
            waitForVisibility(By.cssSelector("#content"));
            By viewButton = By.xpath("//td[@class='text-end' and normalize-space()='#" + orderNumber + "']/parent::tr/td[last()]/a/i");
            waitForVisibility(viewButton);
            safeClick(waitForClickable(viewButton));

            //Delete Order
            By deleteButton = By.xpath("//*[@id=\"product-row-0\"]/td[5]/div/a");
            waitForVisibility(deleteButton);
            safeClick(waitForClickable(deleteButton));

            //confirm deletion
            waitForVisibility(By.cssSelector("#input-return-reason-1"));
            scrollIntoView(waitForVisibility(By.cssSelector("#input-return-reason-1")));
            safeClick(waitForClickable(By.cssSelector("#input-return-reason-1")));
            WebElement comfirmation = waitForClickable(By.xpath("//button[contains(normalize-space(.),'Submit')]"));
            //scroll down to the button
            //scrollIntoView(comfirmation);
            //waitForVisibility(comfirmation);
            safeClick(comfirmation);

            //Return success
            By ReturnMessage = By.id("content");
            waitForAjax();
            waitForVisibility(ReturnMessage);
            waitForStability(ReturnMessage);

            WebElement comfirmReturn = waitForClickable(By.xpath("//*[@id=\"content\"]/div/a"));
            waitForVisibility(comfirmReturn);
            safeClick(comfirmReturn);

            //go to product Order second time to ensure deletion
            waitForVisibility(By.xpath("(//span[normalize-space()='My Account'])[1]"));
            safeClick(waitForClickable(By.xpath("(//span[normalize-space()='My Account'])[1]")));
            waitForVisibility(By.xpath("(//a[normalize-space()='Order History'])[1]"));
            safeClick(waitForClickable(By.xpath("(//a[normalize-space()='Order History'])[1]")));
            waitForVisibility(By.cssSelector("#content"));

            //ensure the product is deleted
            By deletedOrder = By.xpath("//td[@class='text-end' and normalize-space()='#" + orderNumber + "']");
            if (isElementVisible(deletedOrder, 5)) {
                return false; // Order still exists, deletion failed
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
