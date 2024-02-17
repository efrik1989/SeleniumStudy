import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CartWorkCheckScenario {
    WebDriverWait wait;
    WebDriver driver;

    @Before
    public void beforeTest(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));

    }

    @Test
    public void cartWorkCheck() throws Exception {
        System.out.println("Открыть главную страницу");
        driver.get("http://localhost/litecard");

        for (int i = 0; i < 3; i++) {

            System.out.println("Открыть первый товар из списка");
            driver.findElement(By.cssSelector("div.content li.product")).click();
            //Проверить коичество товара в корзине
            int productCountBefore = Integer.parseInt(driver.findElement(By.cssSelector("div#cart span.quantity"))
                    .getAttribute("textContent"));

            System.out.println("Добавить товар в корзину");
            if (!wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#box-product select")))) {
                Select select = new Select(driver.findElement(By.cssSelector("#box-product select")));
                select.selectByVisibleText("Small");
            }
            driver.findElement(By.cssSelector("button[name^=add]")).click();

            int productCountAfter = Integer.parseInt(driver.findElement(By.cssSelector("div#cart span.quantity"))
                    .getAttribute("textContent"));

            System.out.println("Количество товаров в корзине увеличилось: " + (productCountBefore < productCountAfter) +
                    " = " + productCountBefore);

            driver.findElement(By.cssSelector("#site-menu li.general-0 > a")).click();
        }

        driver.findElement(By.cssSelector("#cart > a.link")).click();

        while (true) {
            if (!wait.until(ExpectedConditions.textToBe(By.cssSelector("#checkout-cart-wrapper > p:nth-child(1) > em"), "There are no items in your cart."))) {
                driver.findElement(By.cssSelector("#box-checkout-cart button[name^=remove]")).click();
            } else break;
        }
        System.out.println("Все товары удалены из корзины");
        driver.quit();

    }

    @After
    public void afterTest() {
        try {
            driver.close();
        }catch (Exception e) {
            System.out.println("Browser is already closed.");
        }

    }

}
