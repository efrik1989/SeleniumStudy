package task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends Page {
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(tagName = "h2")
    public WebElement title;

    @FindAll(@FindBy(css = "#order_confirmation-wrapper > table > tbody > tr > td.item"))
    public List<WebElement> cartItemList;

    @FindBy(css = "#box-checkout-cart button[name^=remove]")
    public WebElement removeButton;

    public void tableUpdateCheck(List<WebElement> positions) {
        try {
            for (WebElement el : positions) {
                wait.until(ExpectedConditions.stalenessOf(el));
            }
        } catch (StaleElementReferenceException e) {
            System.out.println("Список товаров обновился.");
        }
    }

    public int quantityCheck(List<WebElement> positions) {
        int quantity = 0;
        for (WebElement el : positions) {
            String qua = el.findElement(By.xpath("..//td[1]")).getText();
            quantity+= Integer.parseInt(qua);
            System.out.println(qua + " | " + el.getText());
        }
        return quantity;
    }
}
