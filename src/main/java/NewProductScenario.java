import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewProductScenario {
    String file;
    WebDriver driver;
    final static long DEFAULT_TIMEOUT = 5000;
    String productName = "Balloon" + new Date().getTime();

    @Before
    public void beforeTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        //ChromeOptions options = new ChromeOptions();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        file = "src/main/resources/balloon.png";


    }

    @Test
    public void menuCheck() {
        driver.get("http://localhost/litecard/admin");

        //login
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        System.out.println("Логин в админку");
        //Переход в каталог
        driver.findElement(By.linkText("Catalog")).click();
        driver.findElement(By.linkText("Add New Product")).click();
        System.out.println("Переход в Catalog и добавление нового продукта");

        driver.findElement(By.cssSelector("#tab-general > table > tbody > tr:nth-child(1) label:nth-child(3) > input[type=radio]")).click();
        driver.findElement(By.cssSelector("#tab-general > table > tbody > tr:nth-child(2) input[type=text]")).sendKeys(productName);
        driver.findElement(By.cssSelector("#tab-general > table > tbody > tr:nth-child(7) " +
                "tr:nth-child(4) > td:nth-child(1) > input[type=checkbox]")).click();

        WebElement quantity = driver.findElement(By.cssSelector("#tab-general > table > tbody > tr:nth-child(8) td:nth-child(1) > input[type=number]"));
        quantity.clear();
        quantity.sendKeys("100");

        WebElement image = driver.findElement(By.cssSelector("#tab-general input[type=file]"));
        image.sendKeys(Paths.get(file).toAbsolutePath().toString());
        System.out.println("Настройки во вкладке general завершены");

        //Переход в "Information"
        driver.findElement(By.linkText("Information")).click();

        Select select = new Select(driver.findElement(By.cssSelector("#tab-information > table > tbody > tr:nth-child(1) > td > select")));
        select.selectByVisibleText("ACME Corp.");

        driver.findElement(By.cssSelector("#tab-information > table > tbody > tr:nth-child(3) input[type=text]")).sendKeys(productName);
        driver.findElement(By.cssSelector("#tab-information > table > tbody > tr:nth-child(4) input[type=text]")).sendKeys(productName);
        driver.findElement(By.cssSelector("#tab-information > table > tbody > tr:nth-child(5) div.trumbowyg-editor")).sendKeys(productName);
        driver.findElement(By.cssSelector("#tab-information > table > tbody > tr:nth-child(6) input[type=text]")).sendKeys(productName);
        driver.findElement(By.cssSelector("#tab-information > table > tbody > tr:nth-child(7) input[type=text]")).sendKeys(productName);
        System.out.println("Настройки во вкладке Information завершены");

        //переход во вкладку "Prices"
        driver.findElement(By.linkText("Prices")).click();

        WebElement price = driver.findElement(By.cssSelector("#tab-prices > table:nth-child(2) input[type=number]"));
        price.clear();
        price.sendKeys("4");
        Select select1 = new Select(driver.findElement(By.cssSelector("#tab-prices > table:nth-child(2) select")));
        select1.selectByVisibleText("US Dollars");

        driver.findElement(By.cssSelector("#tab-prices > table:nth-child(4) > tbody > " +
                "tr:nth-child(2) > td:nth-child(1) input[type=text]")).sendKeys("5000");
        System.out.println("Настройки в Prices завершены");

        driver.findElement(By.cssSelector("#content > form > p > span > button:nth-child(1)")).click();
        driver.findElement(By.cssSelector("div.notice.success"));
        System.out.println("Добавление нового товара закончено");

        driver.findElement(By.linkText(productName));
        System.out.println("Новый товар появился в каталоге");

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

    public List<String> getMenuElementsNames(String cssSelector) {
        List<String> menuElementsNames = new LinkedList<>();
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        if (elements.size() > 0) {
            for (WebElement element : elements) {
                menuElementsNames.add(element.getText());
            }
        }
        return menuElementsNames;
    }

}
