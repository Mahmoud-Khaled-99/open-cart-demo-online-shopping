import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SetupPage {
    public static final String BASE_URL = "http://localhost/opencart/index.php?route=account/login&language=en-gb";
    public static final String Browser = "edge";
    public static final String Email = "mahmoudkhaled99@gmail.com";
    public static final String Password = "123456789";
    public static final int ProductNumber = 1;
    public static final int DEFAULT_TIMEOUT = 10;
    public static final Duration DEFAULT_WAIT = Duration.ofSeconds(10);
    public static WebDriver driver = null;
    public final String FirstName = "Mahmoud";
    public final String LastName = "Khaled";


    public SetupPage() {
        // Default constructor
    }

    public SetupPage(WebDriver driver) {
        this.driver = driver;
        SetupPage.driver = driver;
        /*PageFactory.initElements(driver, this);*/
    }

    /**
     * Initializes the WebDriver based on the provided browser name.
     *
     * @param browserName The name of the browser to use (e.g., "chrome", "firefox", "edge").
     * @return The initialized WebDriver instance.
     */
    public static WebDriver BrowserRunner(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Sets an implicit wait for the WebDriver.
     *
     * @param time The time in seconds to wait implicitly.
     */
    public static void waitingimplicit(int time) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
        } else {
            System.err.println("WebDriver is not initialized. Cannot set implicit wait.");
        }
    }

    /**
     * Clicks a WebElement after waiting for it to be clickable.
     *
     * @param element        The WebElement to click.
     * @param timeoutSeconds The maximum time to wait for the element to be clickable.
     */
    public void clickWhenClickable(WebElement element, int timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(element))
                .click();
    }

    /**
     * Navigates the browser to the specified URL.
     *
     * @param url The URL to navigate to.
     */
    public void navigateToUrl(String url) {
        if (driver != null) {
            driver.get(url);
        } else {
            System.err.println("WebDriver is not initialized. Cannot navigate to URL.");
        }
    }

    /**
     * Quits the WebDriver instance, closing all associated browser windows.
     */
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Performs a hover action on a parent element and then clicks a child element.
     * This is useful for dropdown menus or elements that appear on hover.
     *
     * @param hoverElement   The WebElement to hover over.
     * @param clickElement   The WebElement to click after hovering.
     * @param timeoutSeconds The maximum time to wait for the click element to be clickable.
     */
    public void hoverAndClick(WebElement hoverElement, WebElement clickElement, int timeoutSeconds) {
        Actions actions = new Actions(driver);
        actions.moveToElement(hoverElement).perform();
        clickWhenClickable(clickElement, timeoutSeconds);
    }

    public WebElement waitForVisibility(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void clickMyAccount() {
        WebElement myAccountBtnn = driver.findElement(By.xpath("//*[@id='top']//span[text()='My Account']"));
        clickWhenClickable(myAccountBtnn, 5);
    }

    public void clickLogin() {
        WebElement loginBtn = driver.findElement(By.xpath("//*[@id='top']//a[text()='Login']"));
        clickWhenClickable(loginBtn, 5);
    }

    public void clickRegister() {
        WebElement RegisterBtn = driver.findElement(By.xpath("/html/body/div/nav/div/div/div[2]/ul/li[2]/div/ul/li[1]/a"));
        clickWhenClickable(RegisterBtn, 5);
    }

    public String getUniqueEmail() {
        return FirstName + System.currentTimeMillis() + "@gmail.com";
    }

}
