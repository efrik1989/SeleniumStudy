import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StickersAndDucks {
    WebDriver driver;
    final static long DEFAULT_TIMEOUT = 5000;

    @Before
    public void beforeTest(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    @Test
    public void stickersCheck() throws Exception {
        driver.get("http://localhost/litecard");

        // Список товаров на главной странице
        List<WebElement> elements = driver.findElements(By.cssSelector("div.content li.product.column.shadow"));

        for (WebElement element : elements) {
            System.out.print("Product: " + element.findElement(By.cssSelector("img")).getAttribute("alt") + "    | ");
            List <WebElement> stickers = element.findElements(By.cssSelector("div.sticker"));
            System.out.println("Stickers: " + stickers.size());
            if (stickers.size() != 1) {
                throw new Exception(" Количество стикеров не равно 1");
            }
        }
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
