import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LogsCheckScenario {
    WebDriverWait wait;
    WebDriver driver;

    @Before
    public void beforeTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

    }

    @Test
    public void logsCheck() {
        driver.get("http://localhost/litecard/admin");

        //login
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElement(By.linkText("Catalog")).click();
        driver.findElement(By.linkText("Rubber Ducks")).click();
        driver.findElement(By.linkText("Subcategory")).click();
        int startIndex = 5;
        int finishIndex = startIndex + driver.findElements(By.cssSelector("#content td:nth-child(3) > a")).size();
        for (int i = startIndex; i < finishIndex; i++) {
            WebElement product = driver.findElement(By.cssSelector("#content tr:nth-child("+ i +") > td:nth-child(3) > a"));
            System.out.println(product.getText());
            product.click();
            for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
                System.out.println(l);
            }
            driver.navigate().back();
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


