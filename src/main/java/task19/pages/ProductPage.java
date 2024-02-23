package task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class ProductPage extends Page {

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(tagName = "h1")
    public WebElement title;

    @FindBy(css = "button[name^=add]")
    public WebElement addButton;

    @FindBy(css = "#site-menu li.general-0 > a")
    public WebElement backToMainPage;

    public void sizeSelection() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        if (driver.findElements(By.cssSelector("#box-product select")).size() > 0) {
            Select select = new Select(driver.findElement(By.cssSelector("#box-product select")));
            select.selectByVisibleText("Small");
        }
    }
}

