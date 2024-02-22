import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CartWorkCheckScenario {
    WebDriverWait wait;
    WebDriver driver;

    @Before
    public void beforeTest(){
        WebDriverManager.chromedriver().setup();
        //ChromeOptions options = new ChromeOptions();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(8));

    }

    @Test
    public void cartWorkCheck() throws Exception {
        System.out.println("Открыть главную страницу");
        driver.get("http://localhost/litecard");

        for (int i = 0; i < 3; i++) {

            System.out.println("Открыть первый товар из списка");
            driver.findElement(By.cssSelector("div.content li.product")).click();
            //Проверить коичество товара в корзине
            int productCountBefore = Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div#cart span.quantity")))
                    .getAttribute("textContent"));

            System.out.println("Добавить товар в корзину");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
            if (driver.findElements(By.cssSelector("#box-product select")).size() > 0) {
                Select select = new Select(driver.findElement(By.cssSelector("#box-product select")));
                select.selectByVisibleText("Small");
            }
            driver.findElement(By.cssSelector("button[name^=add]")).click();

            int productCountAfter = 0;
            boolean isProductCountChanged = wait.until(ExpectedConditions
                    .attributeContains(By.cssSelector("div#cart span.quantity"),"textContent", "" + (productCountBefore+1)));
            if (isProductCountChanged) {
                productCountAfter = Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#cart span.quantity")))
                        .getAttribute("textContent"));
            }

            System.out.println("Количество товаров в корзине увеличилось: " + (productCountBefore < productCountAfter) +
                    " = " + productCountAfter);

            driver.findElement(By.cssSelector("#site-menu li.general-0 > a")).click();
        }

        driver.findElement(By.cssSelector("#cart > a.link")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));

        System.out.println("Удаляем товары из корзины");
        while (true) {
            List<WebElement> positions = driver.findElements(By.cssSelector("#order_confirmation-wrapper > table > tbody > tr > td.item"));
            int quantity = quantityCheck(positions);
            if (quantity == 0) {
                break;
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-checkout-cart button[name^=remove]"))).click();
            System.out.println("Количество товаров в корзине: " + quantity);
            try {
                for (WebElement el : positions) {
                    wait.until(ExpectedConditions.stalenessOf(el));
                }
            } catch (StaleElementReferenceException e) {
                System.out.println("Список товаров обновился.");
            }
        }
        System.out.println("Все товары удалены из корзины");
        driver.quit();


    }

    @After
    public void afterTest() {
        try {
            driver.quit();
        }catch (Exception e) {
            System.out.println("Browser is already closed.");
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
