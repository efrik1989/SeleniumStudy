package task19.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends Page {


    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div.content li.product")
    public WebElement firstProduct;

    @FindBy(css = "div#cart span.quantity")
    public WebElement quantity;

    @FindBy(css = "#cart > a.link")
    public WebElement cart;

    public void openMainPage() {
        driver.get("http://localhost/litecard");
    }

    public boolean isCountChange(int productCountBefore) {
        return wait.until(ExpectedConditions
                .attributeContains(quantity,"textContent", "" + (productCountBefore+1)));
    }
}
