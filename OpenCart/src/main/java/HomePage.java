/*import org.openqa.selenium.*;

public class HomePage extends SetupPage{
    public  HomePage() {
        super();
    }

    WebDriver driver;
    By searchBox = By.name("search");
    By searchButton = By.xpath("//*[@id=\"container\"]/header/div/div/div[2]/form/button/i");
    By firstProduct = By.cssSelector(".product-layout .caption a");

    public void searchProduct(String name) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(name);
        driver.findElement(searchButton).click();
    }

    public void openFirstProduct() {
        driver.findElement(firstProduct).click();
    }
}
}*/
